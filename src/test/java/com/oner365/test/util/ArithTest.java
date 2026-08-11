package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.Arith;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class ArithTest extends BaseUtilsTest {

    @Test
    void add() {
        double result = Arith.add(1d, 2d);
        Assertions.assertEquals(3d, result);
    }

    @Test
    void sub() {
        double result = Arith.sub(1d, -2d);
        Assertions.assertEquals(3d, result);
    }

    @Test
    void mul() {
        double result = Arith.mul(2d, 3d);
        Assertions.assertEquals(6d, result);
    }

    @Test
    void div() {
        double result = Arith.div(6d, 2d);
        Assertions.assertEquals(3d, result);
    }

    @Test
    void divScale() {
        double result = Arith.div(2d, 3d, 1);
        Assertions.assertEquals(0.7, result);
    }

    @Test
    void round() {
        double result = Arith.round(1.2345, 3);
        Assertions.assertEquals(1.235, result);
    }

}
