package com.oner365.test.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.oner365.util.VerifyCodeUtils;

/**
 * 工具类测试
 * 
 * @author zhaoyong
 *
 */
public class VerifyCodeUtilsTest extends BaseUtilsTest {

    @Test
    public void test() {
        String result = VerifyCodeUtils.generateVerifyCode(4);
        assertNotNull(result);
    }

}
