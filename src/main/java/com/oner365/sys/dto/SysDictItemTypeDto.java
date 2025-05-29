package com.oner365.sys.dto;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

/**
 * 字典类型 SysDictItemType
 *
 * @author zhaoyong
 */
@Schema(name = "字典类型")
public class SysDictItemTypeDto implements Serializable {

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
     * 类型名称 type_name
     */
    @Schema(name = "类型名称", requiredMode = RequiredMode.REQUIRED)
    private String typeName;

    /**
     * 类型编码 type_code
     */
    @Schema(name = "类型编码", requiredMode = RequiredMode.REQUIRED)
    private String typeCode;

    /**
     * 类型描述 type_des
     */
    @Schema(name = "类型描述")
    private String typeDes;

    /**
     * 排序 type_order
     */
    @Schema(name = "排序")
    private Integer typeOrder;

    /**
     * 状态 status
     */
    @Schema(name = "状态")
    private StatusEnum status;

    /**
     * Constructor
     */
    public SysDictItemTypeDto() {
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

    public String getTypeDes() {
        return typeDes;
    }

    public void setTypeDes(String typeDes) {
        this.typeDes = typeDes;
    }

    public Integer getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(Integer typeOrder) {
        this.typeOrder = typeOrder;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}
