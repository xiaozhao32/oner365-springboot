package com.oner365.data.commons.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JavaWebToken常用类
 *
 * @author zhaoyong
 */
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * Token 解析中的参数
     */
    private static final String TOKEN_USER_NAME = "userName";

    /**
     * 密钥可放在配置文件
     */
    private static final String SECRET = "test";

    private JwtUtils() {

    }

    private static SecretKey getSecureKey() {
        return getSecureKey(SECRET);
    }

    private static SecretKey getSecureKey(String secret) {
        try {
            // 确保密钥至少512位（64字节）
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

            if (keyBytes.length < 64) {
                // 使用SHA-512扩展密钥
                MessageDigest digest = MessageDigest.getInstance("SHA-512");
                byte[] hashedBytes = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
                // 再次哈希确保长度
                byte[] finalBytes = new byte[64];
                System.arraycopy(hashedBytes, 0, finalBytes, 0, Math.min(hashedBytes.length, 64));
                return Keys.hmacShaKeyFor(finalBytes);
            }

            return Keys.hmacShaKeyFor(keyBytes);
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.error("获取密钥失败", e);
            // 降级方案：使用随机密钥
            return Jwts.SIG.HS512.key().build();
        }
    }

    /**
     * 创建token
     * @param username 账号
     * @param expired 过期时间
     * @param secret 密钥
     * @return String
     */
    public static String generateToken(String username, Date expired, String secret) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", RsaUtils.encrypt(username));
        claims.put("created", DateUtil.getDate());

        SecretKey key = getSecureKey();
        return Jwts.builder().claims(claims).expiration(expired).signWith(key).compact();
    }

    /**
     * 通过Token获取token中的用户名
     * @param token 令牌
     * @param secret 秘钥
     * @return String
     */
    public static String getUsernameFromToken(String token, String secret) {
        if (DataUtils.isEmpty(token)) {
            return null;
        }
        final Claims claims = getClaimsFromToken(token, secret);
        if (claims != null && claims.getSubject() != null) {
            return RsaUtils.decrypt(claims.getSubject());
        }
        return null;
    }

    /**
     * 从token中读取Claims对象
     * @param token 令牌
     * @param secret 加密秘钥
     * @return Claims
     */
    private static Claims getClaimsFromToken(String token, String secret) {
        try {
            SecretKey key = getSecureKey();
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        }
        catch (ExpiredJwtException e) {
            LOGGER.error("token: {}, 已过期: {}", token, e.getMessage());
            return e.getClaims();
        }
        catch (Exception e) {
            LOGGER.error("getClaimsFromToken error", e);
        }
        return null;
    }

    /**
     * 验证token有效性
     * @param token 令牌
     * @param secret 加密秘钥
     * @return Boolean
     */
    public static Boolean validateToken(String token, String secret) {
        final String userName = getUsernameFromToken(token, secret);
        if (userName != null) {
            return (!DataUtils.isEmpty(JSON.parseObject(userName).getString(TOKEN_USER_NAME))
                    && !isTokenExpired(token, secret));
        }
        return false;
    }

    /**
     * 验证token是否过期
     * @param token 令牌
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
     * @param token 令牌
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

}
