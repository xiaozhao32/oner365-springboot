package com.oner365.rabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.controller.BaseController;
import com.oner365.rabbitmq.constants.RabbitmqConstants;

/**
 * rabbitmq controller
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitmqTestController extends BaseController {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @GetMapping("/send")
    public JSONObject send(String data) {
        JSONObject json = new JSONObject();
        json.put("data", data);
        rabbitTemplate.convertAndSend(RabbitmqConstants.QUEUE_TYPE, RabbitmqConstants.QUEUE_KEY, data);
        return json;
    }
}
