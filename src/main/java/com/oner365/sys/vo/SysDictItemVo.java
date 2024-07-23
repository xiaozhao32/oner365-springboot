package com.oner365.sys.vo;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 字典 SysDictItem
 * 
 * @author zhaoyong
 */
@Schema(name = "字典")
public class SysDictItemVo implements Serializable {

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
     * 字典类型编码 type_id
     */
    @Schema(name = "字典类型编码", required = true)
    @NotBlank(message = "字典类型编码不能为空")
    private String typeId;

    /**
     * 字典编码 item_code
     */
    @Schema(name = "字典编码", required = true)
    @NotBlank(message = "字典编码不能为空")
    private String itemCode;

    /**
     * 字典名称 item_name
     */
    @Schema(name = "字典名称", required = true)
    @NotBlank(message = "字典名称不能为空")
    private String itemName;

    /**
     * 排序 item_order
     */
    @Schema(name = "排序")
    private Integer itemOrder;

    /**
     * 状态 status
     */
    @Schema(name = "状态")
    private StatusEnum status;

    /**
     * 上级id parent_id
     */
    @Schema(name = "上级id")
    private String parentId;

    /**
     * Constructor
     */
    public SysDictItemVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}
