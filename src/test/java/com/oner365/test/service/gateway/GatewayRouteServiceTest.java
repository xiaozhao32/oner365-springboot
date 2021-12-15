package com.oner365.test.service.gateway;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test GatewayRouteService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class GatewayRouteServiceTest extends BaseServiceTest {

  @Autowired
  private DynamicRouteService service;

  @RepeatedTest(value = 2)
  void findList() {
    List<GatewayRouteDto> list = service.findList();
    LOGGER.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

  @Test
  void pageList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    PageInfo<GatewayRouteDto> list = service.pageList(paramData);
    LOGGER.info("pageList:{}", list.getSize());
    Assertions.assertNotEquals(0, list.getSize());
  }

  @Test
  void getById() {
    List<GatewayRouteDto> list = service.findList();
    if (!list.isEmpty()) {
      GatewayRouteDto entity = service.getById(list.get(0).getId());
      LOGGER.info("getById:{}", JSON.toJSONString(entity));
      Assertions.assertNotNull(entity);
    }
  }

}
