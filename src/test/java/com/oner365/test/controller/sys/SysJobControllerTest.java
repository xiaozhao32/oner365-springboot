package com.oner365.test.controller.sys;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.constants.PublicConstants;
import com.oner365.sys.entity.SysJob;
import com.oner365.test.controller.BaseControllerTest;

/**
 * Test AuthController
 *
 * @author zhaoyong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SysJobControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/job";

    @Test
    void get() {
        String url = PATH + "/get/1";
        Object result = get(url);
        logger.info("get:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/list";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        logger.info("list:[{}] -> {}", url, result);
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

        Map<String, Object> map = (Map<String, Object>) put(savePath, BodyInserters.fromValue(JSON.toJSON(entity)));
        logger.info("save:[{}] -> {}", savePath, map);
        Assertions.assertNotNull(map);

        String deletePath = PATH + "/delete";
        Map<String, Object> sysJob = (Map<String, Object>) map.get(PublicConstants.MSG);
        String[] ids = new String[] { sysJob.get("id").toString() };
        Object deleteResult = delete(deletePath, BodyInserters.fromValue(ids));
        logger.info("delete:[{}] -> {}", deletePath, deleteResult);
        Assertions.assertNotNull(deleteResult);
    }

}
