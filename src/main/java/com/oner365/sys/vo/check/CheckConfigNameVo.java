package com.oner365.sys.vo.check;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 检测配置名称
 * 
 * @author zhaoyong
 *
 */
@Schema(name = "检测配置名称")
public class CheckConfigNameVo implements Serializable {

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
  @Schema(name = "配置名称", required = true)
  @NotBlank(message = "检测配置名称不能为空")
  private String configName;

  /**
   * 构造方法
   */
  public CheckConfigNameVo() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getConfigName() {
    return configName;
  }

  public void setConfigName(String configName) {
    this.configName = configName;
  }

}
