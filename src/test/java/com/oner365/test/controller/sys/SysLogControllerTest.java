package com.oner365.test.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * Test SysLogController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysLogControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/log";

    @Test
    public void get() {
        String url = PATH + "/get/4028b88177d778810177d778c7190000";
        Object result = get(url);
        LOGGER.info("get:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    public void list() {
        String url = PATH + "/list";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("list:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
