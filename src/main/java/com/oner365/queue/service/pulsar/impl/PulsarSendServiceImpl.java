package com.oner365.queue.service.pulsar.impl;

import java.util.concurrent.TimeUnit;

import jakarta.annotation.Resource;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.data.redis.RedisCache;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;

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
  private PulsarClient pulsarClient;

  public <T> Producer<T> createProducer(String topic, Schema<T> schema) {
    try {
      return pulsarClient.newProducer(schema).topic(topic).batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
          .sendTimeout(10, TimeUnit.SECONDS).blockIfQueueFull(true).create();
    } catch (PulsarClientException e) {
      throw new ProjectRuntimeException("初始化Pulsar Producer失败");
    }
  }

  @Async
  @Override
  public void sendMessage(JSONObject data) {
    if (redisCache.lock(QueueConstants.MESSAGE_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      try (Producer<JSONObject> producer = createProducer(QueueConstants.MESSAGE_QUEUE_NAME,
          Schema.JSON(JSONObject.class))) {
        MessageId messageId = producer.send(data);
        logger.info("Pulsar sendMessage: {} topic: {} messageId: {}", data, QueueConstants.MESSAGE_QUEUE_NAME, messageId);
      } catch (PulsarClientException e) {
        logger.error("Pulsar sendMessage error:", e);
      }
    }
  }

  @Async
  @Override
  public void syncRoute() {
    if (redisCache.lock(QueueConstants.ROUTE_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      try (Producer<String> producer = createProducer(QueueConstants.ROUTE_QUEUE_NAME, Schema.STRING)) {
        String data = HttpClientUtils.getLocalhost();
        MessageId messageId = producer.send(data);
        logger.info("Pulsar syncRoute: {} topic: {} messageId: {}", data, QueueConstants.MESSAGE_QUEUE_NAME, messageId);
      } catch (PulsarClientException e) {
        logger.error("Pulsar syncRoute error:", e);
      }
    }
  }

  @Async
  @Override
  public void pullTask(InvokeParamDto data) {
    if (redisCache.lock(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      try (Producer<InvokeParamDto> producer = createProducer(QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
          Schema.JSON(InvokeParamDto.class))) {
        MessageId messageId = producer.send(data);
        logger.info("Pulsar syncRoute: {} topic: {} messageId: {}", data, QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
            messageId);
      } catch (PulsarClientException e) {
        logger.error("Pulsar pullTask error:", e);
      }
    }
  }

  @Async
  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
    if (redisCache.lock(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      try (Producer<UpdateTaskExecuteStatusDto> producer = createProducer(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME,
          Schema.JSON(UpdateTaskExecuteStatusDto.class))) {
        MessageId messageId = producer.send(data);
        logger.info("Pulsar updateTaskExecuteStatus: {} topic: {} messageId: {}", data,
            QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, messageId);
      } catch (PulsarClientException e) {
        logger.error("Pulsar updateTaskExecuteStatus error:", e);
      }
    }
  }

  @Async
  @Override
  public void saveExecuteTaskLog(SysTaskDto data) {
    if (redisCache.lock(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      try (Producer<SysTaskDto> producer = createProducer(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME,
          Schema.JSON(SysTaskDto.class))) {
        MessageId messageId = producer.send(data);
        logger.info("Pulsar saveExecuteTaskLog: {} topic: {} messageId: {}", data,
            QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, messageId);
      } catch (PulsarClientException e) {
        logger.error("Pulsar saveExecuteTaskLog error:", e);
      }
    }
  }

}
