package com.oner365.queue.service.pulsar.impl;

import org.apache.pulsar.client.api.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.redis.RedisCache;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;

import jakarta.annotation.Resource;

/**
 * pulsar service impl
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(PulsarCondition.class)
public class PulsarSendServiceImpl implements IQueueSendService {

    private final Logger logger = LoggerFactory.getLogger(PulsarSendServiceImpl.class);

    @Resource
    private RedisCache redisCache;

    @Resource
    private PulsarTemplate<String> pulsarTemplate;

    @Async
    @Override
    public void sendMessage(String data) {
        boolean isLock = redisCache.lock(QueueConstants.MESSAGE_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            MessageId messageId = pulsarTemplate.send(QueueConstants.MESSAGE_QUEUE_NAME, data);
            logger.info("Pulsar sendMessage: {} topic: {} messageId: {}", data, QueueConstants.MESSAGE_QUEUE_NAME,
                    messageId);
        }
    }

    @Async
    @Override
    public void syncRoute() {
        boolean isLock = redisCache.lock(QueueConstants.ROUTE_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            String data = HttpClientUtils.getLocalhost();
            MessageId messageId = pulsarTemplate.send(QueueConstants.ROUTE_QUEUE_NAME, data);
            logger.info("Pulsar syncRoute: {} topic: {} messageId: {}", data, QueueConstants.ROUTE_QUEUE_NAME,
                    messageId);
        }
    }

    @Async
    @Override
    public void pullTask(InvokeParamDto data) {
        boolean isLock = redisCache.lock(QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
                PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            MessageId messageId = pulsarTemplate.send(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, JSON.toJSONString(data));
            logger.info("Pulsar pullTask: {} topic: {} messageId: {}", data, QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
                    messageId);
        }
    }

    @Async
    @Override
    public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
        boolean isLock = redisCache.lock(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME,
                PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            MessageId messageId = pulsarTemplate.send(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME,
                    JSON.toJSONString(data));
            logger.info("Pulsar updateTaskExecuteStatus: {} topic: {} messageId: {}", data,
                    QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, messageId);
        }
    }

    @Async
    @Override
    public void saveExecuteTaskLog(SysTaskDto data) {
        boolean isLock = redisCache.lock(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME,
                PublicConstants.QUEUE_LOCK_TIME_SECOND);
        if (isLock) {
            MessageId messageId = pulsarTemplate.send(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, JSON.toJSONString(data));
            logger.info("Pulsar pullTask: {} topic: {} messageId: {}", data, QueueConstants.SAVE_TASK_LOG_QUEUE_NAME,
                    messageId);
        }
    }

}
