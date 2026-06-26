package com.oner365.test.controller.statemachine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test StatemachineController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderStatemachineTestControllerTest extends BaseControllerTest {

    private static final String PATH = "/statemachine/order";

    @RepeatedTest(2)
    void send() {
        String url = PATH + "/test?orderId=" + 12345;
        Object result = get(url);
        logger.info("首次请求返回true, 再次请求返回false.");
        logger.info("send:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
