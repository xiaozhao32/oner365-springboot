package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.util.GeneTransFormUtils;

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
        Assertions.assertEquals(false, result.isEmpty());
    }
    
    @Test
    void geneFormatString() {
        String str = "[{\"name\": \"D8S1179\", \"value\": \"11/12\"}, {\"name\": \"D2S11\", \"value\": \"9/10\"}]";
        JSONObject result = GeneTransFormUtils.geneFormatString(str);
        Assertions.assertEquals(false, result.isEmpty());
    }
    
    @Test
    void match() {
        String str1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String str2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12/13\"}";
        boolean result = GeneTransFormUtils.match(JSON.parseObject(str1), JSON.parseObject(str2));
        Assertions.assertEquals(true, result);
    }
    
    @Test
    void matchEquals() {
        String str1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String str2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\"}";
        boolean result = GeneTransFormUtils.matchEquals(JSON.parseObject(str1), JSON.parseObject(str2));
        Assertions.assertEquals(true, result);
    }

}
