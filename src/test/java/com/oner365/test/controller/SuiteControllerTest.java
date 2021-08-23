package com.oner365.test.controller;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * Junit 单元测试 Controller
 * 
 * @author zhaoyong
 *
 */
@RunWith(JUnitPlatform.class)
@SelectPackages({
    "com.oner365.test.controller.auth",
    "com.oner365.test.controller.datasource",
    "com.oner365.test.controller.elasticsearch",
    "com.oner365.test.controller.files",
    "com.oner365.test.controller.gateway",
    "com.oner365.test.controller.monitor",
    "com.oner365.test.controller.sys"
})
public class SuiteControllerTest {

    @Test
    public void test() {
        
    }
}
