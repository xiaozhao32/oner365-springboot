package com.oner365.test.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.data.commons.config.properties.DefaultQueueProperties;
import com.oner365.data.commons.enums.QueueEnum;
import com.oner365.queue.config.properties.RabbitmqProperties;

import jakarta.annotation.Resource;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
@EnableConfigurationProperties({ RabbitmqProperties.class })
class HttpClientUtilsTest extends BaseUtilsTest {

    @Resource
    private RabbitmqProperties rabbitmqProperties;

    @Resource
    private DefaultQueueProperties defaultQueueProperties;

    @Test
    void test() throws IOException {
        QueueEnum queueType = defaultQueueProperties.getType();
        logger.info("Queue Type: {}", queueType);
        Assertions.assertNotNull(queueType);

        // Rabbitmq Test
        if (QueueEnum.RABBITMQ.equals(queueType)) {
            String uri = rabbitmqProperties.getUri();
            HttpHost target = new HttpHost(StringUtils.substringBefore(rabbitmqProperties.getAddresses(), ":"),
                    Integer.parseInt(StringUtils.substringAfterLast(uri, ":")), StringUtils.substringBefore(uri, ":"));
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()),
                    new UsernamePasswordCredentials(rabbitmqProperties.getUsername(),
                            rabbitmqProperties.getPassword()));
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
            HttpGet httpget = new HttpGet(rabbitmqProperties.getUri() + "/api/queues/%2f/oner365.saveTaskLogTask");
            AuthCache authCache = new BasicAuthCache();
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);
            HttpClientContext localContext = HttpClientContext.create();

            Assertions.assertNotNull(localContext);
            localContext.setAuthCache(authCache);
            CloseableHttpResponse response = httpclient.execute(target, httpget, localContext);
            logger.info("result:{}", EntityUtils.toString(response.getEntity()));
        }
    }

}
