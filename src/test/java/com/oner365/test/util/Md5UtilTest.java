package com.oner365.test.util;

import java.security.MessageDigest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.Md5Util;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class Md5UtilTest extends BaseUtilsTest {

    private final String str = "1";

    @Test
    void testMd5() {
        String result1 = Md5Util.getInstance().getMd5(str);
        Assertions.assertEquals("c4ca4238a0b923820dcc509a6f75849b", result1);
        String result2 = getMd5(str, "MD5");
        Assertions.assertEquals(result1, result2);
    }

    @Test
    void testSha1() {
        String result1 = Md5Util.getInstance().getSha1(str);
        Assertions.assertEquals("356a192b7913b04c54574d18c28d46e6395428ab", result1);
        String result2 = getMd5(str, "SHA1");
        Assertions.assertEquals(result1, result2);
    }

    private String getMd5(String str, String instance) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(instance);
            byte[] bytes = messageDigest.digest(str.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(Integer.toHexString(bt));
            }
            return stringBuilder.toString();
        }
        catch (Exception e) {
            logger.error("getMd5 error", e);
        }
        return null;
    }

}
