package com.oner365.api.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.alibaba.fastjson.JSON;
import com.oner365.api.dto.Message;
import com.oner365.api.service.SseService;

/**
 * SSE Emitter 实现类
 *
 * @author zhaoyong
 */
@Service
public class SseServiceImpl implements SseService {

  private final Logger logger = LoggerFactory.getLogger(SseServiceImpl.class);

  private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

  @Async
  @Override
  public SseEmitter connect(String uuid) {
    SseEmitter sseEmitter = new SseEmitter();
    try {
      sseEmitter.send(SseEmitter.event().comment("welcome"));
    } catch (IOException e) {
      logger.error("SseEmitter connect error", e);
    }

    // 连接断开
    sseEmitter.onCompletion(() -> {
      sseEmitterMap.remove(uuid);
    });

    // 连接超时
    sseEmitter.onTimeout(() -> {
      sseEmitterMap.remove(uuid);
    });

    // 连接报错
    sseEmitter.onError((throwable) -> {
      sseEmitterMap.remove(uuid);
    });

    sseEmitterMap.put(uuid, sseEmitter);
    return sseEmitter;
  }

  @Override
  public void sendMessage(Message message) {
    logger.info("SseEmitter Map:{}", sseEmitterMap);
    logger.info("SseEmitter Message:{}", JSON.toJSONString(message));
    message.setTotal(sseEmitterMap.size());

    sseEmitterMap.forEach((uuid, sseEmitter) -> {
      try {
        sseEmitter.send(message, MediaType.APPLICATION_JSON);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

}
