package com.oner365.monitor.controller.rabbitmq;

import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson.JSONObject;
import com.oner365.controller.BaseController;
import com.oner365.util.DataUtils;

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

    /**
     * 首页
     * 
     * @return JSONObject
     */
    @GetMapping("/index")
    @ApiOperation("首页")
    public JSONObject index() {
        String uri = "/api/overview";
        Mono<JSONObject> mono = getWebClient().get().uri(uri).header(HttpHeaders.AUTHORIZATION, AUTH).retrieve()
                .bodyToMono(JSONObject.class);
        return mono.block();
    }
    
    private WebClient getWebClient() {
        String url = "http://" + host + ":15672";
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        return WebClient.builder().clientConnector(httpConnector).baseUrl(url).build();
    }

    private String getUrl(String paramName, String name, int pageIndex, int pageSize) {
        return "/api/" + paramName + "?page=" + pageIndex + "&page_size=" + pageSize + "&name="
                + DataUtils.trimToEmpty(name) + "&use_regex=false&pagination=true";
    }

    /**
     * Connection
     * 
     * @param pageIndex 分页页码
     * @param pageSize  分页长度
     * @param name      名称
     * @return JSONObject
     */
    @GetMapping("/connection/list")
    @ApiOperation("客户端连接列表")
    public JSONObject connectionList(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
            String name) {
        String url = getUrl("connections", name, pageIndex, pageSize);
        return request(url);
    }

    /**
     * Channel
     * 
     * @param pageIndex 分页页码
     * @param pageSize  分页长度
     * @param name      名称
     * @return JSONObject
     */
    @GetMapping("/channel/list")
    @ApiOperation("客户端通道列表")
    public JSONObject channelList(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
            String name) {
        String url = getUrl("channels", name, pageIndex, pageSize);
        return request(url);
    }

    /**
     * Exchange
     * 
     * @param pageIndex 分页页码
     * @param pageSize  分页长度
     * @param name      名称
     * @return JSONObject
     */
    @GetMapping("/exchange/list")
    @ApiOperation("服务端交换区列表")
    public JSONObject exchangeList(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
            String name) {
        String url = getUrl("exchanges", name, pageIndex, pageSize);
        return request(url);
    }

    /**
     * Queue
     * 
     * @param pageIndex 分页页码
     * @param pageSize  分页长度
     * @param name      名称
     * @return JSONObject
     */
    @GetMapping("/queue/list")
    @ApiOperation("服务端队列列表")
    public JSONObject queueList(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
            String name) {
        String url = getUrl("queues", name, pageIndex, pageSize);
        return request(url);
    }
    
    /**
     * 删除
     * 
     * @param type 删除类型
     * @param name 名称
     * @return JSONObject
     */
    @DeleteMapping("/delete/{type}/{name}")
    public JSONObject delete(@PathVariable("type") String type, @PathVariable("name") String name) {
        try {
            String vhost = "/";
            // TODO 这里 '/' encode无效
            String uri = "/api/" + type + "/" + URLEncoder.encode(vhost, Charset.defaultCharset().name()) + "/" + name;
            JSONObject paramJson = new JSONObject();
            paramJson.put("vhost", vhost);
            paramJson.put("mode", "delete");
            paramJson.put("name", name);
            Mono<JSONObject> mono = getWebClient().method(HttpMethod.DELETE).uri(uri)
                    .header(HttpHeaders.AUTHORIZATION, AUTH).body(BodyInserters.fromValue(paramJson)).retrieve()
                    .bodyToMono(JSONObject.class);
            
            return mono.block();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject request(String uri) {
        Mono<JSONObject> mono = getWebClient().get().uri(uri).header(HttpHeaders.AUTHORIZATION, AUTH).retrieve()
                .bodyToMono(JSONObject.class);
        return mono.block();
    }
}
