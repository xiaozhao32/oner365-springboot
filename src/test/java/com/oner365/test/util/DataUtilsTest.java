package com.oner365.test.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.DataUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class DataUtilsTest extends BaseUtilsTest {

    @Test
    void test() {
        assertTrue(DataUtils.isEmpty(null));
    }

}
