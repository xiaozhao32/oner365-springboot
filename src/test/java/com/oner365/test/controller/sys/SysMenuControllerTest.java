package com.oner365.test.controller.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SysMenuController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysMenuControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/menu";

    @Test
    public void get() {
        String url = PATH + "/get/101";
        Object result = get(url);
        LOGGER.info("get:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    public void list() {
        String url = PATH + "/list";
        JSONObject paramJson = new JSONObject();
        paramJson.put("menuTypeId", "1");
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("list:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
