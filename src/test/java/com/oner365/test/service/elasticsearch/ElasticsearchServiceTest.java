package com.oner365.test.service.elasticsearch;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
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

    @Resource
    private ISampleGeneElasticsearchService service;

    @RepeatedTest(value = 2)
    void pageList() {
        QueryCriteriaBean paramJson = new QueryCriteriaBean();
        PageInfo<SampleGeneDto> list = service.pageList(paramJson);
        logger.info("findList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

}
