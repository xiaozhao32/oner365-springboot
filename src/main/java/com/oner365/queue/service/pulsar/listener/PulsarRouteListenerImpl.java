package com.oner365.queue.service.pulsar.listener;

import java.util.Arrays;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.oner365.common.service.BaseService;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.queue.condition.PulsarCondition;

import jakarta.annotation.Resource;

/**
 * pulsar Route listener
 *
 * @author zhaoyong
 *
 */
@Service
@Conditional(PulsarCondition.class)
public class PulsarRouteListenerImpl implements MessageListener<String>, BaseService {

  private static final long serialVersionUID = 1L;

  private final Logger logger = LoggerFactory.getLogger(PulsarRouteListenerImpl.class);

  @Resource
  private DynamicRouteService dynamicRouteService;

  @Override
  public void received(Consumer<String> consumer, Message<String> msg) {
    try {
      String data = Arrays.toString(msg.getData());
      logger.info("Pulsar consumer data: {}, topic: {}", data, consumer.getTopic());
      consumer.acknowledge(msg);
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
    // business
    dynamicRouteService.refreshRoute();

  }

}
