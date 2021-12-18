package com.oner365.test.util;

import com.oner365.util.AesUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        LOGGER.info("加密: {}", encrypt);
        String decrypt = AesUtils.getDecryptString(encrypt);
        LOGGER.info("解密: {}", decrypt);
        Assertions.assertEquals(data, decrypt);
        AesUtils.removeKey();

        String encrypt2 = AesUtils.getEncryptString(data, key);
        LOGGER.info("加密: {}", encrypt2);
        String decrypt2 = AesUtils.getDecryptString(encrypt2, key);
        LOGGER.info("解密: {}", decrypt2);
        Assertions.assertEquals(data, decrypt2);
        AesUtils.removeKey();
    }
}
