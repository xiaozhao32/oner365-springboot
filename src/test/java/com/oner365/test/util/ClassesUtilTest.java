package com.oner365.test.util;

import com.oner365.deploy.utils.DeployUtils;
import com.oner365.util.ClassesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class ClassesUtilTest extends BaseUtilsTest {

    @Test
    void test() {
        boolean result = ClassesUtil.isPrimitive(String.class);
        Assertions.assertTrue(result);
    }

    @Test
    void isMac() {
      Boolean b = DeployUtils.isMac();
      Assertions.assertNotNull(b);
    }

}
