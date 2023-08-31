package com.oner365.test.dao.sys;

import java.util.List;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.sys.dao.ISysRoleMenuDao;
import com.oner365.test.dao.BaseDaoTest;

/**
 * Test SysRoleMenuDao
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysRoleMenuDaoTest extends BaseDaoTest {
  
  @Resource
  private ISysRoleMenuDao dao;
  
  @Test
  void findUserJobByUserId() {
    String roleId = "1";
    String menuTypeId = "1";
    List<String> result = dao.findMenuListByRoleId(roleId, menuTypeId);
    logger.info("result:{}", result.size());
    Assertions.assertNotEquals(0, result.size());
  }

}
