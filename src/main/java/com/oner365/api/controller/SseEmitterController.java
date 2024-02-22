package com.oner365.api.controller;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.api.dto.Message;
import com.oner365.api.service.SseService;
import com.oner365.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * SSE Emitter 接口测试
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "SSE Emitter公共接口")
@RequestMapping("/sse/emitter")
public class SseEmitterController extends BaseController {

  @Resource
  private SseService sseService;

  /**
   * 创建SSE连接
   *
   * @return SseEmitter
   */
  @ApiOperation("1.创建SSE连接")
  @ApiOperationSupport(order = 1)
  @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter sse() {
    String uuid = UUID.randomUUID().toString();
    logger.info("新用户连接：{}", uuid);
    return sseService.connect(uuid);
  }

  /**
   * 广播消息
   *
   * @param message 消息体
   */
  @ApiOperation("2.广播消息")
  @ApiOperationSupport(order = 2)
  @PostMapping("/send")
  public void sendMessage(@RequestBody Message message) {
    sseService.sendMessage(message);
  }
}
