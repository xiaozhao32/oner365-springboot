package com.oner365.test.controller.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SysTaskController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SysTaskControllerTest extends BaseControllerTest {

    private static final String PATH = "/monitor/task";

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/page";
        Object result = post(url, BodyInserters.fromValue(objectMapper.createObjectNode()));
        logger.info("page:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
