package com.oner365.test.service.sys;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.entity.SysDictItem;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysDictItemService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysDictItemServiceTest extends BaseServiceTest {

    @Autowired
    private ISysDictItemService service;

    @RepeatedTest(value = 2)
    public void findList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysDictItem> list = service.findList(paramData);
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<SysDictItem> list = service.pageList(paramData);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    public void getById() {
        String id = "1101";
        SysDictItem entity = service.getById(id);
        LOGGER.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

}
