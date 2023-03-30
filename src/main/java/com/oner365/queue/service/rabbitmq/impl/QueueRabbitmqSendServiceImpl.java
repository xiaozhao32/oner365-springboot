package com.oner365.queue.service.rabbitmq.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.RabbitmqCondition;
import com.oner365.queue.constants.RabbitmqConstants;
import com.oner365.queue.service.IQueueSendService;
import com.oner365.queue.service.rabbitmq.callback.QueueRabbitmqConfirmCallback;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

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
  private RabbitTemplate rabbitTemplate;

  @Resource
  private QueueRabbitmqConfirmCallback confirmCallback;

  @Override
  public void sendMessage(JSONObject data) {
    logger.info("Rabbitmq sendMessage: {}", data);
    // 是否回调确认
    rabbitTemplate.setConfirmCallback(confirmCallback);

    rabbitTemplate.convertSendAndReceive(RabbitmqConstants.MESSAGE_QUEUE_TYPE, RabbitmqConstants.MESSAGE_QUEUE_KEY,
        data, new CorrelationData(DateUtil.getCurrentTime()));
  }

  @Override
  public void syncRoute() {
    logger.info("Rabbitmq syncRoute: {}", DataUtils.getLocalhost());
    rabbitTemplate.convertAndSend(RabbitmqConstants.ROUTE_QUEUE_TYPE, RabbitmqConstants.ROUTE_QUEUE_KEY,
        DataUtils.getLocalhost());
  }

  @Override
  public void pullTask(InvokeParamDto data) {
    logger.info("Rabbitmq pullTask: {}", data);
    rabbitTemplate.convertAndSend(RabbitmqConstants.SCHEDULE_TASK_QUEUE_TYPE, RabbitmqConstants.SCHEDULE_TASK_QUEUE_KEY,
        data);
  }

  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto data) {
    logger.info("Rabbitmq updateTaskExecuteStatus push: {}", data);
    rabbitTemplate.convertAndSend(RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_TYPE,
        RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_KEY, data);
  }

  @Override
  public void saveExecuteTaskLog(SysTaskDto data) {
    logger.info("Rabbitmq saveExecuteTaskLog push: {}", data);
    rabbitTemplate.convertAndSend(RabbitmqConstants.SAVE_TASK_LOG_QUEUE_TYPE, RabbitmqConstants.SAVE_TASK_LOG_QUEUE_KEY,
        data);
  }

}