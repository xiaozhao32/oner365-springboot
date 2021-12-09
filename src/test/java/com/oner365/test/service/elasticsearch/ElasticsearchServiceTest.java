package com.oner365.test.service.elasticsearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Iterables;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.elasticsearch.dto.SampleGeneDto;
import com.oner365.elasticsearch.service.ISampleGeneElasticsearchService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test Elasticsearch service
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class ElasticsearchServiceTest extends BaseServiceTest {

    @Autowired
    private ISampleGeneElasticsearchService service;

    @RepeatedTest(value = 2)
    void findList() {
        QueryCriteriaBean paramJson = new QueryCriteriaBean();
        Iterable<SampleGeneDto> list = service.findList(paramJson);
        LOGGER.info("findList:{}", Iterables.size(list));
        Assertions.assertNotEquals(0, Iterables.size(list));
    }

}
