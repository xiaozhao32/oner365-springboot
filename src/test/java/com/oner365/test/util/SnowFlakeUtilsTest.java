package com.oner365.test.util;

import com.oner365.util.SnowFlakeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class SnowFlakeUtilsTest extends BaseUtilsTest {

    @Test
    void test() {
        Long result = new SnowFlakeUtils(1L, 1L).nextId();
        Assertions.assertNotNull(result);
    }

}
