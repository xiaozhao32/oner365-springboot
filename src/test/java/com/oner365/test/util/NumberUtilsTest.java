package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.NumberUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class NumberUtilsTest extends BaseUtilsTest {

    @Test
    void test() {
        double result = NumberUtils.format2precision(0.156d);
        Assertions.assertEquals(0.16d, result);
    }

}
