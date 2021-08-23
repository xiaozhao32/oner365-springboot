package com.oner365.test.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.oner365.util.DateUtil;

/**
 * 工具类测试
 * 
 * @author zhaoyong
 *
 */
public class DateUtilsTest extends BaseUtilsTest {

    @Test
    public void test() {
        List<String> result = DateUtil.getDialectDate("2021-05-10", "2021-05-21");
        assertNotNull(result);
    }

}
