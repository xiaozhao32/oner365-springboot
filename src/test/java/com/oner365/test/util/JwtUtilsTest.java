package com.oner365.test.util;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.commons.util.JwtTools;
import com.oner365.data.commons.util.JwtUtils;

/**
 * 工具类测试
 *
 * @author liutao
 *
 */
class JwtUtilsTest extends BaseUtilsTest {

    private static final String KEY = "test";

    private static final Date expireTime = DateUtil.after(DateUtil.getDate(), 1440, Calendar.MINUTE);

    @Test
    void test() {
        JSONObject json = new JSONObject();
        json.put("userName", "test");
        String token = JwtUtils.generateToken(json.toJSONString(), expireTime, KEY);
        // 打印token
        logger.info("io.jsonwebtoken.Jwts token: {}", token);
        Assertions.assertNotEquals(null, token);
        // 解密token
        String body = JwtUtils.getUsernameFromToken(token, KEY);
        logger.info("io.jsonwebtoken.Jwts parse token: {}", body);
        boolean isValid = JwtUtils.validateToken(token, KEY);
        logger.info("io.jsonwebtoken.Jwts validateToken: {}", isValid);
        Assertions.assertEquals(Boolean.TRUE, isValid);

        logger.info("io.jsonwebtoken.Jwts base64 decode:");
        Assertions.assertNotEquals(null, token);
        Arrays.stream(token.split("\\."))
            .filter(s -> s.startsWith("ey"))
            .forEach(s -> logger.info("io.jsonwebtoken.Jwts content:{}",
                    new String(Base64.getDecoder().decode(s.trim()), Charset.defaultCharset())));
    }

    @Test
    void testJwtTools() {
        // 生成token
        JSONObject json = JSON.parseObject(
                "{\"id\":\"CGWU5434122967LGB5E4\",\"tokenType\":\"login\",\"userName\":\"9D11AE753FC3824D79541C7A71EA8EA5\",\"deviceId\":\"1d96163d30db4ea97b7478e39f\"}");
        String token = JwtTools.generateToken(json.toJSONString(), expireTime, KEY);
        // 打印token
        logger.info("com.auth0.jwt token: {}", token);
        Assertions.assertNotEquals(null, token);
        // 解密token
        DecodedJWT jwt = JwtTools.decodeToken(token, KEY);
        logger.info("com.auth0.jwt parse token: {}", Objects.requireNonNull(jwt).getClaim("body").asString());
        boolean isValid = JwtTools.validateToken(token, KEY);
        logger.info("com.auth0.jwt validateToken: {}", isValid);
        Assertions.assertEquals(Boolean.TRUE, isValid);
    }

}
