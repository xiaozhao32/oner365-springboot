package com.oner365.test.service.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.test.service.BaseServiceTest;
import com.oner365.util.DataUtils;

/**
 * Test SysTaskService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysTaskServiceTest extends BaseServiceTest {

  @Autowired
  private ISysTaskService service;

  @RepeatedTest(value = 2)
  void pageList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    Page<SysTaskDto> list = service.pageList(paramData);
    LOGGER.info("pageList:{}", list.getSize());
    Assertions.assertNotEquals(0, list.getSize());
  }

  @Test
  void selectTaskById() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    Page<SysTaskDto> list = service.pageList(paramData);
    if (!DataUtils.isEmpty(list) && !list.getContent().isEmpty()) {
      SysTaskDto entity = service.selectTaskById(list.getContent().get(0).getId());
      LOGGER.info("selectTaskById:{}", JSON.toJSONString(entity));
      Assertions.assertNotNull(entity);
    }
  }

}
