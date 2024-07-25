package com.oner365.api.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * SSE Emitter 接口
 *
 * @author zhaoyong
 */
public interface SseService {

  /**
   * 订阅
   * 
   * @param id 主键
   * @return SseEmitter
   */
  SseEmitter subscribe(String id);

  /**
   * 发送内容
   * 
   * @param id   主键
   * @param data 消息
   * @return 是否成功
   */
  Boolean push(String id, String data);

  /**
   * 关闭
   * 
   * @param id 主键
   * @return 是否成功
   */
  Boolean close(String id);

}
