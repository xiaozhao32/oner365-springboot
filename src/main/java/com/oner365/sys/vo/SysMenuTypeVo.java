package com.oner365.sys.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 菜单类型对象
 * @author zhaoyong
 */
@Schema(name = "菜单类型")
public class SysMenuTypeVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(name = "主键")
    private String id;

    /**
     * 类型名称
     */
    @Schema(name = "类型名称", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.menuType.typeName.message}")
    private String typeName;

    /**
     * 类型编码
     */
    @Schema(name = "类型编码", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.menuType.typeCode.message}")
    private String typeCode;

    /**
     * 状态
     */
    @Schema(name = "状态", requiredMode = RequiredMode.REQUIRED)
    @NotNull(message = "{system.vo.menuType.status.message}")
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
    public SysMenuTypeVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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
