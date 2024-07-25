package com.oner365.test.service.sys;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
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

    @Resource
    private ISysDictItemTypeService service;

    @RepeatedTest(value = 2)
    void findList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysDictItemTypeDto> list = service.findList(paramData);
        logger.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void findListByCodes() {
        String[] codes = new String[]{"sys_task_group", "sys_task_status"};
        List<SysDictItemTypeDto> list = service.findListByCodes(Arrays.asList(codes));
        logger.info("findListByCodes:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        PageInfo<SysDictItemTypeDto> list = service.pageList(paramData);
        logger.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    void getById() {
        String id = "sys_task_group";
        SysDictItemTypeDto entity = service.getById(id);
        logger.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

}
