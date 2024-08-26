package com.oner365.sys.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;

/**
 * 操作对象
 * @author zhaoyong
 */
@Schema(name = "菜单操作")
public class SysMenuOperationVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @Schema(name = "主键")
    private String id;

    /**
     * 操作名称
     */
    @Schema(name = "操作名称", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.menuOperation.operationName.message}")
    private String operationName;

    /**
     * 操作类型
     */
    @Schema(name = "操作类型")
    private String operationType;

    /**
     * 状态 status
     */
    @Schema(name = "状态")
    private StatusEnum status;

    /**
     * 创建时间 create_time
     */
    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间 update_time
     */
    @Schema(name = "更新时间")
    private LocalDateTime updateTime;

    /**
     * Constructor
     */
    public SysMenuOperationVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}
