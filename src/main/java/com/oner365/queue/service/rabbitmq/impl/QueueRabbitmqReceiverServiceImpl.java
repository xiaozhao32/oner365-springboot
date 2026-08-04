package com.oner365.queue.service.rabbitmq.impl;

import java.io.IOException;
import java.time.LocalDateTime;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.commons.util.GsonUtils;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.RabbitmqCondition;
import com.oner365.queue.service.rabbitmq.IQueueRabbitmqReceiverService;
import com.rabbitmq.client.Channel;

import jakarta.annotation.Resource;
import jakarta.json.JsonObject;

/**
 * rabbitmq 接收队列实现类
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(RabbitmqCondition.class)
public class QueueRabbitmqReceiverServiceImpl implements IQueueRabbitmqReceiverService {

    private final Logger logger = LoggerFactory.getLogger(QueueRabbitmqReceiverServiceImpl.class);

    @Resource
    private DynamicRouteService dynamicRouteService;

    @Resource
    private ISysTaskLogService sysTaskLogService;

    @Resource
    private ISysTaskService sysTaskService;

    @Override
    public void message(String msg, Channel channel, Message message) {
        try {
            logger.info("Message: {}", msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
        catch (IOException e) {
            if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {
                try {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                }
                catch (IOException e1) {
                    logger.info("消息处理失败，拒绝接收.");
                }
            }
            else {
                try {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
                catch (IOException e1) {
                    logger.info("消息重新发送.");
                }
            }
        }
    }

    @Override
    public void syncRoute(String gatewayIp) {
        logger.info("syncRoute: {}", gatewayIp);
        dynamicRouteService.refreshRoute();
    }

    @Override
    public void scheduleTask(String data) {
        InvokeParamDto invokeParamDto = GsonUtils.jsonToBean(data, InvokeParamDto.class);
        if (ScheduleConstants.SCHEDULE_SERVER_NAME.equals(invokeParamDto.getTaskServerName())) {
            taskExecute(invokeParamDto.getConcurrent(), invokeParamDto.getTaskId(), invokeParamDto.getTaskParam());
        }
    }

    private void taskExecute(String concurrent, String taskId, JsonObject param) {
        SysTaskDto sysTask = sysTaskService.selectTaskById(taskId);
        if (sysTask != null) {
            if (ScheduleConstants.SCHEDULE_CONCURRENT.equals(concurrent)) {
                logger.info("taskExecute  concurrent : {} , update sysTask  executeStatus = 0", concurrent);
                execute(taskId, param, sysTask);

            }
            else {
                if (!StatusEnum.NO.equals(sysTask.getExecuteStatus())) {
                    execute(taskId, param, sysTask);
                }
                logger.info("taskExecute  concurrent : {}", concurrent);
            }
            saveExecuteTaskLog(GsonUtils.objectToJson(sysTask));
        }
    }

    private StatusEnum execute(String taskId, JsonObject param, SysTaskDto sysTask) {
        try {
            logger.info("taskId:{}", taskId);
            sysTask.setExecuteStatus(StatusEnum.NO);
            sysTaskService.save(convert(sysTask, SysTaskVo.class));
            int day = param.getInt("day");
            String time = DateUtil.nextDay(day - 2 * day, DateUtil.FULL_TIME_FORMAT);
            sysTaskLogService.deleteTaskLogByCreateTime(time);

            sysTask.setExecuteStatus(StatusEnum.YES);
            sysTaskService.save(convert(sysTask, SysTaskVo.class));
            return StatusEnum.YES;
        }
        catch (Exception e) {
            logger.error("update sysTask Exception:", e);
            return StatusEnum.NO;
        }
    }

    @Override
    public void updateTaskExecuteStatus(String data) throws SchedulerException, TaskException {
        logger.info("updateTaskExecuteStatus :{}", data);
        UpdateTaskExecuteStatusDto updateTask = GsonUtils.jsonToBean(data, UpdateTaskExecuteStatusDto.class);

        SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
        if (sysTask != null) {
            sysTask.setExecuteStatus(updateTask.getExecuteStatus());
            sysTaskService.save(convert(sysTask, SysTaskVo.class));
        }
    }

    @Override
    public void saveExecuteTaskLog(String data) {
        logger.info("saveExecuteTaskLog :{}", data);
        SysTaskDto sysTask = GsonUtils.jsonToBean(data, SysTaskDto.class);

        long time = System.currentTimeMillis();
        SysTaskLogVo taskLog = new SysTaskLogVo();
        taskLog.setStartTime(LocalDateTime.now());
        taskLog.setExecuteIp(HttpClientUtils.getLocalhost());
        taskLog.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
        taskLog.setStatus(TaskStatusEnum.NORMAL);
        taskLog.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
        taskLog.setTaskGroup(sysTask.getTaskGroup());
        taskLog.setTaskName(sysTask.getTaskName());
        taskLog.setInvokeTarget(sysTask.getInvokeTarget());
        taskLog.setCreateUser(sysTask.getCreateUser());
        taskLog.setStopTime(LocalDateTime.now());
        sysTaskLogService.addTaskLog(taskLog);
    }

}
