package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.Pinyin4jUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class Pinyin4jUtilsTest extends BaseUtilsTest {

    @Test
    void toPinYinLowercase() {
        String str = "你好";
        String result = Pinyin4jUtils.toPinYinLowercase(str);
        Assertions.assertEquals("nihao", result);
    }

    @Test
    void toPinYinUppercase() {
        String str = "你好";
        String result = Pinyin4jUtils.toPinYinUppercase(str);
        Assertions.assertEquals("NIHAO", result);
    }

}
