package com.oner365.test.dao.sys;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.sys.dao.ISysMenuDao;
import com.oner365.sys.entity.SysMenu;
import com.oner365.test.dao.BaseDaoTest;

/**
 * Test SysMenuDao
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysMenuDaoTest extends BaseDaoTest {
  
  @Autowired
  private ISysMenuDao dao;
  
  @Test
  void findMenuByTypeCode() {
    String typeCode = "nt_sys";
    List<SysMenu> result = dao.findMenuByTypeCode(typeCode);
    logger.info("result:{}", result.size());
  }

}
