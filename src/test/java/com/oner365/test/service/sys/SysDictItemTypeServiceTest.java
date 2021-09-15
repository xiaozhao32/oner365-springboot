package com.oner365.test.service.sys;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.entity.SysDictItemType;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysDictItemTypeService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysDictItemTypeServiceTest extends BaseServiceTest {

    @Autowired
    private ISysDictItemTypeService service;

    @RepeatedTest(value = 2)
    public void findList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysDictItemType> list = service.findList(paramData);
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void findListByCodes() {
        JSONObject paramJson = new JSONObject();
        JSONArray paramArray = new JSONArray();
        paramArray.add("sys_task_group");
        paramArray.add("sys_task_status");
        paramJson.put("codes", paramArray);
        List<SysDictItemType> list = service.findListByCodes(paramJson);
        LOGGER.info("findListByCodes:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<SysDictItemType> list = service.pageList(paramData);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    public void getById() {
        String id = "sys_task_group";
        SysDictItemType entity = service.getById(id);
        LOGGER.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

}
