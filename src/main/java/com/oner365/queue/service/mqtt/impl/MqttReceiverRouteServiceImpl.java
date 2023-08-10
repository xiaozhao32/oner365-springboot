package com.oner365.queue.service.mqtt.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.constants.MqttConstants;
import com.oner365.queue.service.mqtt.IMqttReceiverRouteService;

/**
 * MQTT 接收实现
 * 
 * @author zhaoyong
 *
 */
@Service
@Conditional(MqttCondition.class)
public class MqttReceiverRouteServiceImpl implements IMqttReceiverRouteService {

  private final Logger logger = LoggerFactory.getLogger(MqttReceiverRouteServiceImpl.class);

  @Resource
  private DynamicRouteService dynamicRouteService;

  @Override
  @ServiceActivator(inputChannel = MqttConstants.IN_BOUND_CHANNEL, outputChannel = MqttConstants.OUT_BOUND_CHANNEL)
  public void message(Object message) {
    logger.info("Mqtt receive syncRoute: {}", message);
    // business
    dynamicRouteService.refreshRoute();
  }

}
