package com.oner365.test.service;

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
@RunWith(JUnitPlatform.class)
@SelectPackages({ "com.oner365.test.service.datasource", "com.oner365.test.service.elasticsearch",
        "com.oner365.test.service.file", "com.oner365.test.service.gateway", "com.oner365.test.service.monitor",
        "com.oner365.test.service.sys" })
class SuiteServiceTest {

    @Test
    void test() {
        Assertions.assertEquals("SuiteServiceTest", SuiteServiceTest.class.getSimpleName());
    }

}
