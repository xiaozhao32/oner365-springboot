package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.util.Md5Util;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class Md5UtilTest extends BaseUtilsTest {

    @Test
    void test() {
        String result = Md5Util.getInstance().getMd5("1");
        Assertions.assertEquals("c4ca4238a0b923820dcc509a6f75849b", result);
    }

}
