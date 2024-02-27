package com.oner365.api.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.api.service.SseService;
import com.oner365.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;

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
   * 订阅
   *
   * @return SseEmitter
   */
  @ApiOperation("1.订阅")
  @ApiOperationSupport(order = 1)
  @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter subscribe() {
    String uuid = UUID.randomUUID().toString();
    logger.info("新用户连接: {}", uuid);
    return sseService.subscribe(uuid);
  }

  /**
   * 发送消息
   *
   * @param id   主键uuid
   * @param data 消息体
   * @return 是否成功
   */
  @ApiOperation("2.广播消息")
  @ApiOperationSupport(order = 2)
  @GetMapping(path = "/push")
  public Boolean push(String id, String data) {
    return sseService.push(id, data);
  }

  /**
   * 关闭连接
   *
   * @param id   主键uuid
   * @return 是否成功
   */
  @ApiOperation("3.关闭连接")
  @ApiOperationSupport(order = 3)
  @GetMapping("/close")
  public Boolean close(String id) {
    logger.info("关闭连接: {}", id);
    return sseService.close(id);
  }
}
