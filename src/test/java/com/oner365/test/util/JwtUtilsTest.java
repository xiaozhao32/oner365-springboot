package com.oner365.test.util;

import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

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
    	LOGGER.info("token:{}",token);
    	LOGGER.info("解密token:{}",JwtUtils.getUsernameFromToken(token, key));
    	LOGGER.info("validateToken:{}",JwtUtils.validateToken(token, key));
    	LOGGER.info("base64 decode:");
    	Arrays.stream(token.split("\\.")).filter(s-> s.startsWith("ey")).forEach(s -> LOGGER.info("content:{}",new String(Base64.getDecoder().decode(s.trim()))));
    	
    }
}
