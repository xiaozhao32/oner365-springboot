package com.oner365.test.controller.auth;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test AuthController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class AuthControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/auth";

    @Test
    public void authLogin() {
        LOGGER.info("authLogin:[{}] -> {}", PATH + "/login", token);
        Assertions.assertNotNull(token);
    }

    @Test
    public void captchaImage() {
        String url = PATH + "/captchaImage";
        Object result = get(url);
        LOGGER.info("captchaImage:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    public void findMenuByRoles() {
        String url = PATH + "/menu/1";
        ArrayList<?> result = (ArrayList<?>) get(url);
        LOGGER.info("findMenuByRoles:[{}] -> {}", url, result);
        Assertions.assertTrue(result.size() != 0);
    }

    @RepeatedTest(2)
    public void findMenuOperByRoles() {
        String url = PATH + "/menu/operation/101";
        ArrayList<?> result = (ArrayList<?>) get(url);
        LOGGER.info("findMenuOperByRoles:[{}] -> {}", url, result);
        Assertions.assertTrue(result.size() != 0);
    }
}
