package com.oner365.test.service.datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

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

    @Autowired
    private IDataSourceConfigService service;

    @RepeatedTest(value = 2)
    void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<DataSourceConfigDto> list = service.pageList(paramData);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

}
