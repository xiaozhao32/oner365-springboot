package com.oner365.data.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.oner365.data.commons.constants.PublicConstants;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

/**
 * 基因型工具类
 *
 * @author zhaoyong
 *
 */
public class GeneTransFormUtils {

    private static final String GENE_NAME = "name";

    private static final String GENE_VALUE = "value";

    /**
     * 构造方法
     */
    private GeneTransFormUtils() {
    }

    /**
     * 转换基因格式 转换格式 {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539":
     * "10/13"} 目标类型 [{"name": "D8S1179", "value": "11,12"}, {"name": "D2S11", "value":
     * "9,10"} ......]
     * @param geneInfo 基因型
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> geneFormatList(Map<String, Object> geneInfo) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (geneInfo != null && !geneInfo.isEmpty()) {
            for (Map.Entry<String, Object> entry : geneInfo.entrySet()) {
                Map<String, Object> map = new HashMap<>();
                map.put(GENE_NAME, entry.getKey());
                map.put(GENE_VALUE, entry.getValue());
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 转换基因格式 转换格式 {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539":
     * "10/13"} 目标类型 [{"name": "D8S1179", "value": "11,12"}, {"name": "D2S11", "value":
     * "9,10"} ......]
     * @param geneInfo 基因型
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> geneFormatList(String geneInfo) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (geneInfo != null && !geneInfo.trim().isEmpty()) {
            JsonObject jsonObject = GsonUtils.jsonToBean(geneInfo, JsonObject.class);
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                Map<String, Object> map = new HashMap<>();
                map.put(GENE_NAME, entry.getKey());
                map.put(GENE_VALUE, entry.getValue().getAsString());
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 转换基因格式（对象格式） 输入格式: {"D2S11":"9/10","D8S1179":"11/12","D16S539":"11/12"} 输出格式:
     * Map<String, Object>
     * @param geneInfo 基因型JSON对象字符串
     * @return Map<String, Object>
     */
    public static Map<String, Object> geneFormatMapFromObject(String geneInfo) {
        Map<String, Object> result = new HashMap<>();
        if (geneInfo != null && !geneInfo.trim().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            // 直接解析JSON对象为Map
            result = mapper.readValue(geneInfo, new TypeReference<Map<String, Object>>() {
            });
        }
        return result;
    }

    /**
     * 转换基因格式 目标类型 [{"name": "D8S1179", "value": "11,12"}, {"name": "D2S11", "value":
     * "9,10"} ......] 转换格式 {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12",
     * "D16S539": "10/13"}
     * @param geneInfo 基因型
     * @return Map<String, Object>
     */
    public static Map<String, Object> geneFormatMap(String geneInfo) {
        Map<String, Object> result = new HashMap<>();
        if (geneInfo != null && !geneInfo.trim().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            // 解析JSON数组为List<Map>
            List<Map<String, Object>> list = mapper.readValue(geneInfo, new TypeReference<List<Map<String, Object>>>() {
            });
            // 遍历转换为Map
            for (Map<String, Object> item : list) {
                Object name = item.get(GENE_NAME);
                Object value = item.get(GENE_VALUE);
                if (name != null) {
                    result.put(name.toString(), value);
                }
            }
        }
        return result;
    }

    /**
     * 转换基因格式 目标类型 [{"name": "D8S1179", "value": "11,12"}, {"name": "D2S11", "value":
     * "9,10"} ......] 转换格式 {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12",
     * "D16S539": "10/13"}
     * @param geneInfo 基因型
     * @return Map<String, Object>
     */
    public static Map<String, Object> geneFormatMap(List<Map<String, Object>> geneInfo) {
        Map<String, Object> result = new HashMap<>();
        if (geneInfo != null && !geneInfo.isEmpty()) {
            // 遍历转换为Map
            for (Map<String, Object> item : geneInfo) {
                Object name = item.get(GENE_NAME);
                Object value = item.get(GENE_VALUE);
                if (name != null) {
                    result.put(name.toString(), value);
                }
            }
        }
        return result;
    }

    /**
     * 过滤空基因 (字符串冒号后面不能有空格) 转换格式 {"D7S820":"", "D12S391":"18/18", "D13S317":"11/12",
     * "D16S539":"10/13"} 目标类型 {"D12S391":"18/18", "D13S317":"11/12", "D16S539":"10/13"}
     * @param geneInfo 基因型
     * @return String
     */
    public static String geneTrimString(String geneInfo) {
        return geneInfo.replaceAll("((?<=\\{)\"\\w+\":\"\",|,*\"\\w+\":\"\")", PublicConstants.EMPTY);
    }

