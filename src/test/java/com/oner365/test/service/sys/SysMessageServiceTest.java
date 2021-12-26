package com.oner365.test.service.sys;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.service.ISysMessageService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysMessageService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysMessageServiceTest extends BaseServiceTest {

  @Autowired
  private ISysMessageService service;

  @Test
  void getById() {
    String messageType = "test";
    List<SysMessageDto> list = service.findList(messageType);
    if (!list.isEmpty()) {
      SysMessageDto entity = service.getById(list.get(0).getId());
      LOGGER.info("getById:{}", JSON.toJSONString(entity));
      Assertions.assertNotNull(entity);
    }
  }

}
