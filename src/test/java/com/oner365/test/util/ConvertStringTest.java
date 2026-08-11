package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.ConvertString;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class ConvertStringTest extends BaseUtilsTest {

    @Test
    void digitUppercase() {
        double d = 2.3d;
        String result = ConvertString.digitUppercase(d);
        logger.info("result: {}", result);
        Assertions.assertEquals("贰元叁角", result);
    }

}
