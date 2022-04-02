package com.oner365.test.util;

import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oner365.common.jwt.JwtTools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.jwt.JwtUtils;
import com.oner365.util.DateUtil;

/**
 * 工具类测试
 *
 * @author liutao
 *
 */
class JwtUtilsTest extends BaseUtilsTest {

  @Test
  void test() {
    JSONObject json = new JSONObject();
    json.put("userName", "test");
    String key = "test";
    Date time = DateUtil.after(DateUtil.getDate(), 1440, Calendar.MINUTE);
    String token = JwtUtils.generateToken(json.toJSONString(), time, key);
    logger.info("token:{}", token);
    logger.info("解密token:{}", JwtUtils.getUsernameFromToken(token, key));
    logger.info("validateToken:{}", JwtUtils.validateToken(token, key));
    logger.info("base64 decode:");
    Assertions.assertNotEquals(null, token);
    Arrays.stream(token.split("\\.")).filter(s -> s.startsWith("ey"))
        .forEach(s -> logger.info("content:{}", new String(Base64.getDecoder().decode(s.trim()))));
  }

  @Test
  void testJwtTools() {
    // 生成token
    Date time = DateUtil.after(DateUtil.getDate(), 14400, Calendar.MINUTE);
    JSONObject json = JSON.parseObject(
        "{\"id\":\"CGWU5434122967LGB5E4\",\"tokenType\":\"login\",\"userName\":\"9D11AE753FC3824D79541C7A71EA8EA5\",\"deviceId\":\"1d96163d30db4ea97b7478e39f\"}");
    String token = JwtTools.getToken(json.toJSONString(), time, "test");
    // 打印token
    logger.info("token: {}", token);
    Assertions.assertNotEquals(null, token);
    // 解密token
    DecodedJWT jwt = JwtTools.decodeToken(token, "test");
    logger.info("body: {}", jwt.getClaim("body").asString());
  }
}
