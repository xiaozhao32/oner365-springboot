package com.oner365.test.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;

/**
 * Junit 单元测试 Controller
 *
 * @author zhaoyong
 *
 */
@SelectPackages({ "com.oner365.test.controller.auth", "com.oner365.test.controller.datasource",
        "com.oner365.test.controller.elasticsearch", "com.oner365.test.controller.files",
        "com.oner365.test.controller.gateway", "com.oner365.test.controller.monitor",
        "com.oner365.test.controller.sys" })
class SuiteControllerTest {

    @Test
    void test() {
        Assertions.assertEquals("SuiteControllerTest", SuiteControllerTest.class.getSimpleName());
    }

}
