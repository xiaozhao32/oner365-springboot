package com.oner365.api.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.oner365.api.dto.Message;

/**
 * SSE Emitter 接口
 *
 * @author zhaoyong
 */
public interface SseService {

  /**
   * 创建SSE连接
   * 
   * @param uuid 标识
   * @return SseEmitter
   */
  SseEmitter connect(String uuid);

  /**
   * 发送内容
   * 
   * @param message 消息
   */
  void sendMessage(Message message);

}
