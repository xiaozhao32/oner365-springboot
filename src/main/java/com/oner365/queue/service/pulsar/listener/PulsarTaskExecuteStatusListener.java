package com.oner365.queue.service.pulsar.listener;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
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

  private final Logger logger = LoggerFactory.getLogger(PulsarTaskExecuteStatusListener.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Resource
  private PulsarClient pulsarClient;

  @Override
  public void received(Consumer<UpdateTaskExecuteStatusDto> consumer, Message<UpdateTaskExecuteStatusDto> msg) {
    try {
      logger.info("Pulsar consumer data: {}, topic: {}", new String(msg.getData()), consumer.getTopic());
      consumer.acknowledge(msg);
      
      // business
      
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
  }

}
