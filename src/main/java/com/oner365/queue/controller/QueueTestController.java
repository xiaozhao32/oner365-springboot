package com.oner365.queue.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.web.controller.BaseController;
import com.oner365.queue.service.IQueueSendService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.json.Json;
import jakarta.json.JsonObject;

/**
 * 队列 controller
 *
 * @author zhaoyong
 *
 */
@RestController
@Tag(name = "队列测试")
@RequestMapping("/queue")
public class QueueTestController extends BaseController {

    @Resource
    private IQueueSendService service;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试发送
     * @param data 参数
     * @return JSONObject
     */
    @Operation(summary = "1.测试发送")
    @ApiOperationSupport(order = 1)
    @GetMapping("/send")
    public JsonObject send(String data) {
        JsonObject json = Json.createObjectBuilder().add("data", data).build();
        service.sendMessage(json.toString());
        service.syncRoute();
        return json;
    }

    @Operation(summary = "2.测试订阅")
    @ApiOperationSupport(order = 2)
    @GetMapping("/subscribe")
    public JsonObject subscribe(String data) {
        // 订阅
        Long result = redisTemplate.convertAndSend(PublicConstants.NAME, data);

        return Json.createObjectBuilder()
                .add("data", data)
                .add("result", result)
                .build();
    }

}
