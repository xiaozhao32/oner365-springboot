package com.oner365.queue.service.kafka.impl;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.redis.RedisCache;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.KafkaCondition;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;

import jakarta.annotation.Resource;

/**
 * Kafka 接收实现
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(KafkaCondition.class)
public class KafkaSendServiceImpl implements IQueueSendService {

    private final Logger logger = LoggerFactory.getLogger(KafkaSendServiceImpl.class);

    @Resource
    private RedisCache redisCache;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    @Override
    public void sendMessage(String data) {
        if (redisCache.lock(QueueConstants.MESSAGE_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND)) {
            logger.info("Kafka sendMessage: {}", data);
            try {
                CompletableFuture<SendResult<String, Object>> future = kafkaTemplate
                    .send(QueueConstants.MESSAGE_QUEUE_NAME, data);
                SendResult<String, Object> result = future.get();
                logger.info("Kafka future: {}", result.getProducerRecord());
            }
            catch (InterruptedException e) {
                logger.error("sendMessage InterruptedException:", e);
                Thread.currentThread().interrupt();
            }
            catch (Exception e) {
                logger.error("sendMessage error:", e);
            }
        }
    }

    @Async
    @Override
    public void syncRoute() {
        if (redisCache.lock(QueueConstants.ROUTE_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND)) {
            logger.info("Kafka syncRoute: {}", HttpClientUtils.getLocalhost());
            kafkaTemplate.send(QueueConstants.ROUTE_QUEUE_NAME, HttpClientUtils.getLocalhost());
        }
    }

    @Async
    @Override
    public void pullTask(InvokeParamDto data) {
        if (redisCache.lock(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND)) {
            logger.info("Kafka pullTask: {}", data);
            kafkaTemplate.send(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, JSON.toJSONString(data));
        }
    }

    @Async
    @Override
    public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
        if (redisCache.lock(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND)) {
            logger.info("Kafka updateTaskExecuteStatus push: {}", data);
            kafkaTemplate.send(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, JSON.toJSONString(data));
        }
    }

    @Async
    @Override
    public void saveExecuteTaskLog(SysTaskDto data) {
        if (redisCache.lock(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND)) {
            logger.info("Kafka saveExecuteTaskLog push: {}", data);
            kafkaTemplate.send(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, JSON.toJSONString(data));
        }
    }

}
