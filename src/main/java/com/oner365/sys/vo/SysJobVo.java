package com.oner365.sys.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.google.common.base.MoreObjects;
import com.oner365.sys.entity.SysJob;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 职位信息 SysJob
 * 
 * @author zhaoyong
 */
@ApiModel(value = "职位信息")
public class SysJobVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 职位名称 job_name
     */
    @ApiModelProperty(value = "职位名称", required = true)
    private String jobName;

    /**
     * 职位名称 parent_id
     */
    @ApiModelProperty(value = "职位上级id", required = true)
    private String parentId;

    /**
     * 职位图标 job_logo
     */
    @ApiModelProperty(value = "图标名称")
    private String jobLogo;

    /**
     * 职位图标 job_logo_url
     */
    @ApiModelProperty(value = "图标地址")
    private String jobLogoUrl;

    /**
     * 职位描述 job_info
     */
    @ApiModelProperty(value = "职位描述")
    private String jobInfo;

    /**
     * 职位排序 job_order
     */
    @ApiModelProperty(value = "职位排序")
    private Integer jobOrder;

    /**
     * 状态 status
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 创建时间 create_time
     */
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    /**
     * 更新时间 update_time
     */
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    /**
     * Generate constructor
     */
    public SysJobVo() {
        super();
    }

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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getJobLogo() {
        return jobLogo;
    }

    public void setJobLogo(String jobLogo) {
        this.jobLogo = jobLogo;
    }

    public String getJobLogoUrl() {
        return jobLogoUrl;
    }

    public void setJobLogoUrl(String jobLogoUrl) {
        this.jobLogoUrl = jobLogoUrl;
    }

    public String getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    public Integer getJobOrder() {
        return jobOrder;
    }

    public void setJobOrder(Integer jobOrder) {
        this.jobOrder = jobOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

    /**
     * 转换对象
     * 
     * @return SysJob
     */
    public SysJob toObject() {
        SysJob result = new SysJob();
        result.setCreateTime(this.getCreateTime());
        result.setId(this.getId());
        result.setJobInfo(this.getJobInfo());
        result.setJobLogo(this.getJobLogo());
        result.setJobLogoUrl(this.getJobLogoUrl());
        result.setJobName(this.getJobName());
        result.setJobOrder(this.getJobOrder());
        result.setParentId(this.getParentId());
        result.setStatus(this.getStatus());
        result.setUpdateTime(this.getUpdateTime());
        return result;
    }
}