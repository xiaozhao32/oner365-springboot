package com.oner365.test.service.sys;

import com.alibaba.fastjson.JSON;
import com.oner365.sys.entity.SysMenu;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.test.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Test SysMenuService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysMenuServiceTest extends BaseServiceTest {

    @Autowired
    private ISysMenuService service;

    @Test
    public void getById() {
        String id = "101";
        SysMenu entity = service.getById(id);
        LOGGER.info("getById:{}", JSON.toJSONString(entity));
        Assertions.assertNotNull(entity);
    }

    @Test
    public void findMenuByTypeCode() {
        String typeCode = "nt_sys";
        List<SysMenu> list = service.findMenuByTypeCode(typeCode);
        LOGGER.info("findMenuByTypeCode:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void selectMenuByRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("1");
        List<SysMenu> list = service.selectMenuByRoles(roles, "1", "-1");
        LOGGER.info("selectMenuByRoles:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void findMenu() {
        List<SysMenu> list = service.findMenu("1", "-1");
        LOGGER.info("findMenu:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void selectListByRoleId() {
        List<Integer> list = service.selectListByRoleId("1", "1");
        LOGGER.info("selectListByRoleId:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void selectList() {
        // cache error
        SysMenu menu = new SysMenu();
        menu.setMenuTypeId("1");
        List<SysMenu> list = service.selectList(menu);
        LOGGER.info("selectList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void selectListByUserId() {
        SysMenu menu = new SysMenu();
        menu.setMenuTypeId("1");
        menu.setUserId("1");
        List<SysMenu> list = service.selectListByUserId(menu);
        LOGGER.info("selectListByUserId:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

}
