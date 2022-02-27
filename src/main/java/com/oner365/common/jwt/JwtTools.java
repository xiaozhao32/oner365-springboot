package com.oner365.common.jwt;
 
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oner365.util.DateUtil;
 
public class JwtTools {
	/**
	* 生成加密后的token
	* @param body 加密内容
	* @param time  时效
	* @param secret  签名key
	* @return 加密后的token
	*/
	public static String getToken(String body, Date time,String secret) {
		String token = null;
		try {
			token = JWT.create().withIssuer("auth0").withClaim("body",body)
					.withExpiresAt(time)
					.sign(Algorithm.HMAC256(secret));
		} catch (JWTCreationException exception) {
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return token;
	}
 
	/**
	 * 解密
	 * 先验证token是否被伪造，然后解码token。
	 * @param token 字符串token
	 * @param secret 签名key
	 * @return 解密后的DecodedJWT对象，可以读取token中的数据。
	 */
	public static DecodedJWT decodeToken(final String token,String secret) {
		DecodedJWT jwt = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer("auth0").build();
			jwt = verifier.verify(token);
		} catch (JWTVerificationException exception) {
			exception.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return jwt;
	}
 
	public static void main(String[] args) {
		// 生成token
		Date time = DateUtil.after(DateUtil.getDate(), 14400, Calendar.MINUTE);
		JSONObject json = JSON.parseObject("{\"id\":\"CGWU5434122967LGB5E4\",\"tokenType\":\"login\",\"userName\":\"9D11AE753FC3824D79541C7A71EA8EA5\",\"deviceId\":\"1d96163d30db4ea97b7478e39f\"}");
		String token = JwtTools.getToken(json.toJSONString(), time,"test");
		// 打印token
		System.out.println("token: " + token);
		// 解密token
		DecodedJWT jwt = JwtTools.decodeToken(token,"test");
		System.out.println("body: " + jwt.getClaim("body").asString());
	}
}
