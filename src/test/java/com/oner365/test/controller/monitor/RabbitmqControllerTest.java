package com.oner365.test.controller.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test RabbitmqController
 *
 * @author zhaoyong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RabbitmqControllerTest extends BaseControllerTest {

    private static final String PATH = "/monitor/rabbitmq";

    @Test
    void index() {
        String url = PATH + "/index";
        Object result = get(url);
        logger.info("index:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }
    
    @Test
    void list() {
      String url = PATH + "/list/EXCHANGES?pageIndex=1&pageSize=5";
      Object result = get(url);
      logger.info("list:[{}] -> {}", url, result);
      Assertions.assertNotNull(result);
    }
    
}
