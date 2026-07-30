package com.oner365.test.controller.sys;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.oner365.sys.entity.SysJob;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test AuthController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SysJobControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/job";

    /**
     * 多线程测试
     */
    @RepeatedTest(10)
    @Execution(ExecutionMode.CONCURRENT)
    void get() {
        String url = PATH + "/get/1";
        Object result = get(url);
        logger.info("get:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(10)
    @Execution(ExecutionMode.CONCURRENT)
    void list() {
        String url = PATH + "/page";
        Object result = post(url, BodyInserters.fromValue(objectMapper.createObjectNode()));
        logger.info("page:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @Test
    @SuppressWarnings("unchecked")
    void save() {
        String savePath = PATH + "/save";
        SysJob entity = new SysJob();
        entity.setJobName("test");
        entity.setJobInfo("test");
        entity.setJobOrder(3);

        Map<String, Object> map = (Map<String, Object>) put(savePath, BodyInserters.fromValue(entity));
        logger.info("save:[{}] -> {}", savePath, map);
        Assertions.assertNotNull(map);

        String deletePath = PATH + "/delete";
        String[] ids = new String[] { map.get("id").toString() };
        Object deleteResult = delete(deletePath, BodyInserters.fromValue(ids));
        logger.info("delete:[{}] -> {}", deletePath, deleteResult);
        Assertions.assertNotNull(deleteResult);
    }

}
