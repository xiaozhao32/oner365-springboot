package com.oner365.init;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.oner365.queue.condition.RabbitmqCondition;
import com.oner365.queue.constants.RabbitmqConstants;
import com.oner365.sys.constants.SysMessageConstants;

@Component
@Conditional(RabbitmqCondition.class)
public class RabbitmqRunner implements ApplicationRunner {
  
  private final Logger logger = LoggerFactory.getLogger(RabbitmqRunner.class);
  
  @Resource
  private RabbitAdmin rabbitAdmin;
  
  @Override
  public void run(ApplicationArguments args) {
    
  }

  @PreDestroy
  public void destroy() {
    rabbitAdmin.deleteQueue(RabbitmqConstants.MESSAGE_QUEUE_NAME);
    rabbitAdmin.deleteQueue(RabbitmqConstants.SAVE_TASK_LOG_QUEUE_NAME);
    rabbitAdmin.deleteQueue(RabbitmqConstants.SCHEDULE_TASK_QUEUE_NAME);
    rabbitAdmin.deleteQueue(RabbitmqConstants.ROUTE_QUEUE_NAME);
    rabbitAdmin.deleteQueue(SysMessageConstants.QUEUE_NAME);
    rabbitAdmin.deleteQueue(RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_NAME);
    logger.info("Destroy Rabbitmq queue.");

    rabbitAdmin.deleteExchange(RabbitmqConstants.MESSAGE_QUEUE_TYPE);
    rabbitAdmin.deleteExchange(RabbitmqConstants.SAVE_TASK_LOG_QUEUE_TYPE);
    rabbitAdmin.deleteExchange(RabbitmqConstants.SCHEDULE_TASK_QUEUE_TYPE);
    rabbitAdmin.deleteExchange(RabbitmqConstants.ROUTE_QUEUE_TYPE);
    rabbitAdmin.deleteExchange(SysMessageConstants.QUEUE_TYPE);
    rabbitAdmin.deleteExchange(RabbitmqConstants.TASK_UPDATE_STATUS_QUEUE_TYPE);
    logger.info("Destroy Rabbitmq exchange.");
  }

  
}
