package com.oner365.test.controller.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SysMessageController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysMessageControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/message";

    @Test
    void refresh() {
        String url = PATH + "/refresh?messageType=test";
        Object result = get(url);
        LOGGER.info("refresh:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
