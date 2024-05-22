package com.oner365.queue.service.mqtt.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.common.cache.RedisCache;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.IQueueSendService;
import com.oner365.queue.service.mqtt.component.IMqttSendInvokeParamService;
import com.oner365.queue.service.mqtt.component.IMqttSendMessageService;
import com.oner365.queue.service.mqtt.component.IMqttSendRouteService;
import com.oner365.queue.service.mqtt.component.IMqttSendTaskExecuteStatusService;
import com.oner365.queue.service.mqtt.component.IMqttSendTaskLogService;

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
  private RedisCache redisCache;
  
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
  public void sendMessage(JSONObject message) {
    if (!message.isEmpty() && redisCache.lock(QueueConstants.MESSAGE_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Mqtt send message: {} topic: {}", message, QueueConstants.MESSAGE_QUEUE_NAME);
      messageService.sendMessage(QueueConstants.MESSAGE_QUEUE_NAME, message.toJSONString());
    }
  }

  @Async
  @Override
  public void syncRoute() {
    if (redisCache.lock(QueueConstants.ROUTE_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Mqtt send syncRoute topic: {}", QueueConstants.ROUTE_QUEUE_NAME);
      routeService.sendMessage(QueueConstants.ROUTE_QUEUE_NAME, QueueConstants.ROUTE_QUEUE_NAME);
    }
  }

  @Async
  @Override
  public void pullTask(InvokeParamDto data) {
    if (redisCache.lock(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Mqtt send pullTask: {} topic: {}", data, QueueConstants.SCHEDULE_TASK_QUEUE_NAME);
      invokeParamService.sendMessage(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, JSON.toJSONString(data));
    }
  }

  @Async
  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
    if (redisCache.lock(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Mqtt send updateTaskExecuteStatus: {} topic: {}", data, QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME);
      taskExecuteStatusService.sendMessage(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, JSON.toJSONString(data));
    }
  }

  @Async
  @Override
  public void saveExecuteTaskLog(SysTaskDto data) {
    if (redisCache.lock(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, QueueConstants.QUEUE_LOCK_TIME_SECOND)) {
      logger.info("Mqtt send saveExecuteTaskLog: {} topic: {}", data, QueueConstants.SAVE_TASK_LOG_QUEUE_NAME);
      taskLogService.sendMessage(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, JSON.toJSONString(data));
    }
  }

}
