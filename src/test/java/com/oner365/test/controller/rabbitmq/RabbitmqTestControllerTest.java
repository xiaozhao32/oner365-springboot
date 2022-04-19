package com.oner365.test.controller.rabbitmq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test RabbitmqTestController
 *
 * @author zhaoyong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RabbitmqTestControllerTest extends BaseControllerTest {

  private static final String PATH = "/rabbitmq";

  @RepeatedTest(2)
  void send() {
      String url = PATH + "/send?data=hello";
      Object result = get(url);
      logger.info("send:[{}] -> {}", url, result);
      Assertions.assertNotNull(result);
  }

}