    /**
     * 判断2个json是否包含 后者是否包含前者，包含返回true 否则返回false
     * @param matchJson 被比对单一基因型
     * @param geneJson 混合基因型
     * @return boolean
     */
    public static boolean match(Map<String, Object> matchJson, Map<String, Object> geneJson) {
        return matchJson.entrySet().stream().allMatch(entry -> {
            String key = entry.getKey();
            Optional<Object> optional = Optional.ofNullable(geneJson.get(key));

            if (!optional.isPresent()) {
                // geneJson 不包含该 key，跳过
                return true;
            }

            String matchVal = entry.getValue().toString();
            String geneVal = optional.get().toString();

            Set<String> matchSet = new HashSet<>(Arrays.asList(matchVal.split(PublicConstants.DELIMITER)));
            Set<String> geneSet = new HashSet<>(Arrays.asList(geneVal.split(PublicConstants.DELIMITER)));

            // 检查matchSet中所有元素是否都在geneSet中
            return geneSet.containsAll(matchSet);
        });
    }

    /**
     * 判断2个json是否包含 后者是否包含前者，包含返回true 否则返回false
     * @param matchJson 被比中的单一基因型
     * @param geneJson 单一基因型
     * @return boolean
     */
    public static boolean matchEquals(Map<String, Object> matchJson, Map<String, Object> geneJson) {
        return geneJson.entrySet().stream().allMatch(entry -> {
            String key = entry.getKey();
            Object matchVal = matchJson.get(key);
            Object geneVal = entry.getValue();

            // 如果两个值都为null，返回true；如果只有一个为null，返回false
            if (matchVal == null && geneVal == null) {
                return true;
            }
            if (matchVal == null || geneVal == null) {
                return false;
            }

            // 比较字符串值
            return geneVal.toString().equals(matchVal.toString());
        });
    }

    /**
     * 同一比对测试
     * @param matchJson 比对基因
     * @param geneJson 目标基因
     * @return Map { matchCount: 比中数, totalCount: 总个数, trimCount: 空的数, diffCount: 不同数 }
     */
    public static Map<String, Integer> matchGeneEquals(Map<String, Object> matchJson, Map<String, Object> geneJson) {
        int diff = 0;
        int trim = 0;
        int match = 0;

        for (Map.Entry<String, Object> entry : geneJson.entrySet()) {
            String key = entry.getKey();
            Object matchVal = matchJson.get(key);
            Object geneVal = entry.getValue();

            if (matchVal != null && geneVal != null) {
                if (geneVal.toString().equals(matchVal.toString())) {
                    match++;
                }
                else {
                    diff++;
                }
            }
            else {
                trim++;
            }
        }

        return getCount(matchJson, match, diff, trim);
    }

    /**
     * 混合比对测试
     * @param matchJson 比对基因
     * @param geneJson 目标基因
     * @return Map { matchCount: 比中数, totalCount: 总个数, trimCount: 空的数, diffCount: 不同数 }
     */
    public static Map<String, Integer> matchGeneContains(Map<String, Object> matchJson, Map<String, Object> geneJson) {
        int diff = 0;
        int trim = 0;
        int match = 0;
        for (Map.Entry<String, Object> entry : matchJson.entrySet()) {
            String key = entry.getKey();
            Object geneVal = geneJson.get(key);
            Object matchVal = matchJson.get(key);

            if (geneVal != null) {
                String matchStr = matchVal != null ? matchVal.toString() : "";
                String geneStr = geneVal.toString();

                Set<String> value = new HashSet<>(Arrays.asList(matchStr.split(PublicConstants.DELIMITER)));
                Set<String> gene = new HashSet<>(Arrays.asList(geneStr.split(PublicConstants.DELIMITER)));
                Set<String> s = Sets.difference(value, gene);
                if (s.isEmpty()) {
                    match++;
                }
            }
            else {
                if (matchVal == null || matchVal.toString().trim().isEmpty()) {
                    trim++;
                }
                else {
                    diff++;
                }
            }
        }
        return getCount(matchJson, match, diff, trim);
    }

    /**
     * 返回结果
     * @param matchJson 比对数据
     * @param matchCount 比中数
     * @param diffCount 不同数
     * @param trimCount 空的数
     * @return Map { matchCount: 比中数, totalCount: 总个数, trimCount: 空的数, diffCount: 不同数 }
     */
    private static Map<String, Integer> getCount(Map<String, Object> matchJson, int matchCount, int diffCount,
            int trimCount) {
        Map<String, Integer> result = new HashMap<>(4);
        result.put("totalCount", matchJson.keySet().size());
        result.put("matchCount", matchCount);
        result.put("diffCount", diffCount);
        result.put("trimCount", trimCount);
        return result;
    }

}
