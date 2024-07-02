package com.oner365.test.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

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
        String result = VerifyCodeUtils.generateVerifyCode(4);
        assertNotNull(result);
    }

}
