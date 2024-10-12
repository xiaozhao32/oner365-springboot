package com.oner365.queue.service.rabbitmq.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.redis.RedisCache;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.RabbitmqCondition;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;
import com.oner365.queue.service.rabbitmq.callback.QueueRabbitmqConfirmCallback;

/**
 * rabbitmq 发送队列实现类
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(RabbitmqCondition.class)
public class QueueRabbitmqSendServiceImpl implements IQueueSendService {

  private final Logger logger = LoggerFactory.getLogger(QueueRabbitmqSendServiceImpl.class);
  
  @Resource
  private RedisCache redisCache;

  @Resource
  private RabbitTemplate rabbitTemplate;

  @Resource
  private QueueRabbitmqConfirmCallback confirmCallback;

  @Async
  @Override
  public void sendMessage(String data) {
    if (redisCache.lock(QueueConstants.MESSAGE_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Rabbitmq sendMessage: {}", data);
      // 是否回调确认
      rabbitTemplate.setConfirmCallback(confirmCallback);
  
      rabbitTemplate.convertSendAndReceive(QueueConstants.MESSAGE_QUEUE_TYPE, QueueConstants.MESSAGE_QUEUE_KEY,
          data, new CorrelationData(DateUtil.getCurrentTime()));
    }
  }

  @Async
  @Override
  public void syncRoute() {
    if (redisCache.lock(QueueConstants.ROUTE_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Rabbitmq syncRoute: {}", HttpClientUtils.getLocalhost());
      rabbitTemplate.convertAndSend(QueueConstants.ROUTE_QUEUE_TYPE, QueueConstants.ROUTE_QUEUE_KEY,
          HttpClientUtils.getLocalhost());
    }
  }

  @Async
  @Override
  public void pullTask(InvokeParamDto data) {
    if (redisCache.lock(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Rabbitmq pullTask: {}", data);
      rabbitTemplate.convertAndSend(QueueConstants.SCHEDULE_TASK_QUEUE_TYPE, QueueConstants.SCHEDULE_TASK_QUEUE_KEY,
          JSON.toJSONString(data));
    }
  }

  @Async
  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
    if (redisCache.lock(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Rabbitmq updateTaskExecuteStatus push: {}", data);
      rabbitTemplate.convertAndSend(QueueConstants.TASK_UPDATE_STATUS_QUEUE_TYPE,
          QueueConstants.TASK_UPDATE_STATUS_QUEUE_KEY, JSON.toJSONString(data));
    }
  }

  @Async
  @Override
  public void saveExecuteTaskLog(SysTaskDto data) {
    if (redisCache.lock(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Rabbitmq saveExecuteTaskLog push: {}", data);
      rabbitTemplate.convertAndSend(QueueConstants.SAVE_TASK_LOG_QUEUE_TYPE, QueueConstants.SAVE_TASK_LOG_QUEUE_KEY,
          JSON.toJSONString(data));
    }
  }

}
