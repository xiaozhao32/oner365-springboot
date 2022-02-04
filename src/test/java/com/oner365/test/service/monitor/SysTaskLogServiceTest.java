package com.oner365.test.service.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.monitor.dto.SysTaskLogDto;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.test.service.BaseServiceTest;
import com.oner365.util.DataUtils;

/**
 * Test SysTaskLogService
 *
 * @author zhaoyong
 */
@SpringBootTest
class SysTaskLogServiceTest extends BaseServiceTest {

  @Autowired
  private ISysTaskLogService service;

  @RepeatedTest(value = 2)
  void pageList() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    PageInfo<SysTaskLogDto> list = service.pageList(paramData);
    logger.info("pageList:{}", list.getSize());
    Assertions.assertNotEquals(0, list.getSize());
  }

  @Test
  void selectTaskLogById() {
    QueryCriteriaBean paramData = new QueryCriteriaBean();
    PageInfo<SysTaskLogDto> list = service.pageList(paramData);
    if (!DataUtils.isEmpty(list) && !list.getContent().isEmpty()) {
      SysTaskLogDto entity = service.selectTaskLogById(list.getContent().get(0).getId());
      logger.info("selectTaskLogById:{}", JSON.toJSONString(entity));
      Assertions.assertNotNull(entity);
    }
  }

}
