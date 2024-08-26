package com.oner365.sys.vo.check;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;

/**
 * 检测字典编码
 * 
 * @author zhaoyong
 *
 */
@Schema(name = "检测字典编码")
public class CheckCodeVo implements Serializable {

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
     * 编码
     */
    @Schema(name = "编码", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "{system.vo.check.code.message}")
    private String code;
    
    /**
     * 构造方法
     */
    public CheckCodeVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
