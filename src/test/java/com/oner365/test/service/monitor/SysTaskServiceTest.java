package com.oner365.test.service.monitor;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysTaskService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysTaskServiceTest extends BaseServiceTest {

  @Resource
  private ISysTaskService service;

  @RepeatedTest(value = 2)
  void pageList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    PageInfo<SysTaskDto> list = service.pageList(paramData);
    logger.info("pageList:{}", list.getSize());
    Assertions.assertNotEquals(0, list.getSize());
  }

  @Test
  void selectTaskById() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    PageInfo<SysTaskDto> list = service.pageList(paramData);
    if (!list.getContent().isEmpty()) {
      SysTaskDto entity = service.selectTaskById(list.getContent().get(0).getId());
      logger.info("selectTaskById:{}", JSON.toJSONString(entity));
      Assertions.assertNotNull(entity);
    }
  }

}
