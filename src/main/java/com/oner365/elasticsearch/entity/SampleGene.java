package com.oner365.elasticsearch.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.oner365.data.commons.util.DateUtil;
import com.oner365.elasticsearch.enums.GeneTypeEnum;

import jakarta.persistence.Enumerated;

/**
 * SampleGene
 *
 * @author zhaoyong
 */
@Document(indexName = "samplegene")
@Setting(shards = 5, refreshInterval = "-1")
public class SampleGene implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 基因类型 (格式: 1:X 2:Y)
     */
    @Enumerated
    @Field(type = FieldType.Keyword)
    private GeneTypeEnum geneType;

    /**
     * 人员编号
     */
    @Field(type = FieldType.Keyword)
    private String personCode;

    /**
     * 实验室编号
     */
    @Field(type = FieldType.Keyword)
    private String initServerNo;

    /**
     * 基因型信息 (格式: {key:value} )
     */
    @Field(type = FieldType.Object)
    private Map<String, Object> geneInfo;

    /**
     * 比对的基因型信息 (过滤空值)
     */
    @Field(type = FieldType.Object)
    private Map<String, Object> matchJson;

    /**
     * 创建时间
     */
    @Field(name = "create_time", type = FieldType.Date, pattern = DateUtil.FULL_TIME_FORMAT)
    private Date createTime;

    /**
     * 页面使用的基因型 {key:value} 转换成 {"name":key, "value":value} 格式
     */
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

}
