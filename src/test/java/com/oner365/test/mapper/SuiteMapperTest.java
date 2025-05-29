package com.oner365.test.mapper;

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
@SelectPackages({ "com.oner365.test.mapper.sys" })
class SuiteMapperTest {

    @Test
    void test() {
        Assertions.assertEquals("SuiteMapperTest", SuiteMapperTest.class.getSimpleName());
    }

}
