package com.oner365.rabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/rabbitmq")
@Api(tags = "Rabbitmq 测试")
public class RabbitmqTestController extends BaseController {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @GetMapping("/send")
    @ApiOperation("测试发送")
    public JSONObject send(String data) {
        JSONObject json = new JSONObject();
        json.put("data", data);
        rabbitTemplate.convertAndSend(RabbitmqConstants.QUEUE_TYPE, RabbitmqConstants.QUEUE_KEY, data);
        return json;
    }
}
