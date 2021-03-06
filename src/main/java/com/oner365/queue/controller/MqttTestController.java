package com.oner365.queue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.controller.BaseController;
import com.oner365.queue.config.MqttCondition;
import com.oner365.queue.service.IMqttSendService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * MQTT controller
 * 
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "MQTT测试")
@RequestMapping("/mqtt")
@Conditional(MqttCondition.class)
public class MqttTestController extends BaseController {
  
  @Autowired
  private IMqttSendService service;

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
