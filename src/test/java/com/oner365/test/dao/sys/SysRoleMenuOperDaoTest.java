package com.oner365.test.dao.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.sys.dao.ISysRoleMenuOperDao;
import com.oner365.test.dao.BaseDaoTest;

/**
 * Test SysRoleMenuOperDao
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysRoleMenuOperDaoTest extends BaseDaoTest {
  
  @Autowired
  private ISysRoleMenuOperDao dao;
  
  @Test
  void findMenuOperListByRoleId() {
    String roleId = "1";
    String menuTypeId = "1";
    List<Map<String,String>> result = dao.findMenuOperListByRoleId(roleId, menuTypeId);
    logger.info("result:{}", result.size());
  }
  
  @Test
  void findMenuOperByRoles() {
    List<String> roles = new ArrayList<>();
    roles.add("1");
    String menuId = "101";
    List<Map<String,String>> result = dao.findMenuOperByRoles(roles, menuId);
    logger.info("result:{}", result.size());
  }

}
