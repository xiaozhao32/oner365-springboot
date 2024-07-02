package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oner365.data.commons.util.PasswordEncoderUtils;
import com.oner365.data.commons.util.PasswordEncoderUtils.Encoder;

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
    
    getPasswordEncoder(pwd, Encoder.ARGON2);
    getPasswordEncoder(pwd, Encoder.BCRYPT);
    getPasswordEncoder(pwd, Encoder.PBKDF2);
    getPasswordEncoder(pwd, Encoder.SCRYPT);
    
  }
  
  private void getPasswordEncoder(String pwd, Encoder encoder) {
    // 加密方式
    PasswordEncoder passwordEncoder = PasswordEncoderUtils.getDelegatingPasswordEncoder(encoder);
    String result = passwordEncoder.encode(pwd);
    logger.info("Password result: {}", result);
    boolean matches = passwordEncoder.matches(pwd, result);
    logger.info("Password Encoder: {}, matches: {}", encoder, matches);
    Assertions.assertTrue(matches);
  }

}
