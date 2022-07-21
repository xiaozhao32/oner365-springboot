package com.oner365.queue.service;

/**
 * MQTT 接收者
 * 
 * @author zhaoyong
 *
 */
public interface IMqttReceiverService {

  /**
   * 接收消息
   */
  void message(Object message);
}
