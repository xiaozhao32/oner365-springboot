package com.oner365.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * Junit 单元测试服务
 *
 * @author zhaoyong
 *
 */
@SuppressWarnings("deprecation")
@RunWith(JUnitPlatform.class)
@SelectPackages({
    "com.oner365.test.controller",
    "com.oner365.test.service",
    "com.oner365.test.mapper",
    "com.oner365.test.dao",
    "com.oner365.test.util"
})
class SuiteTest {

    @Test
    void test() {
      Assertions.assertEquals("SuiteTest", SuiteTest.class.getSimpleName());
    }
}
