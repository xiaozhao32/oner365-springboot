package com.oner365.test.service.sys;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.dto.SysDictItemTypeDto;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysDictItemTypeService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysDictItemTypeServiceTest extends BaseServiceTest {

    @Autowired
    private ISysDictItemTypeService service;

    @RepeatedTest(value = 2)
    void findList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysDictItemTypeDto> list = service.findList(paramData);
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void findListByCodes() {
        String[] codes = new String[]{"sys_task_group", "sys_task_status"};
        List<SysDictItemTypeDto> list = service.findListByCodes(Arrays.asList(codes));
        LOGGER.info("findListByCodes:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<SysDictItemTypeDto> list = service.pageList(paramData);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    void getById() {
        String id = "sys_task_group";
        SysDictItemTypeDto entity = service.getById(id);
        LOGGER.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

}
