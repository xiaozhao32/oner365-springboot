package com.oner365.api.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.oner365.api.service.SseService;

/**
 * SSE Emitter 实现类
 *
 * @author zhaoyong
 */
@Service
public class SseServiceImpl implements SseService {

  private final Logger logger = LoggerFactory.getLogger(SseServiceImpl.class);

  private static final Map<String, SseEmitter> SUBSCRIBE_MAP = new ConcurrentHashMap<>();

  @Override
  public SseEmitter subscribe(String id) {
     SseEmitter sseEmitter = new SseEmitter();
     try {
      sseEmitter.send(SseEmitter.event().reconnectTime(1000).data("welcome: " + id));
       SUBSCRIBE_MAP.put(id, sseEmitter);
     } catch (IOException e) {
       logger.error("SseEmitter connect error", e);
     }
 
     // 连接断开
     sseEmitter.onCompletion(() -> SUBSCRIBE_MAP.remove(id));
 
     // 连接超时
     sseEmitter.onTimeout(() -> SUBSCRIBE_MAP.remove(id));
 
     // 连接报错
     sseEmitter.onError((throwable) -> SUBSCRIBE_MAP.remove(id));
     return sseEmitter;
   }

  @Override
  public Boolean push(String id, String data) {
    try {
      SseEmitter sseEmitter = SUBSCRIBE_MAP.get(id);

      if (sseEmitter != null) {
        sseEmitter.send(SseEmitter.event().name("message").data(data));
        return Boolean.TRUE;
      }
    } catch (Exception e) {
      logger.error("SseEmitter push error", e);
    }
    return Boolean.FALSE;
  }

  @Override
  public Boolean close(String id) {
    try {
      SseEmitter sseEmitter = SUBSCRIBE_MAP.get(id);
      if (sseEmitter != null) {
        sseEmitter.complete();
        SUBSCRIBE_MAP.remove(id);
        return Boolean.TRUE;
      }
    } catch (Exception e) {
      logger.error("SseEmitter close error", e);
    }
    return Boolean.FALSE;
  }

}
