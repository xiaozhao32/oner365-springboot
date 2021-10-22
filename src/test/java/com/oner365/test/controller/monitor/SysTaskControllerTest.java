package com.oner365.test.controller.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SysTaskController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysTaskControllerTest extends BaseControllerTest {

    private static final String PATH = "/monitor/task";

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/list";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("list:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
