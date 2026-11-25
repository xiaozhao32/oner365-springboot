package com.oner365.test.service.elasticsearch;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.elasticsearch.autoconfigure.ElasticsearchProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.elasticsearch.dto.SampleGeneDto;
import com.oner365.elasticsearch.service.ISampleGeneElasticsearchService;
import com.oner365.test.service.BaseServiceTest;

import jakarta.annotation.Resource;

/**
 * Test Elasticsearch service
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class ElasticsearchServiceTest extends BaseServiceTest {

    @Resource
    private ISampleGeneElasticsearchService service;

    @Resource
    private WebClient webClient;

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    @RepeatedTest(value = 2)
    void pageList() {
        QueryCriteriaBean paramJson = new QueryCriteriaBean();
        PageInfo<SampleGeneDto> list = service.pageList(paramJson);
        logger.info("findList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    void queryElasticsearch() {
        // ✅ 添加认证头
        String auth = elasticsearchProperties.getUsername() + PublicConstants.COLON
                + elasticsearchProperties.getPassword();
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        logger.info("Auth: {}", authHeader);

        String uri = elasticsearchProperties.getUris().get(0) + "/samplelocation/_search";
        String response = webClient.get()
            .uri(uri)
            .header(HttpHeaders.AUTHORIZATION, authHeader)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        logger.info("response: {}", response);
        Assertions.assertNotNull(response);
    }

}
