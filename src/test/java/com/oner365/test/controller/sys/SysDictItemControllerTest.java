package com.oner365.test.controller.sys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test DictItemController
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SysDictItemControllerTest extends BaseControllerTest {

    private static final String PATH = "/system/dict";

    @Test
    void getTypeById() {
        String url = PATH + "/type/get/sys_task_status";
        Object result = get(url);
        logger.info("getTypeById:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void findItemByTypeIds() {
        String url = PATH + "/type/codes/list";
        String[] array = new String[] { "sys_task_status" };
        Object result = post(url, BodyInserters.fromValue(array));
        logger.info("findTypeInfoByCodes:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void findTypeList() {
        String url = PATH + "/type/page";
        Object result = post(url, BodyInserters.fromValue(objectMapper.createObjectNode()));
        logger.info("findTypeList:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void findItemList() {
        String url = PATH + "/item/page";
        Object result = post(url, BodyInserters.fromValue(objectMapper.createObjectNode()));
        logger.info("findItemList:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @Test
    void getItemById() {
        String url = PATH + "/item/get/1101";
        Object result = get(url);
        logger.info("getItemById:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

    @RepeatedTest(2)
    void findListByCodes() {
        String url = PATH + "/type/codes/list";
        String[] array = new String[] { "sys_task_status" };
        Object result = post(url, BodyInserters.fromValue(array));
        logger.info("findListByCodes:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
