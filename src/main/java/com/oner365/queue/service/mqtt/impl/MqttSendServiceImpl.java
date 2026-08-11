package com.oner365.queue.service.mqtt.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.GsonUtils;
import com.oner365.data.redis.RedisCache;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.config.MqttConfig.IMqttSendInvokeParamService;
import com.oner365.queue.config.MqttConfig.IMqttSendMessageService;
import com.oner365.queue.config.MqttConfig.IMqttSendRouteService;
import com.oner365.queue.config.MqttConfig.IMqttSendTaskExecuteStatusService;
import com.oner365.queue.config.MqttConfig.IMqttSendTaskLogService;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;

import jakarta.annotation.Resource;

/**
 * MQTT 接收实现
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(MqttCondition.class)
public class MqttSendServiceImpl implements IQueueSendService {

    private final Logger logger = LoggerFactory.getLogger(MqttSendServiceImpl.class);

    @Resource
    private RedisCache<String> redisCache;

    @Resource
    private IMqttSendMessageService messageService;

    @Resource
    private IMqttSendRouteService routeService;

    @Resource
    private IMqttSendInvokeParamService invokeParamService;

    @Resource
    private IMqttSendTaskLogService taskLogService;

    @Resource
    private IMqttSendTaskExecuteStatusService taskExecuteStatusService;

    @Async
    @Override
    public void sendMessage(String message) {
        boolean isLock = redisCache.lock(QueueConstants.MESSAGE_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (!message.isEmpty() && isLock) {
            logger.info("Mqtt send message: {} topic: {}", message, QueueConstants.MESSAGE_QUEUE_NAME);
            messageService.sendMessage(QueueConstants.MESSAGE_QUEUE_NAME, message);
        }
    }

    @Async
    @Override
    public void syncRoute() {
        boolean isLock = redisCache.lock(QueueConstants.ROUTE_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            logger.info("Mqtt send syncRoute topic: {}", QueueConstants.ROUTE_QUEUE_NAME);
            routeService.sendMessage(QueueConstants.ROUTE_QUEUE_NAME, QueueConstants.ROUTE_QUEUE_NAME);
        }
    }

    @Async
    @Override
    public void pullTask(InvokeParamDto data) {
        boolean isLock = redisCache.lock(QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
                PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            logger.info("Mqtt send pullTask: {} topic: {}", data, QueueConstants.SCHEDULE_TASK_QUEUE_NAME);
            invokeParamService.sendMessage(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, GsonUtils.objectToJson(data));
        }
    }

    @Async
    @Override
    public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
        boolean isLock = redisCache.lock(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME,
                PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            logger.info("Mqtt send updateTaskExecuteStatus: {} topic: {}", data,
                    QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME);
            taskExecuteStatusService.sendMessage(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME,
                    GsonUtils.objectToJson(data));
        }
    }

    @Async
    @Override
    public void saveExecuteTaskLog(SysTaskDto data) {
        boolean isLock = redisCache.lock(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME,
                PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            logger.info("Mqtt send saveExecuteTaskLog: {} topic: {}", data, QueueConstants.SAVE_TASK_LOG_QUEUE_NAME);
            taskLogService.sendMessage(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, GsonUtils.objectToJson(data));
        }
    }

}
