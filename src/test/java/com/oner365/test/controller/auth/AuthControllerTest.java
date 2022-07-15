package com.oner365.test.controller.auth;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test AuthController
 *
 * @author zhaoyong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/auth";

    @Test
    void authLogin() {
        logger.info("authLogin:[{}] -> {}", PATH + "/login", getToken());
        Assertions.assertNotNull(getToken());
    }

    @Test
    void captchaImage() {
        String url = PATH + "/captcha";
        Object result = get(url);
        logger.info("captchaImage:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void findMenuByRoles() {
        String url = PATH + "/menu";
        ArrayList<?> result = (ArrayList<?>) get(url);
        logger.info("findMenuByRoles:[{}] -> {}", url, result);
        // 需要登录 否则返回空
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void findMenuOperByRoles() {
        String url = PATH + "/menu/operation/101";
        ArrayList<?> result = (ArrayList<?>) get(url);
        logger.info("findMenuOperByRoles:[{}] -> {}", url, result);
        // 需要登录 否则返回空
        Assertions.assertNotNull(result);
    }
}
