package com.oner365.test.controller.gateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test DynamicRouteController
 *
 * @author zhaoyong
 *
 */
class DynamicRouteControllerTest extends BaseControllerTest {

    private static final String PATH = "/route";

    @Test
    void get() {
        String url = PATH + "/get/oner365-system";
        Object result = get(url);
        logger.info("get:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/list";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        logger.info("list:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
