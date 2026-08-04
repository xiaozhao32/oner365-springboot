package com.oner365.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        String geneInfo = "{\"D7S820\": \"10/11\", \"D12S391\": \"18/18\", \"D13S317\": \"11/12\", \"D16S539\": \"10/13\"}";
        List<Map<String, Object>> result = GeneTransFormUtils.geneFormatList(geneInfo);
        // [{"name":"D7S820","value":"10/11"},{"name":"D12S391","value":"18/18"},{"name":"D16S539","value":"10/13"},{"name":"D13S317","value":"11/12"}]
        logger.info("geneFormatList: {}", result);
        Assertions.assertEquals(4, result.size());
    }
    
    @Test
    void geneFormatMapList() {
        Map<String, Object> geneInfo = new HashMap<>();
        geneInfo.put("D7S820", "10/11");
        geneInfo.put("D12S391", "18/18");
        geneInfo.put("D13S317", "11/12");
        geneInfo.put("D16S539", "10/13");
        List<Map<String, Object>> result = GeneTransFormUtils.geneFormatList(geneInfo);
        // [{"name":"D7S820","value":"10/11"},{"name":"D12S391","value":"18/18"},{"name":"D16S539","value":"10/13"},{"name":"D13S317","value":"11/12"}]
        logger.info("geneFormatMapList: {}", result);
        Assertions.assertEquals(4, result.size());
    }

    @Test
    void geneFormatMap() {
        String geneInfo = "[{\"name\": \"D8S1179\", \"value\": \"11/12\"}, {\"name\": \"D2S11\", \"value\": \"9/10\"}]";
        Map<String, Object> result = GeneTransFormUtils.geneFormatMap(geneInfo);
        // {"D2S11":"9/10","D8S1179":"11/12"}
        logger.info("geneFormatMap: {}", result);
        Assertions.assertEquals(2, result.size());
    }
    
    @Test
    void geneFormatListMap() {
        List<Map<String, Object>> geneInfo = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "D8S1179");
        map1.put("value", "11/12");
        geneInfo.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "D2S11");
        map2.put("value", "9/10");
        geneInfo.add(map2);
        Map<String, Object> result = GeneTransFormUtils.geneFormatMap(geneInfo);
        // {"D2S11":"9/10","D8S1179":"11/12"}
        logger.info("geneFormatListMap: {}", result);
        Assertions.assertEquals(2, result.size());
    }
    
    @Test
    void geneTrimString() {
        String geneInfo = "[{\"name\": \"D8S1179\", \"value\": \"11/12\"}, {\"name\": \"D2S11\", \"value\": \"9/10\"}]";
        String result = GeneTransFormUtils.geneTrimString(geneInfo);
        logger.info("geneTrimString: {}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void match() {
        String geneInfo1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String geneInfo2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12/13\"}";
        boolean result = GeneTransFormUtils.match(GeneTransFormUtils.geneFormatMapFromObject(geneInfo1), 
                GeneTransFormUtils.geneFormatMapFromObject(geneInfo2));
        logger.info("match: {}", result);
        Assertions.assertTrue(result);
    }

    @Test
    void matchEquals() {
        String geneInfo1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String geneInfo2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\"}";
        boolean result = GeneTransFormUtils.matchEquals(GeneTransFormUtils.geneFormatMapFromObject(geneInfo1), 
                GeneTransFormUtils.geneFormatMapFromObject(geneInfo2));
        logger.info("matchEquals: {}", result);
        Assertions.assertTrue(result);
    }
    
    @Test
    void matchGeneEquals() {
        String geneInfo1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String geneInfo2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12/13\"}";
        Map<String, Integer> result = GeneTransFormUtils.matchGeneEquals(GeneTransFormUtils.geneFormatMapFromObject(geneInfo2), 
                GeneTransFormUtils.geneFormatMapFromObject(geneInfo1));
        // {matchCount=1, totalCount=2, trimCount=1, diffCount=1}
        logger.info("matchGeneEquals: {}", result);
        Assertions.assertEquals(1, result.get("matchCount"));
        Assertions.assertEquals(2, result.get("totalCount"));
        Assertions.assertEquals(1, result.get("trimCount"));
        Assertions.assertEquals(1, result.get("diffCount"));
    }
    
    @Test
    void matchGeneContains() {
        String geneInfo1 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S539\":\"11/12\"}";
        String geneInfo2 = "{\"D2S11\":\"9/10\",\"D8S1179\":\"11/12\",\"D16S531\":\"11/12\"}";
        Map<String, Integer> result = GeneTransFormUtils.matchGeneContains(GeneTransFormUtils.geneFormatMapFromObject(geneInfo2), 
                GeneTransFormUtils.geneFormatMapFromObject(geneInfo1));
        // {matchCount=2, totalCount=3, trimCount=0, diffCount=1}
        logger.info("matchGeneContains: {}", result);
        Assertions.assertEquals(2, result.get("matchCount"));
        Assertions.assertEquals(3, result.get("totalCount"));
        Assertions.assertEquals(0, result.get("trimCount"));
        Assertions.assertEquals(1, result.get("diffCount"));
    }

}
