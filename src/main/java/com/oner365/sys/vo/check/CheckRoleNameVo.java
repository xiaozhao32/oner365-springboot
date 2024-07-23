package com.oner365.sys.vo.check;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 检测角色编码
 * 
 * @author zhaoyong
 *
 */
@Schema(name = "检测角色编码")
public class CheckRoleNameVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键 id
     */
    @Schema(name = "主键")
    private String id;
    
    /**
     * 角色名称
     */
    @Schema(name = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    
    /**
     * 构造方法
     */
    public CheckRoleNameVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
