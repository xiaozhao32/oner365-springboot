package com.oner365.queue.service.redis.impl;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.annotation.RedisListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.jpa.service.BaseService;
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
import com.oner365.queue.condition.RedisCondition;
import com.oner365.queue.constants.QueueConstants;

import jakarta.annotation.Resource;

/**
 * Redis pub/subscribe Pub Service
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(RedisCondition.class)
public class RedisMessageReceiverServiceImpl implements BaseService {

    private final Logger logger = LoggerFactory.getLogger(RedisMessageReceiverServiceImpl.class);

    @Resource
    private DynamicRouteService dynamicRouteService;

    @Resource
    private ISysTaskLogService sysTaskLogService;

    @Resource
    private ISysTaskService sysTaskService;

    @RedisListener(topic = QueueConstants.MESSAGE_QUEUE_TYPE)
    public void onMessage(Message<String> message) {
        logger.info("@RedisListener message: {} Channel: {}", message.getPayload(), QueueConstants.MESSAGE_QUEUE_TYPE);
    }

    @RedisListener(topic = QueueConstants.ROUTE_QUEUE_TYPE)
    public void syncRoute(Message<String> message) {
        logger.info("@RedisListener syncRoute: {} Channel: {}", message.getPayload(), QueueConstants.ROUTE_QUEUE_TYPE);
        dynamicRouteService.refreshRoute();
    }

    @RedisListener(topic = QueueConstants.SCHEDULE_TASK_QUEUE_TYPE)
    public void scheduleTask(Message<InvokeParamDto> message) {
        logger.info("@RedisListener scheduleTask: {} Channel: {}", message.getPayload(),
                QueueConstants.SCHEDULE_TASK_QUEUE_TYPE);
        InvokeParamDto invokeParamDto = message.getPayload();
        if (ScheduleConstants.SCHEDULE_SERVER_NAME.equals(invokeParamDto.getTaskServerName())) {
            taskExecute(invokeParamDto.getConcurrent(), invokeParamDto.getTaskId(), invokeParamDto.getTaskParam());
        }
    }

    @RedisListener(topic = QueueConstants.TASK_UPDATE_STATUS_QUEUE_TYPE)
    public void updateTaskExecuteStatus(Message<UpdateTaskExecuteStatusDto> message)
            throws SchedulerException, TaskException {
        logger.info("@RedisListener updateTaskExecuteStatus: {} Channel: {}", message.getPayload(),
                QueueConstants.TASK_UPDATE_STATUS_QUEUE_TYPE);
        UpdateTaskExecuteStatusDto updateTask = message.getPayload();

        SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
        if (sysTask != null) {
            sysTask.setExecuteStatus(updateTask.getExecuteStatus());
            sysTaskService.save(convert(sysTask, SysTaskVo.class));
        }
    }

    @RedisListener(topic = QueueConstants.SAVE_TASK_LOG_QUEUE_TYPE)
    public void saveExecuteTaskLog(Message<SysTaskDto> message) {
        logger.info("@RedisListener saveExecuteTaskLog: {} Channel: {}", message.getPayload(),
                QueueConstants.SAVE_TASK_LOG_QUEUE_TYPE);
        SysTaskDto sysTask = message.getPayload();
        saveTaskLog(sysTask);
    }

    private void taskExecute(String concurrent, String taskId, JSONObject param) {
        SysTaskDto sysTask = sysTaskService.selectTaskById(taskId);
        if (sysTask != null) {
            if (ScheduleConstants.SCHEDULE_CONCURRENT.equals(concurrent)) {
                logger.info("taskExecute  concurrent : {} , update sysTask  executeStatus = 0", concurrent);
                execute(taskId, param, sysTask);

            } else {
                if (!StatusEnum.NO.equals(sysTask.getExecuteStatus())) {
                    execute(taskId, param, sysTask);
                }
                logger.info("taskExecute  concurrent : {}", concurrent);
            }
            saveTaskLog(sysTask);
        }
    }

    private StatusEnum execute(String taskId, JSONObject param, SysTaskDto sysTask) {
        try {
            logger.info("taskId:{}", taskId);
            sysTask.setExecuteStatus(StatusEnum.NO);
            sysTaskService.save(convert(sysTask, SysTaskVo.class));
            int day = param.getInteger("day");
            String time = DateUtil.nextDay(day - 2 * day, DateUtil.FULL_TIME_FORMAT);
            sysTaskLogService.deleteTaskLogByCreateTime(time);

            sysTask.setExecuteStatus(StatusEnum.YES);
            sysTaskService.save(convert(sysTask, SysTaskVo.class));
            return StatusEnum.YES;
        } catch (Exception e) {
            logger.error("update sysTask Exception:", e);
            return StatusEnum.NO;
        }
    }

    private void saveTaskLog(SysTaskDto sysTask) {
        long time = System.currentTimeMillis();
        SysTaskLogVo taskLog = new SysTaskLogVo();
        taskLog.setExecuteIp(HttpClientUtils.getLocalhost());
        taskLog.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
        taskLog.setStatus(TaskStatusEnum.NORMAL);
        taskLog.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
        taskLog.setTaskGroup(sysTask.getTaskGroup());
        taskLog.setTaskName(sysTask.getTaskName());
        taskLog.setInvokeTarget(sysTask.getInvokeTarget());
        taskLog.setCreateUser(sysTask.getCreateUser());
        sysTaskLogService.addTaskLog(taskLog);
    }

}
