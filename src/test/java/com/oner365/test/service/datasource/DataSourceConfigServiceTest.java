package com.oner365.test.service.datasource;

import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.entity.DataSourceConfig;
import com.oner365.sys.service.IDataSourceConfigService;
import com.oner365.test.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

/**
 * Test DataSourceConfigService
 *
 * @author zhaoyong
 */
@SpringBootTest
public class DataSourceConfigServiceTest extends BaseServiceTest {

    @Autowired
    private IDataSourceConfigService service;

    @RepeatedTest(value = 2)
    public void pageList() {
        JSONObject paramJson = new JSONObject();
        Page<DataSourceConfig> list = service.pageList(paramJson);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

}
