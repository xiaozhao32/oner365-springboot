package com.oner365.test.dao.sys;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.sys.dao.ISysMenuOperDao;
import com.oner365.test.dao.BaseDaoTest;

/**
 * Test SysMenuOperDao
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysMenuOperDaoTest extends BaseDaoTest {
  
  @Resource
  private ISysMenuOperDao dao;
  
  @Test
  void selectByMenuId() {
    String menuId = "101";
    List<String> result = dao.selectByMenuId(menuId);
    logger.info("result:{}", result.size());
    Assertions.assertNotEquals(0, result.size());
  }

}
