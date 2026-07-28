package com.oner365.test.util;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.SecureRandomGenerator;

/**
 * 工具类测试
 * 
 * @author zhaoyong
 *
 */
class VerifyCodeUtilsTest extends BaseUtilsTest {

    @Test
    void test() {
        int number = ThreadLocalRandom.current().nextInt(100);
        logger.info("ThreadLocalRandom:{}", number);
        String result = SecureRandomGenerator.randomAlphanumeric(10);
        logger.info("generateVerifyCode: {}", result);
        Assertions.assertNotNull(result);
    }

}
