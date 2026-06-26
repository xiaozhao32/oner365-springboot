package com.oner365.test.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;

/**
 * Junit 单元测试服务
 *
 * @author zhaoyong
 *
 */
@SelectPackages({ "com.oner365.test.dao.sys" })
class SuiteDaoTest {

    @Test
    void test() {
        Assertions.assertEquals("SuiteDaoTest", SuiteDaoTest.class.getSimpleName());
    }

}
