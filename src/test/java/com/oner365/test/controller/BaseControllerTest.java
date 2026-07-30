package com.oner365.test.controller;

import java.time.Duration;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.redis.RedisCache;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.test.BaseTest;

import jakarta.annotation.Resource;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

/**
 * Base Controller
 *
 * @author zhaoyong
 *
 */
@AutoConfigureWebTestClient
public abstract class BaseControllerTest extends BaseTest {

    @Resource
    private RedisCache redisCache;
    
    @Resource 
    protected ObjectMapper objectMapper;

    private WebTestClient webTestClient;

    @Value("${local.server.port}")
    private int port;

    protected WebTestClient getWebClient() {
        if (webTestClient == null) {
            webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
        }
        return webTestClient;
    }

    /**
     * Request Header Authorization
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
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("userName", "admin");
        objectNode.put("password", "1");

        ResponseData<LoginUserDto> response = getWebClient().post()
            .uri(url)
            .body(BodyInserters.fromValue(objectNode))
            .exchange()
            .expectBody(new ParameterizedTypeReference<ResponseData<LoginUserDto>>() {
            })
            .returnResult()
            .getResponseBody();

        Assertions.assertNotNull(response);
        LoginUserDto result = response.getResult();
        if (ResultEnum.SUCCESS.getCode().equals(response.getCode()) && result != null) {
            token = result.getAccessToken();
            redisCache.setCacheObject(cacheKey, token, Duration.ofMinutes(3));
        }
        return token;
    }

    /**
     * GET request
     * @param url 请求地址
     * @return Object ResponseData result
     */
    protected Object get(String url) {
        ResponseData<?> response = getWebClient().get()
            .uri(url)
            .header(HttpHeaders.AUTHORIZATION, getToken())
            .exchange()
            .expectBody(ResponseData.class)
            .returnResult()
            .getResponseBody();
        return Objects.requireNonNull(response).getResult();
    }

    /**
     * POST request
     * @param url 请求地址
     * @param bodyInserters 请求Body
     * @return Object ResponseData result
     */
    protected Object post(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
        ResponseData<?> response = getWebClient().post()
            .uri(url)
            .header(HttpHeaders.AUTHORIZATION, getToken())
            .body(bodyInserters)
            .exchange()
            .expectBody(ResponseData.class)
            .returnResult()
            .getResponseBody();
        return Objects.requireNonNull(response).getResult();
    }

    /**
     * PUT request
     * @param url 请求地址
     * @param bodyInserters 请求Body
     * @return Object ResponseData result
     */
    protected Object put(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
        ResponseData<?> response = getWebClient().put()
            .uri(url)
            .header(HttpHeaders.AUTHORIZATION, getToken())
            .body(bodyInserters)
            .exchange()
            .expectBody(ResponseData.class)
            .returnResult()
            .getResponseBody();
        return Objects.requireNonNull(response).getResult();
    }

    /**
     * DELETE request
     * @param url 请求地址
     * @param bodyInserters 请求Body
     * @return Object ResponseData result
     */
    protected Object delete(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
        ResponseData<?> response = getWebClient().method(HttpMethod.DELETE)
            .uri(url)
            .header(HttpHeaders.AUTHORIZATION, getToken())
            .body(bodyInserters)
            .exchange()
            .expectBody(ResponseData.class)
            .returnResult()
            .getResponseBody();
        return Objects.requireNonNull(response).getResult();
    }

}
