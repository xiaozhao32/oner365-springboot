package com.oner365.test.service.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.common.config.properties.AccessTokenProperties;
import com.oner365.test.service.BaseServiceTest;

/**
 * 单元测试 - 获取属性配置
 *
 * @author zhaoyong
 */
@SpringBootTest
class PropertiesTest extends BaseServiceTest {

  @Autowired
  private AccessTokenProperties properties;
  
  @Test
  void filePropertiesTest() {
    Assertions.assertNotNull(properties);
    logger.info("properties:{}", JSON.toJSONString(properties));
  }
}
