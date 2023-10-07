package com.oner365.test.dao.sys;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

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
  
  @Resource
  private ISysMenuDao dao;
  
  @Resource
  private JdbcTemplate jdbcTemplate;
  
  @Test
  void findMenuByTypeCode() {
    String typeCode = "nt_sys";
    List<SysMenu> result = dao.findMenuByTypeCode(typeCode);
    logger.info("result:{}", result.size());
    Assertions.assertNotEquals(0, result.size());
    
    String sql = "select m.* from nt_sys_menu m, nt_sys_menu_type t where m.menu_type_id=t.id and m.status=1 and t.type_code=?";
    List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, typeCode);
    logger.info("result:{}", resultList.size());
    Assertions.assertNotEquals(0, resultList.size());
  }

}
