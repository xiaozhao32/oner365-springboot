package com.oner365.monitor.controller.rabbitmq;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.controller.BaseController;
import com.oner365.util.Base64Utils;
import com.oner365.util.DataUtils;
import com.oner365.util.HttpClientUtils;

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
    
    @Value("${spring.rabbitmq.username}")
    private String username;
    
    @Value("${spring.rabbitmq.password}")
    private String password;

    /**
     * 首页
     * 
     * @return JSONObject
     */
    @GetMapping("/index")
    @ApiOperation("首页")
    public JSONObject index() {
        String uri = "/api/overview";
        Mono<JSONObject> mono = getWebClient().get().uri(uri).header(HttpHeaders.AUTHORIZATION, getAuthorization()).retrieve()
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
    
    private String getAuthorization() {
        String auth = username + ":" + password;
        return "Basic " + Base64Utils.encodeBase64String(auth.getBytes());
    }
    
    /**
     * 获取队列列表
     * 
     * @param type      类型
     * @param pageIndex 分页页码
     * @param pageSize  分页长度
     * @param name      名称
     * @return JSONObject
     */
    @GetMapping("/list/{type}")
    @ApiOperation("获取队列列表")
    public JSONObject list(@PathVariable("type") String type, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize, String name) {
        String url = getUrl(type, name, pageIndex, pageSize);
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
    @ApiOperation("删除不同类型的队列")
    public JSONObject delete(@PathVariable("type") String type, @PathVariable("name") String name) {
        try {
            String vhost = "/";
            // TODO 这里 '/' encode无效
//            String uri = "/api/" + type + "/" + URLEncoder.encode(vhost, Charset.defaultCharset().name()) + "/" + name;
            JSONObject paramJson = new JSONObject();
            paramJson.put("vhost", vhost);
            paramJson.put("mode", "delete");
            paramJson.put("name", name);
//            Mono<JSONObject> mono = getWebClient().method(HttpMethod.DELETE).uri(uri)
//                    .header(HttpHeaders.AUTHORIZATION, getAuthorization()).body(BodyInserters.fromValue(paramJson)).retrieve()
//                    .bodyToMono(JSONObject.class);
//            
//            return mono.block();
            String url = "http://"+host+":15672/api/" + type + "/" + URLEncoder.encode(vhost, Charset.defaultCharset().name()) + "/" + name;
            Map<String,Object> headers = Maps.newHashMap();
            headers.put(HttpHeaders.AUTHORIZATION, getAuthorization());
            String result = HttpClientUtils.httpDeleteRequest(url, headers, paramJson);
            return JSON.parseObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    private JSONObject request(String uri) {
        Mono<JSONObject> mono = getWebClient().get().uri(uri).header(HttpHeaders.AUTHORIZATION, getAuthorization()).retrieve()
                .bodyToMono(JSONObject.class);
        return mono.block();
    }
}
