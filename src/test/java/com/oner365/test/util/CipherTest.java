package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.util.Cipher;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class CipherTest extends BaseUtilsTest {

  @Test
  void test() {
    String data = "Oner365";
    String key = "test";
    
    byte[] result = Cipher.encodeSms4(data.getBytes(), key.getBytes());
    Assertions.assertNotNull(result);
    logger.info("加密结果: {}", result);
    
    String decodeResult = Cipher.decodeSms4toString(result, key.getBytes());
    logger.info("解密结果: {}", decodeResult);
  }
}
