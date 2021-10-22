package com.oner365.test.controller.files;

import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * Test AuthController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class FastDfsFilesControllerTest extends BaseControllerTest {

    private static final String PATH = "/files/fdfs";

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/list";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("list:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
