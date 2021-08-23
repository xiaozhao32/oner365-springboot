package com.oner365.test.util;

import com.oner365.util.ClassesUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
public class ClassesUtilTest extends BaseUtilsTest {

    @Test
    public void test() {
        boolean result = ClassesUtil.isPrimitive(String.class);
        assertTrue(result);
    }

}
