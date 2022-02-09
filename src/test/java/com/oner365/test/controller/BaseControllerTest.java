package com.oner365.test.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.ResponseData;
import com.oner365.common.cache.RedisCache;
import com.oner365.test.BaseTest;
import com.oner365.util.RequestUtils;

/**
 * Base Controller
 *
 * @author zhaoyong
 *
 */
public class BaseControllerTest extends BaseTest {

  @Autowired
  protected WebTestClient client;
  
  @Autowired
  private RedisCache redisCache;

  /**
   * Request Header Authorization
   *
   * @return String token
   */
  protected String getToken() {
    final String cacheKey = "Auth:test:token";
    String token = redisCache.getCacheObject(cacheKey);
    if (token != null) {
      return token;
    }
    // auth
    String url = "/system/auth/login";
    JSONObject paramJson = new JSONObject();
    paramJson.put("userName", "admin");
    paramJson.put("password", "1");

    ResponseData<?> response = client.post().uri(url).body(BodyInserters.fromValue(paramJson)).exchange()
        .expectBody(ResponseData.class).returnResult().getResponseBody();

    @SuppressWarnings("unchecked")
    Map<String, Object> result = (Map<String, Object>) response.getResult();
    token = result.get(RequestUtils.ACCESS_TOKEN).toString();
    redisCache.setCacheObject(cacheKey, token, 3, TimeUnit.MINUTES);
    return token;
  }

  /**
   * GET request
   *
   * @param url 请求地址
   * @return Object ResponseData result
   */
  protected Object get(String url) {
    ResponseData<?> response = client.get().uri(url).header(HttpHeaders.AUTHORIZATION, getToken()).exchange()
        .expectBody(ResponseData.class).returnResult().getResponseBody();
    return response.getResult();
  }

  /**
   * POST request
   *
   * @param url           请求地址
   * @param bodyInserters 请求Body
   * @return Object ResponseData result
   */
  protected Object post(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
    ResponseData<?> response = client.post().uri(url).header(HttpHeaders.AUTHORIZATION, getToken()).body(bodyInserters)
        .exchange().expectBody(ResponseData.class).returnResult().getResponseBody();
    return response.getResult();
  }

  @SuppressWarnings({ "unchecked" })
  protected <T> T post(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters, Class<T> clazz) {
    ResponseData<?> response = client.post().uri(url).header(HttpHeaders.AUTHORIZATION, getToken()).body(bodyInserters)
        .exchange().expectBody(ResponseData.class).returnResult().getResponseBody();
    return (T) response.getResult();
  }

  /**
   * PUT request
   *
   * @param url           请求地址
   * @param bodyInserters 请求Body
   * @return Object ResponseData result
   */
  protected Object put(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
    ResponseData<?> response = client.put().uri(url).header(HttpHeaders.AUTHORIZATION, getToken()).body(bodyInserters)
        .exchange().expectBody(ResponseData.class).returnResult().getResponseBody();
    return response.getResult();
  }

  /**
   * DELETE request
   *
   * @param url           请求地址
   * @param bodyInserters 请求Body
   * @return Object ResponseData result
   */
  protected Object delete(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
    ResponseData<?> response = client.method(HttpMethod.DELETE).uri(url).header(HttpHeaders.AUTHORIZATION, getToken()).body(bodyInserters)
        .exchange().expectBody(ResponseData.class).returnResult().getResponseBody();
    return response.getResult();
  }
}
