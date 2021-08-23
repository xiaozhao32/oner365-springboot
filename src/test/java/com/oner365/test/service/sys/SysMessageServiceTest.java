package com.oner365.test.service.sys;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.sys.entity.SysMessage;
import com.oner365.sys.service.ISysMessageService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysMessageService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysMessageServiceTest extends BaseServiceTest {

    @Autowired
    private ISysMessageService service;

    @RepeatedTest(value = 2)
    public void findList() {
        String messageType = "test";
        List<SysMessage> list = service.findList(messageType);
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void getById() {
        String messageType = "test";
        List<SysMessage> list = service.findList(messageType);
        if (!list.isEmpty()) {
            SysMessage entity = service.getById(list.get(0).getId());
            LOGGER.info("getById:{}", JSON.toJSONString(entity));
            Assertions.assertNotNull(entity);
        }
    }

}
