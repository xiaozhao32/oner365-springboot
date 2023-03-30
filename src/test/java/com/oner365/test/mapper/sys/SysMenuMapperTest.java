package com.oner365.test.mapper.sys;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.sys.entity.SysMenu;
import com.oner365.sys.mapper.SysMenuMapper;
import com.oner365.test.mapper.BaseMapperTest;

/**
 * Mapper 单元测试
 * 
 * @author zhaoyong
 *
 */
public class SysMenuMapperTest extends BaseMapperTest {

  @Test
  void selectListByRoleId() {
    String roleId = "1";
    String menuTypeId = "1";
    List<String> list = getMapper(SysMenuMapper.class).selectListByRoleId(roleId, menuTypeId);
    logger.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }
  
  @Test
  void selectListByUserId() {
    SysMenu sysMenu = new SysMenu();
    sysMenu.setUserId("1");
    sysMenu.setMenuTypeId("1");
    List<SysMenu> list = getMapper(SysMenuMapper.class).selectListByUserId(sysMenu);
    logger.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }
  
  @Test
  void selectMenuByRoles() {
    List<String> roles = new ArrayList<>();
    roles.add("1");
    String menuTypeId = "1";
    String parentId = "-1";
    List<SysMenu> list = getMapper(SysMenuMapper.class).selectMenuByRoles(roles, menuTypeId, parentId);
    logger.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }
}
