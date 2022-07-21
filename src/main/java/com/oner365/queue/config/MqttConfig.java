package com.oner365.queue.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

import com.oner365.queue.config.properties.MqttProperties;

/**
 * MQTT config
 * 
 * @author zhaoyong
 *
 */
@Configuration
public class MqttConfig {

  @Autowired
  private MqttProperties mqttProperties;

  public static final String IN_BOUND_CHANNEL = "mqttInboundChannel";
  public static final String OUT_BOUND_CHANNEL = "mqttOutboundChannel";

  @Bean
  public MqttPahoClientFactory mqttPahoClientFactory() {
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
  public MqttPahoMessageDrivenChannelAdapter adapter() {
    // clientId不能重复
    return new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId() + "_adapter", mqttPahoClientFactory(),
        mqttProperties.getDefaultTopic());
  }

  @Bean
  public MessageProducer mqttInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
    adapter.setCompletionTimeout(5000);
    // 入站投递的通道
    adapter.setOutputChannel(mqttInboundChannel());
    adapter.setQos(1);
    return adapter;
  }

  @Bean
  @ServiceActivator(inputChannel = OUT_BOUND_CHANNEL)
  public MessageHandler mqttOutbound() {
    // clientId不能重复
    MqttPahoMessageHandler handler = new MqttPahoMessageHandler(mqttProperties.getClientId() + "_producer",
        mqttPahoClientFactory());
    handler.setAsync(true);
    handler.setDefaultTopic(mqttProperties.getDefaultTopic());
    return handler;
  }

  @Bean(name = OUT_BOUND_CHANNEL)
  public MessageChannel mqttOutboundChannel() {
    return new DirectChannel();
  }

  @Bean(name = IN_BOUND_CHANNEL)
  public MessageChannel mqttInboundChannel() {
    return new DirectChannel();
  }

}
