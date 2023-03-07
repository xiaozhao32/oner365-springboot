package com.oner365.queue.service.mqtt;

import org.springframework.context.annotation.Conditional;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.oner365.queue.condition.MqttCondition;
import com.oner365.queue.constants.MqttConstants;

/**
 * MQTT 发送者
 *
 * @author zhaoyong
 *
 */
@Component
@Conditional(MqttCondition.class)
@MessagingGateway(defaultRequestChannel = MqttConstants.OUT_BOUND_CHANNEL)
public interface IMqttSendService {

  /**
   * 发送消息 默认topic
   *
   * @param message 发送内容
   */
  void sendMessage(String message);

  /**
   * 发送消息 指定topic
   *
   * @param topic   topic
   * @param message 发送内容
   */
  void sendMessage(@Header(MqttHeaders.TOPIC) String topic, String message);

  /**
   * 发送消息 指定topic 指定qos
   *
   * @param topic   topic
   * @param qos     qos
   * @param message 发送内容
   */
  void sendMessage(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String message);
}
