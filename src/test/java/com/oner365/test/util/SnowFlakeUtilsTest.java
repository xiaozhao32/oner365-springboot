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
public class SnowFlakeUtilsTest extends BaseUtilsTest {

    @Test
    public void test() {
        long result = new SnowFlakeUtils(1L, 1L).nextId();
        Assertions.assertNotNull(result);
    }

}
