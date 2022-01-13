/*
 *    Copyright 2016 10gen Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.oner365.common.jwt;

import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;
import com.oner365.util.RsaUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JavaWebToken常用类
 *
 * @author zhaoyong
 */
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    private JwtUtils() {

    }

    /**
     * 创建token
     *
     * @param username 账号
     * @param days     天
     * @param secret   密钥
     * @return String
     */
    public static String generateToken(String username, int days, String secret) {
    	String data = RsaUtils.encrypt(username);
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", data);
        claims.put("created", DateUtil.getDate());
        return Jwts.builder().setClaims(claims).setExpiration(DateUtil.getDateAfter(days))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 创建token
     *
     * @param username 账号
     * @param expired  过期时间
     * @param secret   密钥
     * @return String
     */
    public static String generateToken(String username, Date expired, String secret) {
    	String data = RsaUtils.encrypt(username);
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", data);
        claims.put("created", DateUtil.getDate());
        return Jwts.builder().setClaims(claims).setExpiration(expired).signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 通过Token获取token中的用户名
     *
     * @param token  令牌
     * @param secret 秘钥
     * @return String
     */
    public static String getUsernameFromToken(String token, String secret) {
        if (DataUtils.isEmpty(token)) {
            return null;
        }
        final Claims claims = getClaimsFromToken(token, secret);
        if (claims != null) {
            return RsaUtils.decrypt(claims.getSubject());
        }
        return null;
    }

    /**
     * 从token中读取Claims对象
     *
     * @param token  令牌
     * @param secret 加密秘钥
     * @return Claims
     */
    private static Claims getClaimsFromToken(String token, String secret) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            LOGGER.error("token: {}, 已过期: {}", token, e.getMessage());
            return e.getClaims();
        }
    }

    /**
     * 验证token有效性
     *
     * @param token  令牌
     * @param secret 加密秘钥
     * @return Boolean
     */
    public static Boolean validateToken(String token, String secret) {
        final String username = getUsernameFromToken(token, secret);
        if (username != null) {
            JSONObject json = JSON.parseObject(username);
            return (!DataUtils.isEmpty(json.getString("userName")) && !isTokenExpired(token, secret));
        }
        return false;
    }

    /**
     * 验证token是否过期
     *
     * @param token  令牌
     * @param secret 加密秘钥
     * @return Boolean
     */
    private static Boolean isTokenExpired(String token, String secret) {
        final Date expiration = getExpirationDateFromToken(token, secret);
        if (expiration != null) {
            return expiration.before(DateUtil.getDate());
        }
        return Boolean.TRUE;
    }

    /**
     * 在access_token中获取有效期
     *
     * @param token  令牌
     * @param secret 秘钥
     * @return Date
     */
    public static Date getExpirationDateFromToken(String token, String secret) {
        Date expiration = null;
        final Claims claims = getClaimsFromToken(token, secret);
        if (claims != null) {
            expiration = claims.getExpiration();
        }
        return expiration;
    }
    
    public static void main (String[] args) {
    	JSONObject json = new JSONObject();
    	json.put("userName", "test");
    	String key = "test";
    	Date time = DateUtil.after(DateUtil.getDate(), 1440, Calendar.MINUTE);
    	String token = JwtUtils.generateToken(json.toJSONString(), time, key);
    	System.out.println("token:"+token);
    	System.out.println("解密token："+JwtUtils.getUsernameFromToken(token, key));
    	System.out.println("isTokenExpired:" + JwtUtils.isTokenExpired(token, key));
    	System.out.println("validateToken:" + JwtUtils.validateToken(token, key));
    	System.out.println("base64 decode:");
    	Arrays.asList(token.split("\\.")).stream().forEach(s -> System.out.println(new String(Base64.getDecoder().decode(s))));
    	
    }

}
