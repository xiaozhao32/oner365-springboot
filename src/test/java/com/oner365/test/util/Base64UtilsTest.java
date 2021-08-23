package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.util.Base64Utils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 */
public class Base64UtilsTest extends BaseUtilsTest {

    @Test
    public void test() {
        String str = "Abc";
        String encode = Base64Utils.encodeBase64String(str.getBytes());
        String decode = Base64Utils.decodeBase64String(encode);
        Assertions.assertEquals("Abc", decode);
    }

}
