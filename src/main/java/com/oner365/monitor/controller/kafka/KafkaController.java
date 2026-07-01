package com.oner365.monitor.controller.kafka;

import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.web.controller.BaseController;
import com.oner365.queue.condition.KafkaCondition;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Kafka MQ监控
 *
 * @author zhaoyong
 *
 */
@RestController
@Tag(name = "监控 - Kafka")
@RequestMapping("/monitor/kafka")
@Conditional(KafkaCondition.class)
public class KafkaController extends BaseController {

    /**
     * 首页
     * @return JSONObject
     */
    @Operation(summary = "1.首页")
    @ApiOperationSupport(order = 1)
    @GetMapping("/index")
    public String index() {
        return "success";
    }
}
