package com.oner365.test.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.dto.SysRoleDto;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysRoleService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysRoleServiceTest extends BaseServiceTest {

  @Autowired
  private ISysRoleService service;

  @RepeatedTest(value = 2)
  void findList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    List<SysRoleDto> list = service.findList(paramData);
    LOGGER.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void pageList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    PageInfo<SysRoleDto> list = service.pageList(paramData);
    LOGGER.info("pageList:{}", list.getSize());
    Assertions.assertNotEquals(0, list.getSize());
  }

  @Test
  void getById() {
    String id = "1";
    SysRoleDto entity = service.getById(id);
    LOGGER.info("getById:{}", JSON.toJSONString(entity));
    Assertions.assertNotNull(entity);
  }

  @Test
  void findMenuByRoles() {
    List<String> roles = new ArrayList<>();
    roles.add("1");
    String menuType = "1";
    JSONArray list = service.findMenuByRoles(roles, menuType);
    LOGGER.info("findMenuByRoles:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void findMenuByRoleId() {
    String menuType = "1";
    String roleId = "1";
    List<String> list = service.findMenuByRoleId(menuType, roleId);
    LOGGER.info("findMenuByRoleId:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void findTreeList() {
    String menuType = "1";
    List<Object> list = service.findTreeList(menuType);
    LOGGER.info("findTreeList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

}
