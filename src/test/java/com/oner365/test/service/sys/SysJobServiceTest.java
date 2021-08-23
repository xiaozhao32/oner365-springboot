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
import com.oner365.sys.entity.SysJob;
import com.oner365.sys.service.ISysJobService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysJobService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysJobServiceTest extends BaseServiceTest {

    @Autowired
    private ISysJobService service;

    @RepeatedTest(value = 2)
    public void findList() {
        JSONObject paramJson = new JSONObject();
        List<SysJob> list = service.findList(paramJson);
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void pageList() {
        JSONObject paramJson = new JSONObject();
        Page<SysJob> list = service.pageList(paramJson);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    public void getById() {
        String id = "1";
        SysJob entity = service.getById(id);
        LOGGER.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

}
