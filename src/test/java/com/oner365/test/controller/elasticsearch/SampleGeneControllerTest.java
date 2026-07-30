package com.oner365.test.controller.elasticsearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;

import com.oner365.test.controller.BaseControllerTest;

/**
 * Test SampleGene Controller
 *
 * @author zhaoyong
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleGeneControllerTest extends BaseControllerTest {

    private static final String PATH = "/elasticsearch/sample/gene";

    @RepeatedTest(2)
    void list() {
        String url = PATH + "/page";
        Object result = post(url, BodyInserters.fromValue(objectMapper.createObjectNode()));
        logger.info("page:[{}] -> {}", url, result);
        Assertions.assertNotNull(result);
    }

}
