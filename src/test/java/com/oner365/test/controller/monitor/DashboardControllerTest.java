package com.oner365.test.controller.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test DashboardController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DashboardControllerTest extends BaseControllerTest {

    private static final String PATH = "/monitor/dashboard";

    @Test
    void index() {
        String url = PATH + "/index";
        Object result = get(url);
        logger.info("index:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
