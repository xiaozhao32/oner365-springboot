package com.oner365.queue.service.mqtt;

import com.oner365.data.jpa.service.BaseService;

/**
 * MQTT 接收者
 *
 * @author zhaoyong
 *
 */
public interface IMqttReceiverMessageService extends BaseService {

  /**
   * 接收消息
   *
   * @param message 消息内容
   */
  void message(Object message);
}
