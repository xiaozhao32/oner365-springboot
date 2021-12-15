package com.oner365.test.controller.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.page.PageInfo;
import com.oner365.sys.dto.SysLogDto;
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

  @Test
  void get() {
    String url = PATH + "/list";
    JSONObject paramJson = new JSONObject();
    @SuppressWarnings("unchecked")
    PageInfo<SysLogDto> page = post(url, BodyInserters.fromValue(paramJson), PageInfo.class);
    if (page != null && !page.getContent().isEmpty()) {
      String getUrl = PATH + "/get/"+page.getContent().get(0).getId();
      Object getResult = get(getUrl);
      LOGGER.info("get:[{}] -> {}", url, getResult);
      Assertions.assertNotNull(getResult);
    }
  }

  @RepeatedTest(2)
  void list() {
    String url = PATH + "/list";
    JSONObject paramJson = new JSONObject();
    Object result = post(url, BodyInserters.fromValue(paramJson));
    LOGGER.info("list:[{}] -> {}", url, result);
    Assertions.assertNotNull(result);
  }

}
