package com.oner365.queue.config;

import jakarta.annotation.Resource;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

import com.oner365.data.commons.enums.QueueEnum;
import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.config.properties.MqttProperties;
import com.oner365.queue.constants.MqttConstants;
import com.oner365.queue.constants.QueueConstants;

/**
 * MQTT config
 *
 * @author zhaoyong
 *
 */
@Configuration
@Conditional(MqttCondition.class)
@EnableConfigurationProperties({ MqttProperties.class })
@EnableIntegration
@IntegrationComponentScan
public class MqttConfig {

    @Resource
    private MqttProperties mqttProperties;

    public MqttConfig() {
        Logger logger = LoggerFactory.getLogger(MqttConfig.class);
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
                mqttPahoClientFactory(), QueueConstants.MESSAGE_QUEUE_NAME, QueueConstants.ROUTE_QUEUE_NAME,
                QueueConstants.SAVE_TASK_LOG_QUEUE_NAME, QueueConstants.SCHEDULE_TASK_QUEUE_NAME,
                QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME);
    }

    @Bean
    MessageProducer messageInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
        // 入站投递的通道
        adapter.setOutputChannel(messageInboundChannel());
        adapter.setCompletionTimeout(MqttConstants.COMPLETION_TIMEOUT);
        adapter.setQos(MqttConstants.QOS);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME)
    MessageHandler messageOutbound() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                mqttProperties.getClientId() + MqttConstants.CHANNEL_PRODUCER, mqttPahoClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic(QueueConstants.MESSAGE_QUEUE_NAME);
        return handler;
    }

    @Bean(name = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME)
    MessageChannel messageOutboundChannel() {
        return new DirectChannel();
    }

    @Bean(name = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME)
    MessageChannel messageInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageProducer routeInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
        // 入站投递的通道
        adapter.setOutputChannel(routeInboundChannel());
        adapter.setCompletionTimeout(MqttConstants.COMPLETION_TIMEOUT);
        adapter.setQos(MqttConstants.QOS);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.ROUTE_QUEUE_NAME)
    MessageHandler routeOutbound() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                mqttProperties.getClientId() + MqttConstants.CHANNEL_PRODUCER, mqttPahoClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic(QueueConstants.ROUTE_QUEUE_NAME);
        return handler;
    }

    @Bean(name = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.ROUTE_QUEUE_NAME)
    MessageChannel routeOutboundChannel() {
        return new DirectChannel();
    }

    @Bean(name = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.ROUTE_QUEUE_NAME)
    MessageChannel routeInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageProducer saveTaskLogInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
        // 入站投递的通道
        adapter.setOutputChannel(saveTaskLogInboundChannel());
        adapter.setCompletionTimeout(MqttConstants.COMPLETION_TIMEOUT);
        adapter.setQos(MqttConstants.QOS);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.SAVE_TASK_LOG_QUEUE_NAME)
    MessageHandler saveTaskLogOutbound() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                mqttProperties.getClientId() + MqttConstants.CHANNEL_PRODUCER, mqttPahoClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic(QueueConstants.SAVE_TASK_LOG_QUEUE_NAME);
        return handler;
    }

    @Bean(name = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.SAVE_TASK_LOG_QUEUE_NAME)
    MessageChannel saveTaskLogOutboundChannel() {
        return new DirectChannel();
    }

    @Bean(name = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.SAVE_TASK_LOG_QUEUE_NAME)
    MessageChannel saveTaskLogInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageProducer scheduleTaskInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
        // 入站投递的通道
        adapter.setOutputChannel(scheduleTaskInboundChannel());
        adapter.setCompletionTimeout(MqttConstants.COMPLETION_TIMEOUT);
        adapter.setQos(MqttConstants.QOS);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.SCHEDULE_TASK_QUEUE_NAME)
    MessageHandler scheduleTaskOutbound() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                mqttProperties.getClientId() + MqttConstants.CHANNEL_PRODUCER, mqttPahoClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic(QueueConstants.SCHEDULE_TASK_QUEUE_NAME);
        return handler;
    }

    @Bean(name = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.SCHEDULE_TASK_QUEUE_NAME)
    MessageChannel scheduleTaskOutboundChannel() {
        return new DirectChannel();
    }

    @Bean(name = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.SCHEDULE_TASK_QUEUE_NAME)
    MessageChannel scheduleTaskInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageProducer updateStatusInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
        // 入站投递的通道
        adapter.setOutputChannel(updateStatusInboundChannel());
        adapter.setCompletionTimeout(MqttConstants.COMPLETION_TIMEOUT);
        adapter.setQos(MqttConstants.QOS);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME)
    MessageHandler updateStatusOutbound() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                mqttProperties.getClientId() + MqttConstants.CHANNEL_PRODUCER, mqttPahoClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic(QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME);
        return handler;
    }

    @Bean(name = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME)
    MessageChannel updateStatusOutboundChannel() {
        return new DirectChannel();
    }

    @Bean(name = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME)
    MessageChannel updateStatusInboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.SCHEDULE_TASK_QUEUE_NAME)
    public interface IMqttSendInvokeParamService {

        /**
         * 发送消息 默认topic
         * @param message 发送内容
         */
        void sendMessage(String message);

        /**
         * 发送消息 指定topic
         * @param topic topic
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, String message);

        /**
         * 发送消息 指定topic 指定qos
         * @param topic topic
         * @param qos qos
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String message);

    }

    @MessagingGateway(defaultRequestChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME)
    public interface IMqttSendMessageService {

        /**
         * 发送消息 默认topic
         * @param message 发送内容
         */
        void sendMessage(String message);

        /**
         * 发送消息 指定topic
         * @param topic topic
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, String message);

        /**
         * 发送消息 指定topic 指定qos
         * @param topic topic
         * @param qos qos
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String message);

    }

    @MessagingGateway(defaultRequestChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.ROUTE_QUEUE_NAME)
    public interface IMqttSendRouteService {

        /**
         * 发送消息 默认topic
         * @param message 发送内容
         */
        void sendMessage(String message);

        /**
         * 发送消息 指定topic
         * @param topic topic
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, String message);

        /**
         * 发送消息 指定topic 指定qos
         * @param topic topic
         * @param qos qos
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String message);

    }

    @MessagingGateway(
            defaultRequestChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.TASK_UPDATE_STATUS_QUEUE_NAME)
    public interface IMqttSendTaskExecuteStatusService {

        /**
         * 发送消息 默认topic
         * @param message 发送内容
         */
        void sendMessage(String message);

        /**
         * 发送消息 指定topic
         * @param topic topic
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, String message);

        /**
         * 发送消息 指定topic 指定qos
         * @param topic topic
         * @param qos qos
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String message);

    }

    @MessagingGateway(defaultRequestChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.SAVE_TASK_LOG_QUEUE_NAME)
    public interface IMqttSendTaskLogService {

        /**
         * 发送消息 默认topic
         * @param message 发送内容
         */
        void sendMessage(String message);

        /**
         * 发送消息 指定topic
         * @param topic topic
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, String message);

        /**
         * 发送消息 指定topic 指定qos
         * @param topic topic
         * @param qos qos
         * @param message 发送内容
         */
        void sendMessage(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String message);

    }

}
