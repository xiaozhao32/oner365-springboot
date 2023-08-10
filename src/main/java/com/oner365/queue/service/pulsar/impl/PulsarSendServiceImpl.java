package com.oner365.queue.service.pulsar.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;
import com.oner365.util.DataUtils;

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
  private PulsarProperties pulsarProperties;

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

  @Override
  public void sendMessage(JSONObject data) {
    try (Producer<JSONObject> producer = createProducer(QueueConstants.MESSAGE_QUEUE_NAME,
        Schema.JSON(JSONObject.class))) {
      MessageId messageId = producer.send(data);
      logger.info("Pulsar sendMessage: {} topic: {} messageId: {}", data, QueueConstants.MESSAGE_QUEUE_NAME, messageId);
    } catch (PulsarClientException e) {
      logger.error("Pulsar sendMessage error:", e);
    }
  }

  @Override
  public void syncRoute() {
    try (Producer<String> producer = createProducer(QueueConstants.ROUTE_QUEUE_NAME, Schema.STRING)) {
      String data = DataUtils.getLocalhost();
      MessageId messageId = producer.send(data);
      logger.info("Pulsar syncRoute: {} topic: {} messageId: {}", data, QueueConstants.MESSAGE_QUEUE_NAME, messageId);
    } catch (PulsarClientException e) {
      logger.error("Pulsar syncRoute error:", e);
    }
  }

  @Override
  public void pullTask(InvokeParamDto data) {
    try (Producer<InvokeParamDto> producer = createProducer(QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
        Schema.JSON(InvokeParamDto.class))) {
      MessageId messageId = producer.send(data);
      logger.info("Pulsar syncRoute: {} topic: {} messageId: {}", data, QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
          messageId);
    } catch (PulsarClientException e) {
      logger.error("Pulsar pullTask error:", e);
    }
  }

  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
    try (Producer<UpdateTaskExecuteStatusDto> producer = createProducer(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME,
        Schema.JSON(UpdateTaskExecuteStatusDto.class))) {
      MessageId messageId = producer.send(data);
      logger.info("Pulsar updateTaskExecuteStatus: {} topic: {} messageId: {}", data,
          QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, messageId);
    } catch (PulsarClientException e) {
      logger.error("Pulsar updateTaskExecuteStatus error:", e);
    }
  }

  @Override
  public void saveExecuteTaskLog(SysTaskDto data) {
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
