package com.oner365.queue.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.constants.RabbitmqConstants;
import com.oner365.queue.service.IQueueSendService;
import com.oner365.util.DataUtils;

import javax.annotation.Resource;

/**
 * rabbitmq 发送队列实现类
 *
 * @author zhaoyong
 *
 */
@Service
public class QueueRabbitmqSendServiceImpl implements IQueueSendService {

  private final Logger logger = LoggerFactory.getLogger(QueueRabbitmqSendServiceImpl.class);

  @Resource
  private RabbitTemplate rabbitTemplate;

  @Override
  public void sendMessage(String data) {
    logger.info("Rabbitmq sendMessage: {}", data);
    rabbitTemplate.convertAndSend(RabbitmqConstants.MESSAGE_QUEUE_TYPE, RabbitmqConstants.MESSAGE_QUEUE_KEY, data);
  }

  @Override
  public void syncRoute() {
    logger.info("Rabbitmq syncRoute: {}", DataUtils.getLocalhost());
    rabbitTemplate.convertAndSend(RabbitmqConstants.ROUTE_QUEUE_TYPE, RabbitmqConstants.ROUTE_QUEUE_KEY, DataUtils.getLocalhost());
  }

  @Override
  public void pullTask(InvokeParamDto invokeParamDto) {
    logger.info("Rabbitmq pullTask: {}", invokeParamDto);
    rabbitTemplate.convertAndSend(RabbitmqConstants.SCHEDULE_TASK_QUEUE_TYPE,
        RabbitmqConstants.SCHEDULE_TASK_QUEUE_KEY, invokeParamDto);
  }

  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteStatusDto updateTaskExecuteStatusDto) {
    logger.info("Rabbitmq updateTaskExecuteStatus push: {}", updateTaskExecuteStatusDto);
    rabbitTemplate.convertAndSend(RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_TYPE,
        RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_KEY, updateTaskExecuteStatusDto);
  }

  @Override
  public void saveExecuteTaskLog(SysTaskDto sysTask) {
    logger.info("Rabbitmq saveExecuteTaskLog push: {}", sysTask);
    rabbitTemplate.convertAndSend(RabbitmqConstants.SAVE_TASK_LOG_QUEUE_TYPE,
        RabbitmqConstants.SAVE_TASK_LOG_QUEUE_KEY, sysTask);
  }


}
