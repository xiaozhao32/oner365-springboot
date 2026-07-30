package com.oner365.test.util;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.util.GeneTransFormUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class GeneTransFormUtilsTest extends BaseUtilsTest {

    @Test
    void geneFormatList() {
        String str = "{\"D7S820\": \"10/11\", \"D12S391\": \"18/18\", \"D13S317\": \"11/12\", \"D16S539\": \"10/13\"}";
        JSONArray result = GeneTransFormUtils.geneFormatList(str);
        logger.info("geneFormatList: {}", result);
        Assertions.assertEquals(4, result.size());
    }

    @Test
    void geneFormatString() {
        String str = "[{\"name\": \"D8S1179\", \"value\": \"11/12\"}, {\"name\": \"D2S11\", \"value\": \"9/10\"}]";
        JSONObject result = GeneTransFormUtils.geneFormatString(str);
        logger.info("geneFormatString: {}", result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void match() {
        String str1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String str2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12/13\"}";
        boolean result = GeneTransFormUtils.match(JSON.parseObject(str1), JSON.parseObject(str2));
        logger.info("match: {}", result);
        Assertions.assertTrue(result);
    }

    @Test
    void matchEquals() {
        String str1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String str2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\"}";
        boolean result = GeneTransFormUtils.matchEquals(JSON.parseObject(str1), JSON.parseObject(str2));
        logger.info("matchEquals: {}", result);
        Assertions.assertTrue(result);
    }
    
    @Test
    void matchGeneEquals() {
        String str1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String str2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\"}";
        Map<String, Integer> result = GeneTransFormUtils.matchGeneEquals(JSON.parseObject(str2), JSON.parseObject(str1));
        logger.info("matchGeneEquals: {}", result);
        Assertions.assertEquals(2, result.get("matchCount"));
    }
    
    @Test
    void matchGeneContains() {
        String str1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String str2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S531\":\"11/12\"}";
        Map<String, Integer> result = GeneTransFormUtils.matchGeneContains(JSON.parseObject(str2), JSON.parseObject(str1));
        logger.info("matchGeneContains: {}", result);
        Assertions.assertEquals(1, result.get("diffCount"));
    }

}
