package com.oner365.elasticsearch.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.oner365.elasticsearch.enums.GeneTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SampleGene
 *
 * @author zhaoyong
 */
@Schema(description = "基因信息")
public class SampleGeneDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 基因类型 (格式: 1:X 2:Y)
     */
    @Schema(description = "基因类型")
    private GeneTypeEnum geneType;

    /**
     * 人员编号
     */
    @Schema(description = "人员编号")
    private String personCode;

    /**
     * 实验室编号
     */
    @Schema(description = "实验室编号")
    private String initServerNo;

    /**
     * 基因型信息 (格式: {key:value} )
     */
    @Schema(description = "基因型信息")
    private Map<String, Object> geneInfo;

    /**
     * 比对的基因型信息 (过滤空值)
     */
    @Schema(description = "比对基因型")
    private Map<String, Object> matchJson;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 页面使用的基因型 {key:value} 转换成 {"name":key, "value":value} 格式
     */
    @Schema(description = "基因型集合")
    private List<Map<String, Object>> geneList;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the geneType
     */
    public GeneTypeEnum getGeneType() {
        return geneType;
    }

    /**
     * @param geneType the geneType to set
     */
    public void setGeneType(GeneTypeEnum geneType) {
        this.geneType = geneType;
    }

    /**
     * @return the personCode
     */
    public String getPersonCode() {
        return personCode;
    }

    /**
     * @param personCode the personCode to set
     */
    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    /**
     * @return the initServerNo
     */
    public String getInitServerNo() {
        return initServerNo;
    }

    /**
     * @param initServerNo the initServerNo to set
     */
    public void setInitServerNo(String initServerNo) {
        this.initServerNo = initServerNo;
    }

    /**
     * @return the geneInfo
     */
    public Map<String, Object> getGeneInfo() {
        return geneInfo;
    }

    /**
     * @param geneInfo the geneInfo to set
     */
    public void setGeneInfo(Map<String, Object> geneInfo) {
        this.geneInfo = geneInfo;
    }

    /**
     * @return the matchJson
     */
    public Map<String, Object> getMatchJson() {
        return matchJson;
    }

    /**
     * @param matchJson the matchJson to set
     */
    public void setMatchJson(Map<String, Object> matchJson) {
        this.matchJson = matchJson;
    }

    /**
     * @return the geneList
     */
    public List<Map<String, Object>> getGeneList() {
        return geneList;
    }

    /**
     * @param geneList the geneList to set
     */
    public void setGeneList(List<Map<String, Object>> geneList) {
        this.geneList = geneList;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}
