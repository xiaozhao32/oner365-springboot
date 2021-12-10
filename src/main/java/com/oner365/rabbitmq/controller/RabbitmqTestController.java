package com.oner365.rabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.controller.BaseController;
import com.oner365.rabbitmq.constants.RabbitmqConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * rabbitmq controller
 * 
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "Rabbitmq 测试")
@RequestMapping("/rabbitmq")
public class RabbitmqTestController extends BaseController {

  @Autowired
  private RabbitTemplate rabbitTemplate;

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
    rabbitTemplate.convertAndSend(RabbitmqConstants.QUEUE_TYPE, RabbitmqConstants.QUEUE_KEY, data);
    return json;
  }
}
