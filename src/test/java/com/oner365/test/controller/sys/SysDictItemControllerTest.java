package com.oner365.test.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.test.controller.BaseControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * Test DictItemController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class SysDictItemControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/dict";

    @Test
    public void getTypeById() {
        String url = PATH + "/getTypeById/sys_task_status";
        Object result = get(url);
        LOGGER.info("getTypeById:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findTypeInfoById() {
        String url = PATH + "/findTypeInfoById/sys_task_status";
        Object result = get(url);
        LOGGER.info("findTypeInfoById:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    public void findTypeInfoByCodes() {
        String url = PATH + "/findTypeInfoByCodes";
        JSONObject paramJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("sys_task_status");
        paramJson.put("codes", jsonArray);
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("findTypeInfoByCodes:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    public void findTypeList() {
        String url = PATH + "/findTypeList";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("findTypeList:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    public void findItemList() {
        String url = PATH + "/findItemList";
        JSONObject paramJson = new JSONObject();
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("findItemList:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @Test
    public void getItemById() {
        String url = PATH + "/getItemById/1101";
        Object result = get(url);
        LOGGER.info("getItemById:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    public void findListByCodes() {
        String url = PATH + "/findListByCodes";
        JSONObject paramJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("sys_task_status");
        paramJson.put("codes", jsonArray);
        Object result = post(url, BodyInserters.fromValue(paramJson));
        LOGGER.info("findListByCodes:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }
}
