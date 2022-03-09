package com.oner365.test.dao.sys;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.sys.dao.ISysUserOrgDao;
import com.oner365.test.dao.BaseDaoTest;

/**
 * Test SysUserOrgDao
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysUserOrgDaoTest extends BaseDaoTest {
  
  @Autowired
  private ISysUserOrgDao dao;
  
  @Test
  void findUserOrgByUserId() {
    String userId = "1";
    List<String> result = dao.findUserOrgByUserId(userId);
    logger.info("result:{}", result.size());
  }

}
