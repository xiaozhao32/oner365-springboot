package com.oner365.queue.service.mqtt.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.constants.MqttConstants;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.mqtt.IMqttReceiverMessageService;

/**
 * MQTT 接收实现
 * 
 * @author zhaoyong
 *
 */
@Service
@Conditional(MqttCondition.class)
public class MqttReceiverMessageServiceImpl implements IMqttReceiverMessageService {

  private final Logger logger = LoggerFactory.getLogger(MqttReceiverMessageServiceImpl.class);

  @Override
  @ServiceActivator(
      inputChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME, 
      outputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME
  )
  public void message(String message) {
    logger.info("Mqtt receive message: {}", message);
  }

}
