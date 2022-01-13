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
    	System.out.println("token:"+token);
    	System.out.println("解密token："+JwtUtils.getUsernameFromToken(token, key));
    	System.out.println("validateToken:" + JwtUtils.validateToken(token, key));
    	System.out.println("base64 decode:");
    	Arrays.asList(token.split("\\.")).stream().forEach(s -> System.out.println(new String(Base64.getDecoder().decode(s.trim()))));
    	
    }
}
