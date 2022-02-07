package com.oner365.test.util;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.util.Base64Utils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class HttpClientUtilsTest extends BaseUtilsTest {

    @Test
    void test() throws IOException {
        String hostname = "localhost";
        logger.info("base64:{}", new String(Base64Utils.encodeBase64("admin:admin123".getBytes())));
        HttpHost target = new HttpHost(hostname, 15672, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("admin", "admin123"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        HttpGet httpget = new HttpGet("http://" + hostname + ":15672/api/queues/%2f/oner365.saveTaskLogTask");
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(target, basicAuth);
        HttpClientContext localContext = HttpClientContext.create();

        Assertions.assertNotNull(localContext);
        localContext.setAuthCache(authCache);
        CloseableHttpResponse response = httpclient.execute(target, httpget, localContext);
        logger.info("result:{}", EntityUtils.toString(response.getEntity()));
//
//        String url = "http://" + hostname + ":15672/api/queues///oner365.saveTaskLogTask";
//        System.out.println(URLEncoder.encode(url,Charset.defaultCharset().name()));
//        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
//        WebClient webClient = WebClient.builder().clientConnector(httpConnector).baseUrl(URLEncoder.encode(url,Charset.defaultCharset().name())).build();
//        Mono<JSONObject> mono = webClient.method(HttpMethod.GET)
//                .header(HttpHeaders.AUTHORIZATION,
//                        "Basic " + new String(Base64Utils.encodeBase64("admin:admin123".getBytes())))
//                .retrieve().bodyToMono(JSONObject.class);
//        System.out.println(mono.block().toJSONString());
    }
}
