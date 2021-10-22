package com.oner365.test.controller.datasource;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * Test DataSourceController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
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
