package com.oner365.queue.config;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
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

import com.oner365.queue.config.properties.MqttProperties;

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
  
  @Autowired
  private MqttProperties mqttProperties;
  
  @Autowired
  private RabbitAdmin rabbitAdmin;

  public static final String IN_BOUND_CHANNEL = "mqttInboundChannel";
  public static final String OUT_BOUND_CHANNEL = "mqttOutboundChannel";
  public static final String CHANNEL_ADAPTER = "_adapter";
  public static final String CHANNEL_PRODUCER = "_producer";
  public static final String SUBCRIPTION = "mqtt-subscription-";
  public static final Integer COMPLETION_TIMEOUT = 5000;
  public static final String QOS_NAME = "qos";
  public static final Integer QOS = 1;

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
    return new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId() + CHANNEL_ADAPTER, mqttPahoClientFactory(),
        mqttProperties.getDefaultTopic());
  }

  @Bean
  public MessageProducer mqttInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
    // 入站投递的通道
    adapter.setOutputChannel(mqttInboundChannel());
    adapter.setCompletionTimeout(COMPLETION_TIMEOUT);
    adapter.setQos(QOS);
    return adapter;
  }

  @Bean
  @ServiceActivator(inputChannel = OUT_BOUND_CHANNEL)
  public MessageHandler mqttOutbound() {
    // clientId不能重复
    MqttPahoMessageHandler handler = new MqttPahoMessageHandler(mqttProperties.getClientId() + CHANNEL_PRODUCER,
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
  
  @PreDestroy
  public void destroy() {

    rabbitAdmin.deleteQueue(SUBCRIPTION + mqttProperties.getClientId() + CHANNEL_PRODUCER + QOS_NAME + QOS);
    rabbitAdmin.deleteQueue(SUBCRIPTION + mqttProperties.getClientId() + CHANNEL_ADAPTER + QOS_NAME + QOS);

  }
}
