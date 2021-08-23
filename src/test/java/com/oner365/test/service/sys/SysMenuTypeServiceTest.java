package com.oner365.test.service.sys;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.entity.SysMenuType;
import com.oner365.sys.service.ISysMenuTypeService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysMenuTypeService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysMenuTypeServiceTest extends BaseServiceTest {

    @Autowired
    private ISysMenuTypeService service;

    @RepeatedTest(value = 2)
    public void findList() {
        JSONObject paramJson = new JSONObject();
        List<SysMenuType> list = service.findList(paramJson);
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void pageList() {
        JSONObject paramJson = new JSONObject();
        Page<SysMenuType> list = service.pageList(paramJson);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    public void getById() {
        String id = "1";
        SysMenuType entity = service.getById(id);
        LOGGER.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

    @Test
    public void getMenuTypeByTypeCode() {
        String code = "nt_sys";
        SysMenuType entity = service.getMenuTypeByTypeCode(code);
        LOGGER.info("getMenuTypeByTypeCode:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

}
