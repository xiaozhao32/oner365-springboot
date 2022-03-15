package com.oner365.test.controller.elasticsearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test Elasticsearch Info Controller
 *
 * @author zhaoyong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchInfoControllerTest extends BaseControllerTest {

  private static final String PATH = "/elasticsearch/info";

  @Test
  void index() {
      String url = PATH + "/index";
      Object result = get(url);
      logger.info("index:[{}] -> {}", url, result);
      Assertions.assertNotNull(result);
  }
}
