package com.oner365.queue.config;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.oner365.common.enums.QueueEnum;
import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.config.properties.MqttProperties;
import com.oner365.queue.constants.MqttConstants;

/**
 * MQTT config
 * 
 * @author zhaoyong
 *
 */
@Configuration
@Conditional(MqttCondition.class)
@EnableConfigurationProperties({ MqttProperties.class })
public class MqttConfig {
  
  private final Logger logger = LoggerFactory.getLogger(MqttConfig.class);

  @Autowired
  private MqttProperties mqttProperties;

  @Autowired
  private RabbitAdmin rabbitAdmin;
  
  public MqttConfig() {
    logger.info("Queue Type: {}", QueueEnum.MQTT);
  }

  @Bean
  MqttPahoClientFactory mqttPahoClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    options.setCleanSession(false);
    options.setServerURIs(mqttProperties.getUri().split(","));
    options.setUserName(mqttProperties.getUsername());
    options.setPassword(mqttProperties.getPassword().toCharArray());
    factory.setConnectionOptions(options);
    return factory;
  }

  @Bean
  MqttPahoMessageDrivenChannelAdapter adapter() {
    // clientId不能重复
    return new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId() + MqttConstants.CHANNEL_ADAPTER,
        mqttPahoClientFactory(), mqttProperties.getDefaultTopic());
  }

  @Bean
  MessageProducer mqttInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
    // 入站投递的通道
    adapter.setOutputChannel(mqttInboundChannel());
    adapter.setCompletionTimeout(MqttConstants.COMPLETION_TIMEOUT);
    adapter.setQos(MqttConstants.QOS);
    return adapter;
  }

  @Bean
  @ServiceActivator(inputChannel = MqttConstants.OUT_BOUND_CHANNEL)
  MessageHandler mqttOutbound() {
    // clientId不能重复
    MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
        mqttProperties.getClientId() + MqttConstants.CHANNEL_PRODUCER, mqttPahoClientFactory());
    handler.setAsync(true);
    handler.setDefaultTopic(mqttProperties.getDefaultTopic());
    return handler;
  }

  @Bean(name = MqttConstants.OUT_BOUND_CHANNEL)
  MessageChannel mqttOutboundChannel() {
    return new DirectChannel();
  }

  @Bean(name = MqttConstants.IN_BOUND_CHANNEL)
  MessageChannel mqttInboundChannel() {
    return new DirectChannel();
  }

  @PreDestroy
  public void destroy() {

    rabbitAdmin.deleteQueue(MqttConstants.SUBCRIPTION + mqttProperties.getClientId() + MqttConstants.CHANNEL_PRODUCER
        + MqttConstants.QOS_NAME + MqttConstants.QOS);
    rabbitAdmin.deleteQueue(MqttConstants.SUBCRIPTION + mqttProperties.getClientId() + MqttConstants.CHANNEL_ADAPTER
        + MqttConstants.QOS_NAME + MqttConstants.QOS);

  }
}
