package com.oner365.queue.service.pulsar.listener;

import jakarta.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;

/**
 * pulsar message listener
 * 
 * @author zhaoyong
 *
 */
@Component
@Conditional(PulsarCondition.class)
public class PulsarMessageListener implements MessageListener<JSONObject> {

  private static final long serialVersionUID = 1L;

  private final Logger logger = LoggerFactory.getLogger(PulsarMessageListener.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Override
  public void received(Consumer<JSONObject> consumer, Message<JSONObject> msg) {
    try {
      String data = String.valueOf(msg.getData());
      logger.info("Pulsar consumer data: {}, topic: {}", data, consumer.getTopic());
      consumer.acknowledge(msg);
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
  }

}
