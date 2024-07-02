package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.DesensitizationUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class DesensitizationUtilsTest extends BaseUtilsTest {

  @Test
  void left() {
    String result = DesensitizationUtils.left("张3疯", 1);
    Assertions.assertNotNull(result);
    logger.info("result:{}", result);
  }

  @Test
  void right() {
    String result = DesensitizationUtils.right("张3疯", 1);
    Assertions.assertNotNull(result);
    logger.info("result:{}", result);
  }

  @Test
  void around() {
    String result = DesensitizationUtils.around("13800138000", 3, 2);
    Assertions.assertNotNull(result);
    logger.info("result:{}", result);
  }

  @Test
  void mobileEncrypt() {
    String result = DesensitizationUtils.mobileEncrypt("13800138000");
    Assertions.assertNotNull(result);
    logger.info("result:{}", result);
  }

  @Test
  void idEncrypt() {
    String result = DesensitizationUtils.idEncrypt("13800138000");
    Assertions.assertNotNull(result);
    logger.info("result:{}", result);
  }

  @Test
  void idPassport() {
    String result = DesensitizationUtils.idPassport("13800138000");
    Assertions.assertNotNull(result);
    logger.info("result:{}", result);
  }

  @Test
  void idPassport2() {
    String result = DesensitizationUtils.idPassport("13800138000", 3);
    Assertions.assertNotNull(result);
    logger.info("result:{}", result);
  }
}
