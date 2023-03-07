package com.oner365.queue.service.pulsar.consumer;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;
import com.oner365.queue.constants.PulsarConstants;
import com.oner365.queue.service.pulsar.listener.PulsarMessageListener;
import com.oner365.queue.service.pulsar.listener.PulsarRouteListener;

/**
 * pulsar consumer
 * 
 * @author zhaoyong
 *
 */
@Component
@Conditional(PulsarCondition.class)
public class PulsarConsumer {
  
  @Resource
  private PulsarProperties pulsarProperties;
  
  @Resource
  private PulsarClient pulsarClient;
  
  @Resource
  private PulsarMessageListener pulsarMessageListener;
  
  @Resource
  private PulsarRouteListener pulsarRouteListener;
  
  @Bean("getMessageConsumer")
  public Consumer<JSONObject> getMessageConsumer() {
    return createConsumer(PulsarConstants.MESSAGE_QUEUE_NAME, pulsarProperties.getSubscription(),
        pulsarMessageListener, Schema.JSON(JSONObject.class));
  }
  
  @Bean("getRouteConsumer")
  public Consumer<String> getRouteConsumer() {
    return createConsumer(PulsarConstants.ROUTE_QUEUE_NAME, pulsarProperties.getSubscription(),
        pulsarRouteListener, Schema.STRING);
  }
  
  public <T> Consumer<T> createConsumer(String topic, String subscription, MessageListener<T> messageListener,
      Schema<T> schema) {
    try {
      return pulsarClient.newConsumer(schema).topic(topic).subscriptionName(subscription)
          .ackTimeout(10, TimeUnit.SECONDS).subscriptionType(SubscriptionType.Shared).messageListener(messageListener)
          .subscribe();
    } catch (PulsarClientException e) {
      throw new RuntimeException("createConsumer error");
    }
  }

}
