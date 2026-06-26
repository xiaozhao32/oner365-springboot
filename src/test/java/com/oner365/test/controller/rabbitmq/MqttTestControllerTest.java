package com.oner365.test.controller.rabbitmq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test MQTT TestController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MqttTestControllerTest extends BaseControllerTest {

    private static final String PATH = "/queue";

    @RepeatedTest(2)
    void send() {
        String url = PATH + "/send?data=hello" + System.currentTimeMillis();
        Object result = get(url);
        logger.info("send:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
