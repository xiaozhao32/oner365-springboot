package com.oner365.test.controller.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.oner365.sys.vo.SysLogVo;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SysLogController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysLogControllerTest extends BaseControllerTest {

  private static final String PATH = "/system/log";

  @RepeatedTest(2)
  void list() {
    String url = PATH + "/list";
    Object result = post(url, BodyInserters.fromValue(new SysLogVo()));
    LOGGER.info("list:[{}] -> {}", url, result);
    Assertions.assertNotNull(result);
  }

}
