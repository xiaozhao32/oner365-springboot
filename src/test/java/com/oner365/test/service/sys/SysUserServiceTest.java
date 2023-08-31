package com.oner365.test.service.sys;

import java.util.List;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.dto.SysUserDto;
import com.oner365.sys.service.ISysUserService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysUserService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysUserServiceTest extends BaseServiceTest {

  @Resource
  private ISysUserService service;

  @RepeatedTest(value = 2)
  void findList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    List<SysUserDto> list = service.findList(paramData);
    logger.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void pageList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    PageInfo<SysUserDto> list = service.pageList(paramData);
    logger.info("pageList:{}", list.getSize());
    Assertions.assertNotEquals(0, list.getSize());
  }

  @Test
  void getById() {
    String id = "1";
    SysUserDto entity = service.getById(id);
    logger.info("getById:{}", JSON.toJSONString(entity));
    Assertions.assertNotNull(entity);
  }

  @Test
  void login() {
    LoginUserDto entity = service.login("admin", "1", "localhost");
    logger.info("login:{}", entity);
    Assertions.assertNotNull(entity);
  }

}
