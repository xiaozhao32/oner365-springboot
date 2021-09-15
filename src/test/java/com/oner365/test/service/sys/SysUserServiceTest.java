package com.oner365.test.service.sys;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.entity.SysUser;
import com.oner365.sys.service.ISysUserService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysUserService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysUserServiceTest extends BaseServiceTest {

    @Autowired
    private ISysUserService service;

    @RepeatedTest(value = 2)
    public void findList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysUser> list = service.findList(paramData);
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<SysUser> list = service.pageList(paramData);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    public void getById() {
        String id = "1";
        SysUser entity = service.getById(id);
        LOGGER.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

    @Test
    public void login() {
        String userName = "admin";
        String password = "1";
        Map<String, Object> map = service.login(userName, password);
        LOGGER.info("login:{}", map);
        Assertions.assertNotEquals(0, map.size());
    }

}
