package com.oner365.test.controller.auth;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test AuthController
 *
 * @author zhaoyong
 *
 */
class AuthControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/auth";

    @Test
    void authLogin() {
        LOGGER.info("authLogin:[{}] -> {}", PATH + "/login", token);
        Assertions.assertNotNull(token);
    }

    @Test
    void captchaImage() {
        String url = PATH + "/captcha";
        Object result = get(url);
        LOGGER.info("captchaImage:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void findMenuByRoles() {
        String url = PATH + "/menu/1";
        ArrayList<?> result = (ArrayList<?>) get(url);
        LOGGER.info("findMenuByRoles:[{}] -> {}", url, result);
        Assertions.assertNotEquals(0, result.size());
    }

    @RepeatedTest(2)
    void findMenuOperByRoles() {
        String url = PATH + "/menu/operation/101";
        ArrayList<?> result = (ArrayList<?>) get(url);
        LOGGER.info("findMenuOperByRoles:[{}] -> {}", url, result);
        Assertions.assertNotEquals(0, result.size());
    }
}
