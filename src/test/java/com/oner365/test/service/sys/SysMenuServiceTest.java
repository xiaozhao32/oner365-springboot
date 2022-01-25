package com.oner365.test.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.sys.dto.SysMenuDto;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.sys.vo.SysMenuVo;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysMenuService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysMenuServiceTest extends BaseServiceTest {

  @Autowired
  private ISysMenuService service;

  @Test
  void getById() {
    String id = "101";
    SysMenuDto entity = service.getById(id);
    LOGGER.info("getById:{}", JSON.toJSONString(entity));
    Assertions.assertNotNull(entity);
  }

  @Test
  void findMenuByTypeCode() {
    String typeCode = "nt_sys";
    List<SysMenuDto> list = service.findMenuByTypeCode(typeCode);
    LOGGER.info("findMenuByTypeCode:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectMenuByRoles() {
    List<String> roles = new ArrayList<>();
    roles.add("1");
    List<SysMenuDto> list = service.selectMenuByRoles(roles, "1", "-1");
    LOGGER.info("selectMenuByRoles:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void findMenu() {
    List<SysMenuDto> list = service.findMenu("1", "-1");
    LOGGER.info("findMenu:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectListByRoleId() {
    List<String> list = service.selectListByRoleId("1", "1");
    LOGGER.info("selectListByRoleId:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectList() {
    // cache error
    SysMenuVo menu = new SysMenuVo();
    menu.setMenuTypeId("1");
    List<SysMenuDto> list = service.selectList(menu);
    LOGGER.info("selectList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectListByUserId() {
    SysMenuVo menu = new SysMenuVo();
    menu.setMenuTypeId("1");
    menu.setUserId("1");
    List<SysMenuDto> list = service.selectListByUserId(menu);
    LOGGER.info("selectListByUserId:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

}
