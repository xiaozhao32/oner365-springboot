package com.oner365.test.util;

import org.junit.Assert;
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
@RunWith(JUnitPlatform.class)
@SelectPackages({ "com.oner365.test.util" })
class SuiteUtilsTest {

    @Test
    void test() {
    	Assert.assertEquals("SuiteUtilsTest", SuiteUtilsTest.class.getSimpleName());
    }
}
