package com.oner365.test.controller.rabbitmq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test RabbitmqTestController
 *
 * @author zhaoyong
 *
 */
@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RabbitmqTestControllerTest extends BaseControllerTest {

    private static final String PATH = "/queue";

    @Test
    void send() {
        String url = PATH + "/send?data=hello" + System.currentTimeMillis();
        Object result = get(url);
        logger.info("send:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
