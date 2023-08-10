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
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;
import com.oner365.api.dto.UpdateTaskExecuteStatusDto;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.monitor.dto.InvokeParamDto;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.queue.condition.PulsarCondition;
import com.oner365.queue.config.properties.PulsarProperties;
import com.oner365.queue.constants.QueueConstants;
import com.oner365.queue.service.pulsar.listener.PulsarInvokeParamListener;
import com.oner365.queue.service.pulsar.listener.PulsarMessageListener;
import com.oner365.queue.service.pulsar.listener.PulsarRouteListener;
import com.oner365.queue.service.pulsar.listener.PulsarTaskExecuteStatusListener;
import com.oner365.queue.service.pulsar.listener.PulsarTaskLogListener;

/**
 * pulsar consumer
 * 
 * @author zhaoyong
 *
 */
@Configuration
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

  @Resource
  private PulsarInvokeParamListener pulsarInvokeParamListener;

  @Resource
  private PulsarTaskExecuteStatusListener pulsarTaskExecuteStatusListener;

  @Resource
  private PulsarTaskLogListener pulsarTaskLogListener;

  @Bean("getMessageConsumer")
  Consumer<JSONObject> getMessageConsumer() {
    return createConsumer(QueueConstants.MESSAGE_QUEUE_NAME, pulsarProperties.getSubscription(), pulsarMessageListener,
        Schema.JSON(JSONObject.class));
  }

  @Bean("getRouteConsumer")
  Consumer<String> getRouteConsumer() {
    return createConsumer(QueueConstants.ROUTE_QUEUE_NAME, pulsarProperties.getSubscription(), pulsarRouteListener,
        Schema.STRING);
  }

  @Bean("getInvokeParamConsumer")
  Consumer<InvokeParamDto> getInvokeParamConsumer() {
    return createConsumer(QueueConstants.SCHEDULE_TASK_QUEUE_NAME, pulsarProperties.getSubscription(),
        pulsarInvokeParamListener, Schema.JSON(InvokeParamDto.class));
  }

  @Bean("getTaskExecuteStatusConsumer")
  Consumer<UpdateTaskExecuteStatusDto> getTaskExecuteStatusConsumer() {
    return createConsumer(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME, pulsarProperties.getSubscription(),
        pulsarTaskExecuteStatusListener, Schema.JSON(UpdateTaskExecuteStatusDto.class));
  }

  @Bean("getTaskLogConsumer")
  Consumer<SysTaskDto> getTaskLogConsumer() {
    return createConsumer(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, pulsarProperties.getSubscription(),
        pulsarTaskLogListener, Schema.JSON(SysTaskDto.class));
  }

  public <T> Consumer<T> createConsumer(String topic, String subscription, MessageListener<T> messageListener,
      Schema<T> schema) {
    try {
      return pulsarClient.newConsumer(schema).topic(topic).subscriptionName(subscription)
          .ackTimeout(10, TimeUnit.SECONDS).subscriptionType(SubscriptionType.Shared).messageListener(messageListener)
          .subscribe();
    } catch (PulsarClientException e) {
      throw new ProjectRuntimeException("createConsumer error");
    }
  }

}
