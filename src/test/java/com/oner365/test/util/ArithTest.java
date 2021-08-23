package com.oner365.test.util;

import com.oner365.util.Arith;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
public class ArithTest extends BaseUtilsTest {

    @Test
    public void add() {
        double result = Arith.add(1d, 2d);
        Assertions.assertEquals(0, 3d, result);
    }

    @Test
    public void sub() {
        double result = Arith.sub(1d, -2d);
        Assertions.assertEquals(0, 3d, result);
    }
}
