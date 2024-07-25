package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.AesUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class AesUtilsTest extends BaseUtilsTest {

    @Test
    void test() {
        String data = "Oner365";
        String key = "abc1234";
        String encrypt = AesUtils.getEncryptString(data);
        logger.info("加密: {}", encrypt);
        String decrypt = AesUtils.getDecryptString(encrypt);
        logger.info("解密: {}", decrypt);
        Assertions.assertEquals(data, decrypt);
        AesUtils.removeKey();

        String encrypt2 = AesUtils.getEncryptString(data, key);
        logger.info("加密: {}", encrypt2);
        String decrypt2 = AesUtils.getDecryptString(encrypt2, key);
        logger.info("解密: {}", decrypt2);
        Assertions.assertEquals(data, decrypt2);
        AesUtils.removeKey();
    }
}
