package com.oner365.test.controller.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;

import com.oner365.sys.vo.SysLogVo;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SysLogController
 *
 * @author zhaoyong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SysLogControllerTest extends BaseControllerTest {

  private static final String PATH = "/system/log";

  @RepeatedTest(2)
  void list() {
    String url = PATH + "/list";
    Object result = post(url, BodyInserters.fromValue(new SysLogVo()));
    logger.info("list:[{}] -> {}", url, result);
    Assertions.assertNotNull(result);
  }

}
