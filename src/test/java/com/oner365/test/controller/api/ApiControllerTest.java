package com.oner365.test.controller.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test ApiController
 *
 * @author zhaoyong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest extends BaseControllerTest {

    private static final String PATH = "/api";

    @RepeatedTest(2)
    void cacheRedis() {
        String url = PATH + "/cache/redis/test";
        Object result = get(url);
        logger.info("cacheRedis:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
