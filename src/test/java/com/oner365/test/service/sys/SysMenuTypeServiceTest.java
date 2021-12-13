package com.oner365.test.service.sys;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.dto.SysMenuTypeDto;
import com.oner365.sys.service.ISysMenuTypeService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysMenuTypeService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysMenuTypeServiceTest extends BaseServiceTest {

  @Autowired
  private ISysMenuTypeService service;

  @RepeatedTest(value = 2)
  void findList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    List<SysMenuTypeDto> list = service.findList(paramData);
    LOGGER.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void pageList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    PageInfo<SysMenuTypeDto> list = service.pageList(paramData);
    LOGGER.info("pageList:{}", list.getSize());
    Assertions.assertNotEquals(0, list.getSize());
  }

  @Test
  void getById() {
    String id = "1";
    SysMenuTypeDto entity = service.getById(id);
    LOGGER.info("getById:{}", JSON.toJSONString(entity));
    Assertions.assertNotNull(entity);
  }

  @Test
  void getMenuTypeByTypeCode() {
    String code = "nt_sys";
    SysMenuTypeDto entity = service.getMenuTypeByTypeCode(code);
    LOGGER.info("getMenuTypeByTypeCode:{}", JSON.toJSONString(entity));
    Assertions.assertNotNull(entity);
  }

}
