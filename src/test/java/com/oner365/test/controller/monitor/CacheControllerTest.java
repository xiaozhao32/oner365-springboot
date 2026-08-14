package com.oner365.test.controller.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test CacheController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CacheControllerTest extends BaseControllerTest {

    private static final String PATH = "/monitor/cache";

    @Test
    void testIndex() {
        String url = PATH + "/index";
        Object result = get(url);
        logger.info("index:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @Test
    void testList() {
        String url = PATH + "/list";
        Object result = get(url);
        logger.info("list:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @Test
    void testClean() {
        String url = PATH + "/clean?index=5";
        Object result = get(url);
        logger.info("clean:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
