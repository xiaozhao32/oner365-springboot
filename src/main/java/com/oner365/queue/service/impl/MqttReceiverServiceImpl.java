package com.oner365.queue.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.oner365.queue.config.MqttCondition;
import com.oner365.queue.config.MqttConfig;
import com.oner365.queue.service.IMqttReceiverService;

/**
 * MQTT 接收实现
 * 
 * @author zhaoyong
 *
 */
@Service
@Conditional(MqttCondition.class)
public class MqttReceiverServiceImpl implements IMqttReceiverService {

  private final Logger logger = LoggerFactory.getLogger(MqttReceiverServiceImpl.class);

  @Override
  @ServiceActivator(inputChannel = MqttConfig.IN_BOUND_CHANNEL, outputChannel = MqttConfig.OUT_BOUND_CHANNEL)
  public void message(Object message) {
    logger.info("MQTT Message: {}", message);
  }

}
