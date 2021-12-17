package com.oner365.test.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.ResponseData;
import com.oner365.test.BaseTest;
import com.oner365.util.RequestUtils;

import reactor.core.publisher.Mono;

/**
 * Base Controller
 *
 * @author zhaoyong
 *
 */
public abstract class BaseControllerTest extends BaseTest {

    protected static final String URL = "http://localhost:8704";

    protected WebClient client;

    protected String token;

    protected BaseControllerTest() {
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        client = WebClient.builder().clientConnector(httpConnector).baseUrl(URL).build();
        token = getToken();
    }

    /**
     * Request Header Authorization
     *
     * @return String token
     */
    @SuppressWarnings("rawtypes")
    protected String getToken() {
        // auth
        String url = "/system/auth/login";
        JSONObject paramJson = new JSONObject();
        paramJson.put("userName", "admin");
        paramJson.put("password", "1");

        Mono<ResponseData> mono = client.post().uri(url).body(BodyInserters.fromValue(paramJson)).retrieve()
                .bodyToMono(ResponseData.class);

        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) Objects.requireNonNull(mono.block()).getResult();
        return result.get(RequestUtils.ACCESS_TOKEN).toString();
    }

    /**
     * GET request
     *
     * @param url 请求地址
     * @return Object ResponseData result
     */
    @SuppressWarnings("rawtypes")
    protected Object get(String url) {
        Mono<ResponseData> mono = client.get().uri(url).header(HttpHeaders.AUTHORIZATION, token).retrieve()
                .bodyToMono(ResponseData.class);
        return Objects.requireNonNull(mono.block()).getResult();
    }

    /**
     * POST request
     *
     * @param url           请求地址
     * @param bodyInserters 请求Body
     * @return Object ResponseData result
     */
    @SuppressWarnings("rawtypes")
    protected Object post(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
        Mono<ResponseData> mono = client.post().uri(url).header(HttpHeaders.AUTHORIZATION, token).body(bodyInserters)
                .retrieve().bodyToMono(ResponseData.class);
        return Objects.requireNonNull(mono.block()).getResult();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected <T> T post(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters, Class<T> clazz) {
        Mono<ResponseData> mono = client.post().uri(url).header(HttpHeaders.AUTHORIZATION, token).body(bodyInserters)
                .retrieve().bodyToMono(ResponseData.class);
        return (T) Objects.requireNonNull(mono.block()).getResult();
    }

    /**
     * PUT request
     *
     * @param url           请求地址
     * @param bodyInserters 请求Body
     * @return Object ResponseData result
     */
    @SuppressWarnings("rawtypes")
    protected Object put(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
        Mono<ResponseData> mono = client.put().uri(url).header(HttpHeaders.AUTHORIZATION, token).body(bodyInserters)
                .retrieve().bodyToMono(ResponseData.class);
        return Objects.requireNonNull(mono.block()).getResult();
    }

    /**
     * DELETE request
     *
     * @param url           请求地址
     * @param bodyInserters 请求Body
     * @return Object ResponseData result
     */
    @SuppressWarnings("rawtypes")
    protected Object delete(String url, BodyInserter<?, ? super ClientHttpRequest> bodyInserters) {
        Mono<ResponseData> mono = client.method(HttpMethod.DELETE).uri(url).header(HttpHeaders.AUTHORIZATION, token)
                .body(bodyInserters).retrieve().bodyToMono(ResponseData.class);
        return Objects.requireNonNull(mono.block()).getResult();
    }
}
