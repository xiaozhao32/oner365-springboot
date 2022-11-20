package com.oner365.queue.service.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.stereotype.Component;

/**
 * 队列回调确认
 * 
 * @author zhaoyong
 *
 */
@Component
public class QueueRabbitmqConfirmCallback implements ConfirmCallback {

  private final Logger logger = LoggerFactory.getLogger(QueueRabbitmqConfirmCallback.class);

  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    logger.info("Rabbitmq Confirm ack: {}, correlationData: {}", ack, correlationData);
    if (!ack) {
      logger.error("Rabbitmq Confirm error:{}", cause);
    }
  }

}
