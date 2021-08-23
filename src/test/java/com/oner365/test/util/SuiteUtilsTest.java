package com.oner365.test.util;

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
public class SuiteUtilsTest {

    @Test
    public void test() {
        
    }
}
