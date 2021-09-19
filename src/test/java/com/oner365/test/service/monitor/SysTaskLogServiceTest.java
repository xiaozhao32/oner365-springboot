package com.oner365.test.service.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.test.service.BaseServiceTest;
import com.oner365.util.DataUtils;

/**
 * Test SysTaskLogService
 *
 * @author zhaoyong
 */
@SpringBootTest
public class SysTaskLogServiceTest extends BaseServiceTest {

    @Autowired
    private ISysTaskLogService service;

    @RepeatedTest(value = 2)
    public void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<SysTaskLog> list = service.pageList(paramData);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    public void selectTaskLogById() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<SysTaskLog> list = service.pageList(paramData);
        if (!DataUtils.isEmpty(list) && !list.getContent().isEmpty()) {
            SysTaskLog entity = service.selectTaskLogById(list.getContent().get(0).getId());
            LOGGER.info("selectTaskLogById:{}", JSON.toJSONString(entity));
            Assertions.assertNotNull(entity);
        }
    }

}
