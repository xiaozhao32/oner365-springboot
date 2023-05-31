package com.oner365.test.service.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

  @Resource
  private ISysMenuService service;

  @Test
  void getById() {
    String id = "101";
    SysMenuDto entity = service.getById(id);
    logger.info("getById:{}", JSON.toJSONString(entity));
    Assertions.assertNotNull(entity);
  }

  @Test
  void findMenuByTypeCode() {
    String typeCode = "nt_sys";
    List<SysMenuDto> list = service.findMenuByTypeCode(typeCode);
    logger.info("findMenuByTypeCode:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectMenuByRoles() {
    List<String> roles = new ArrayList<>();
    roles.add("1");
    List<SysMenuDto> list = service.selectMenuByRoles(roles, "1", "-1");
    logger.info("selectMenuByRoles:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void findMenu() {
    List<SysMenuDto> list = service.findMenu("1", "-1");
    logger.info("findMenu:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectListByRoleId() {
    List<String> list = service.selectListByRoleId("1", "1");
    logger.info("selectListByRoleId:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectList() {
    SysMenuVo menu = new SysMenuVo();
    menu.setMenuTypeId("1");
    List<SysMenuDto> list = service.selectList(menu);
    logger.info("selectList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectListByUserId() {
    SysMenuVo menu = new SysMenuVo();
    menu.setMenuTypeId("1");
    menu.setUserId("1");
    List<SysMenuDto> list = service.selectListByUserId(menu);
    logger.info("selectListByUserId:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

}
