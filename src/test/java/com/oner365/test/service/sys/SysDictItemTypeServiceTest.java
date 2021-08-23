package com.oner365.test.service.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.entity.SysDictItemType;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.test.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

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
        JSONObject paramJson = new JSONObject();
        List<SysDictItemType> list = service.findList(paramJson);
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
        JSONObject paramJson = new JSONObject();
        Page<SysDictItemType> list = service.pageList(paramJson);
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
