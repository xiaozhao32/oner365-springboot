package com.oner365.test.controller.datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test DataSourceController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataSourceControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/datasource";

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/page";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        logger.info("page:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
