package com.oner365.test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.pulsar.shade.org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.SecureRandomGenerator;
import com.oner365.data.commons.util.VerifyCodeUtils;

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
        String result = SecureRandomGenerator.randomAlphanumeric(4);
        logger.info("generateVerifyCode: {}", result);
        Assertions.assertNotNull(result);

        File file = new File("/Users/zhaoyong/Downloads/123.png");
        try (FileOutputStream os = new FileOutputStream(file)) {
            VerifyCodeUtils.outputImage(160, 80, os, result);
            Assertions.assertEquals(true, file.exists());

            FileUtils.delete(file);
            Assertions.assertEquals(false, file.exists());
        }
        catch (Exception e) {
            logger.error("outputImage error", e);
        }
    }

}
