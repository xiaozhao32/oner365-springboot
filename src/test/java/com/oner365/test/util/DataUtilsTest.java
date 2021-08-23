package com.oner365.test.util;

import com.oner365.util.DataUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
public class DataUtilsTest extends BaseUtilsTest {

    @Test
    public void test() {
        assertTrue(DataUtils.isEmpty(null));
    }

}
