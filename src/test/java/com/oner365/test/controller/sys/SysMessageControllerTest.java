package com.oner365.test.controller.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SysMessageController
 *
 * @author zhaoyong
 *
 */
class SysMessageControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/message";

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/list";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        logger.info("list:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
