package com.oner365.test.dao.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysRoleMenuOperDao;
import com.oner365.sys.entity.SysRoleMenuOper;
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
    List<SysRoleMenuOper> result = dao.findMenuOperListByRoleId(roleId, menuTypeId);
    result.forEach(map -> {
      logger.info("menuId:{}, operId:{}", map.getMenuId(), map.getOperationId());
    });
    logger.info("result:{}", result.size());
    Assertions.assertNotEquals(0, result.size());
  }
  
  @Test
  void findMenuOperByRoles() {
    List<String> roles = new ArrayList<>();
    roles.add("1");
    String menuId = "1011";
    List<Map<String,String>> result = dao.findMenuOperByRoles(roles, menuId);
    result.forEach(map -> {
      logger.info("operId:{}", map.get(SysConstants.OPER_ID));
    });
    logger.info("result:{}", result.size());
    Assertions.assertNotEquals(0, result.size());
  }

}
