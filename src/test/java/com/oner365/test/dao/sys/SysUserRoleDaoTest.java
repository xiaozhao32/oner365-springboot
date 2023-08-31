package com.oner365.test.dao.sys;

import java.util.List;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.sys.dao.ISysUserRoleDao;
import com.oner365.test.dao.BaseDaoTest;

/**
 * Test SysUserRoleDao
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysUserRoleDaoTest extends BaseDaoTest {
  
  @Resource
  private ISysUserRoleDao dao;
  
  @Test
  void findUserRoleByUserId() {
    String userId = "1";
    List<String> result = dao.findUserRoleByUserId(userId);
    logger.info("result:{}", result.size());
    Assertions.assertNotEquals(0, result.size());
  }

}
