package com.oner365.test.service.elasticsearch;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.elasticsearch.autoconfigure.ElasticsearchProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

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

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String query = "";
        HttpEntity<String> entity = new HttpEntity<>(query, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                elasticsearchProperties.getUris().get(0) + "/samplelocation/_search", HttpMethod.GET, entity,
                String.class);
        logger.info("response: {}", response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
