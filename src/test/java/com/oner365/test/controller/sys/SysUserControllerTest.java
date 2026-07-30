package com.oner365.test.controller.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SysUserController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SysUserControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/user";

    @Test
    void get() {
        String url = PATH + "/get/1";
        Object result = get(url);
        logger.info("get:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/page";
        Object result = post(url, BodyInserters.fromValue(objectMapper.createObjectNode()));
        logger.info("page:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
