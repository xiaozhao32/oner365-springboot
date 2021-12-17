package com.oner365.test.util;

import org.junit.jupiter.api.Test;

import com.oner365.util.RsaUtils;

import java.util.Map;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class RsaUtilsTest extends BaseUtilsTest {

  @Test
  void test() {
    String data = "Oner365";
    Map<String, String> map = RsaUtils.initRsaKey(2048);
    LOGGER.info("key: {}", map);
    String s = RsaUtils.encrypt(data);
    LOGGER.info("公钥加密: {}", s);
    String s2 = RsaUtils.decrypt(s);
    LOGGER.info("私钥解密: {}", s2);

    String e = RsaUtils.buildRsaEncryptByPublicKey(data, map.get("publicKey"));
    LOGGER.info("公钥加密: {}", e);
    String e2 = RsaUtils.buildRsaDecryptByPrivateKey(e, map.get("privateKey"));
    LOGGER.info("私钥解密: {}", e2);

    String sign = RsaUtils.buildRsaSignByPrivateKey(data, map.get("privateKey"));
    LOGGER.info("签名: {}", sign);
    boolean verify = RsaUtils.buildRsaVerifyByPublicKey(data, map.get("publicKey"), sign);
    LOGGER.info("验证签名: {}", verify);

    String p = RsaUtils.buildRsaEncryptByPrivateKey(data, RsaUtils.PRIVATE_KEY);
    LOGGER.info("私钥加密: {}", p);
    String p2 = RsaUtils.buildRsaDecryptByPublicKey(p, RsaUtils.PUBLIC_KEY);
    LOGGER.info("公钥解密: {}", p2);
  }
}
