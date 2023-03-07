package com.oner365.queue.service.mqtt;

/**
 * MQTT 接收者
 *
 * @author zhaoyong
 *
 */
public interface IMqttReceiverService {

  /**
   * 接收消息
   *
   * @param message 消息内容
   */
  void message(Object message);
}
