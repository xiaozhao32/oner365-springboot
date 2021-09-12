package com.oner365.monitor.controller.rabbitmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson.JSONObject;
import com.oner365.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;

/**
 * Rabbit MQ监控
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/monitor/rabbitmq")
@Api(tags = "监控 - Rabbitmq")
public class RabbitmqController extends BaseController {
    
    @Value("${spring.rabbitmq.host}")
    private String host;
    private static final String AUTH = "Basic YWRtaW46YWRtaW4xMjM=";
    
    @GetMapping("/index")
    @ApiOperation("首页")
    public JSONObject index() {
        String url = "http://" + host + ":15672";
        
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        WebClient client = WebClient.builder().clientConnector(httpConnector).baseUrl(url).build();
        
        String uri = "/api/overview";
        Mono<JSONObject> mono = client.get().uri(uri)
                .header(HttpHeaders.AUTHORIZATION, AUTH)
                .retrieve().bodyToMono(JSONObject.class);
        return mono.block();
    }
}
