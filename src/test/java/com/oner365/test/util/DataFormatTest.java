package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.DataFormat;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class DataFormatTest extends BaseUtilsTest {

    @Test
    void convertDoubleToString() {
        double d = 2.3d;
        String result = DataFormat.convertDoubleToString(d);
        logger.info("result: {}", result);
        Assertions.assertEquals("2.3", result);
    }

    @Test
    void numberToChn() {
        String result = DataFormat.numberToChn(10);
        logger.info("result: {}", result);
        Assertions.assertEquals("一十", result);
    }

}
