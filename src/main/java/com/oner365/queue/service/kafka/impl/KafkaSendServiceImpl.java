package com.oner365.queue.service.kafka.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.KafkaCondition;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;
import com.oner365.util.DataUtils;

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
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Async
  @Override
  public void sendMessage(JSONObject data) {
    logger.info("Kafka sendMessage: {}", data);
    try {
      ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(QueueConstants.MESSAGE_QUEUE_NAME, data.toJSONString());
      future.addCallback(new ListenableFutureCallback<Object>() {

        @Override
        public void onSuccess(Object result) {
          logger.info("Kafka callback success:{}", result);
        }

        @Override
        public void onFailure(Throwable e) {
          logger.error("Kafka callback failure:", e);
        }

      });
    } catch (Exception e) {
      logger.error("sendMessage error:", e);
    }
  }

  @Async
  @Override
  public void syncRoute() {
    logger.info("Kafka syncRoute: {}", DataUtils.getLocalhost());
    kafkaTemplate.send(QueueConstants.ROUTE_QUEUE_NAME, DataUtils.getLocalhost());
  }

  @Async
  @Override
  public void pullTask(InvokeParamDto data) {
    logger.info("Kafka pullTask: {}", data);
    kafkaTemplate.send(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, JSON.toJSONString(data));
  }

  @Async
  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
    logger.info("Kafka updateTaskExecuteStatus push: {}", data);
    kafkaTemplate.send(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, JSON.toJSONString(data));
  }

  @Async
  @Override
  public void saveExecuteTaskLog(SysTaskDto data) {
    logger.info("Kafka saveExecuteTaskLog push: {}", data);
    kafkaTemplate.send(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, JSON.toJSONString(data));
  }

}
