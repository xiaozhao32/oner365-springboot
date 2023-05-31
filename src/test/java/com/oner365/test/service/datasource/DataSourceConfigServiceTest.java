package com.oner365.test.service.datasource;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.dto.DataSourceConfigDto;
import com.oner365.sys.service.IDataSourceConfigService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test DataSourceConfigService
 *
 * @author zhaoyong
 */
@SpringBootTest
class DataSourceConfigServiceTest extends BaseServiceTest {

    @Resource
    private IDataSourceConfigService service;

    @RepeatedTest(value = 2)
    void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        PageInfo<DataSourceConfigDto> list = service.pageList(paramData);
        logger.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

}
