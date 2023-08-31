package com.oner365.test.service.sys;

import java.util.List;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.dto.SysOrganizationDto;
import com.oner365.sys.service.ISysOrganizationService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysOrganizationService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysOrganizationServiceTest extends BaseServiceTest {

  @Resource
  private ISysOrganizationService service;

  @Test
  void getById() {
    String id = "110101";
    SysOrganizationDto entity = service.getById(id);
    logger.info("getById:{}", JSON.toJSONString(entity));
    Assertions.assertNotNull(entity);
  }

  @RepeatedTest(value = 2)
  void findListByParentId() {
    List<SysOrganizationDto> list = service.findListByParentId("-1");
    logger.info("findListByParentId:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void getByCode() {
    String code = "110101000000";
    SysOrganizationDto entity = service.getByCode(code);
    logger.info("getByCode:{}", JSON.toJSONString(entity));
    Assertions.assertNotNull(entity);
  }

  @Test
  void findList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    List<SysOrganizationDto> list = service.findList(paramData);
    logger.info("selectList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void selectListByUserId() {
    String userId = "1";
    List<String> list = service.selectListByUserId(userId);
    logger.info("selectListByUserId:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

}
