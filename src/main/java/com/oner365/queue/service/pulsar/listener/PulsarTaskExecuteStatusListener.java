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

import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;

/**
 * pulsar UpdateTaskExecuteStatusDto listener
 * 
 * @author zhaoyong
 *
 */
@Component
@Conditional(PulsarCondition.class)
public class PulsarTaskExecuteStatusListener implements MessageListener<UpdateTaskExecuteStatusDto> {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(PulsarTaskExecuteStatusListener.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Override
  public void received(Consumer<UpdateTaskExecuteStatusDto> consumer, Message<UpdateTaskExecuteStatusDto> msg) {
    try {
      String data = String.valueOf(msg.getData());
      LOGGER.info("Pulsar consumer data: {}, topic: {}", data, consumer.getTopic());
      consumer.acknowledge(msg);
      
      // business
      
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
  }

}
