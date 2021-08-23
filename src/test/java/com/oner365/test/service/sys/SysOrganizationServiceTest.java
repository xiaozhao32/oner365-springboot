package com.oner365.test.service.sys;

import com.alibaba.fastjson.JSON;
import com.oner365.sys.entity.SysOrganization;
import com.oner365.sys.service.ISysOrganizationService;
import com.oner365.test.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Test SysOrganizationService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysOrganizationServiceTest extends BaseServiceTest {

    @Autowired
    private ISysOrganizationService service;

    @Test
    public void getById() {
        String id = "110101";
        SysOrganization entity = service.getById(id);
        LOGGER.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

    @RepeatedTest(value = 2)
    public void findListByParentId() {
        List<SysOrganization> list = service.findListByParentId("-1");
        LOGGER.info("findListByParentId:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void getByCode() {
        String code = "110101000000";
        SysOrganization entity = service.getByCode(code);
        LOGGER.info("getByCode:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

    @Test
    public void selectList() {
        SysOrganization sysOrg = new SysOrganization();
        // cache error
        List<SysOrganization> list = service.selectList(sysOrg);
        LOGGER.info("selectList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void selectListByUserId() {
        String userId = "1";
        List<String> list = service.selectListByUserId(userId);
        LOGGER.info("selectListByUserId:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

}
