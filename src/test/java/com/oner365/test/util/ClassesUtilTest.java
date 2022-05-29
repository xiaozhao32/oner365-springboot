package com.oner365.test.util;

import com.oner365.deploy.utils.DeployUtils;
import com.oner365.util.ClassesUtil;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(result);
    }
    
    @Test 
    void isMac() {
      Boolean b = DeployUtils.isMac();
      assertNotNull(b);
    }

}
