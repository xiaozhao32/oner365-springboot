package com.oner365.files.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 下载信息
 *
 * @author liutao
 */
@ApiModel(value = "下载信息")
public class DownloadVo implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键")
  private String fileUrl;

  @ApiModelProperty(value = "开始")
  private long offset;

  @ApiModelProperty(value = "结束")
  private long fileSize;

  public DownloadVo() {
    super();
  }

  /**
   * @return the fileUrl
   */
  public String getFileUrl() {
    return fileUrl;
  }

  /**
   * @param fileUrl the fileUrl to set
   */
  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  /**
   * @return the offset
   */
  public long getOffset() {
    return offset;
  }

  /**
   * @param offset the offset to set
   */
  public void setOffset(long offset) {
    this.offset = offset;
  }

  /**
   * @return the fileSize
   */
  public long getFileSize() {
    return fileSize;
  }

  /**
   * @param fileSize the fileSize to set
   */
  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

}
