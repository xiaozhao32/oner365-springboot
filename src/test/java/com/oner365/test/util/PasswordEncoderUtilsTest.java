package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oner365.util.PasswordEncoderUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class PasswordEncoderUtilsTest extends BaseUtilsTest {

  @Test
  void test() {
    // 加密内容
    String pwd = "1";
    // 加密方式
    PasswordEncoderUtils.Encoder encoding = PasswordEncoderUtils.Encoder.MD5;

    PasswordEncoder encoder = PasswordEncoderUtils.getDelegatingPasswordEncoder(encoding);
    String s = encoder.encode(pwd);
    logger.info("Password Encoder: {}", encoding);
    logger.info("Password result: {}", s);
    boolean b = encoder.matches(pwd, s);
    logger.info("Password matches: {}", b);
    Assertions.assertTrue(b);
  }

}
