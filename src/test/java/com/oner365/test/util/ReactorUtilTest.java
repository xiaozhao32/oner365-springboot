package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;

import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class ReactorUtilTest extends BaseUtilsTest {

  @Test
  void testGetUrl() {
    String uri = "http://localhost:8704/actuator/health";
    // GET
    String result = HttpClient.create().get().uri(uri).responseContent().aggregate().asString().block();
    logger.info("HttpClient GET : {}", result);
    Assertions.assertNotNull(result);
  }

  @Test
  void testPostUrl() {
    String uri = "http://localhost:8704/system/auth/login";
    JSONObject json = new JSONObject();
    json.put("userName", "admin");
    json.put("password", "1");
    // POST
    String result = HttpClient.create().headers(h -> h.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .post().uri(uri).send(ByteBufFlux.fromString(Mono.just(json.toJSONString()))).responseContent().aggregate()
        .asString().block();
    logger.info("HttpClient POST : {}", result);
    Assertions.assertNotNull(result);
  }

}
