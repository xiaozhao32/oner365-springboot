package com.oner365.test.controller.datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test DataSourceController
 *
 * @author zhaoyong
 *
 */
class DataSourceControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/datasource";

    @Test
    void get() {
        String url = PATH + "/get/1";
        Object result = get(url);
        LOGGER.info("get:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/list";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("list:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
