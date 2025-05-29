package com.oner365.queue.service.mqtt.impl;

import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.constants.MqttConstants;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.mqtt.IMqttReceiverInvokeParamService;

/**
 * MQTT 接收实现
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(MqttCondition.class)
public class MqttReceiverInvokeParamServiceImpl implements IMqttReceiverInvokeParamService {

    private final Logger logger = LoggerFactory.getLogger(MqttReceiverInvokeParamServiceImpl.class);

    @Resource
    private ISysTaskLogService sysTaskLogService;

    @Resource
    private ISysTaskService sysTaskService;

    @Override
    @ServiceActivator(inputChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
            outputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.SCHEDULE_TASK_QUEUE_NAME)
    public void message(Object message) {
        logger.info("Mqtt receive taskExecute: {}", message);

        // business
        InvokeParamDto dto = JSON.parseObject(message.toString(), InvokeParamDto.class);
        if (dto != null && ScheduleConstants.SCHEDULE_SERVER_NAME.equals(dto.getTaskServerName())) {
            taskExecute(dto.getConcurrent(), dto.getTaskId(), dto.getTaskParam());
        }
    }

    private void taskExecute(String concurrent, String taskId, JSONObject param) {
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
            saveExecuteTaskLog(sysTask);
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
        }
        catch (Exception e) {
            logger.error("update sysTask Exception:", e);
            return StatusEnum.NO;
        }
    }

    public void saveExecuteTaskLog(SysTaskDto sysTask) {
        logger.info("saveExecuteTaskLog :{}", sysTask);

        long time = System.currentTimeMillis();
        SysTaskLogVo taskLog = new SysTaskLogVo();
        taskLog.setExecuteIp(HttpClientUtils.getLocalhost());
        taskLog.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
        taskLog.setStatus(TaskStatusEnum.NORMAL);
        taskLog.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
        taskLog.setTaskGroup(sysTask.getTaskGroup());
        taskLog.setTaskName(sysTask.getTaskName());
        taskLog.setInvokeTarget(sysTask.getInvokeTarget());
        sysTaskLogService.addTaskLog(taskLog);
    }

}
