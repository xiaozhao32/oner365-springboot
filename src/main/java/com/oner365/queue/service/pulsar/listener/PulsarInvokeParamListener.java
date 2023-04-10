package com.oner365.queue.service.pulsar.listener;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;

/**
 * pulsar InvokeParamDto listener
 * 
 * @author zhaoyong
 *
 */
@Component
@Conditional(PulsarCondition.class)
public class PulsarInvokeParamListener implements MessageListener<InvokeParamDto> {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(PulsarInvokeParamListener.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Override
  public void received(Consumer<InvokeParamDto> consumer, Message<InvokeParamDto> msg) {
    try {
      String data = String.valueOf(msg.getData());
      LOGGER.info("Pulsar consumer data: {}, topic: {}", data, consumer.getTopic());
      consumer.acknowledge(msg);
      
      // bussiness
      
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
  }

}
