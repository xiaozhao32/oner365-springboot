package com.oner365.queue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.controller.BaseController;
import com.oner365.queue.service.IQueueSendService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 队列 controller
 * 
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "队列测试")
@RequestMapping("/queue")
public class QueueTestController extends BaseController {
  
  @Autowired
  private IQueueSendService service;

  /**
   * 测试发送
   * 
   * @param data 参数
   * @return JSONObject
   */
  @ApiOperation("1.测试发送")
  @ApiOperationSupport(order = 1)
  @GetMapping("/send")
  public JSONObject send(String data) {
    JSONObject json = new JSONObject();
    json.put("data", data);
    service.sendMessage(data);
    return json;
  }
}
