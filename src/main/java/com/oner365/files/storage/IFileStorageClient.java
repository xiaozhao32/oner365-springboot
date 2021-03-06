package com.oner365.files.storage;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.enums.StorageEnum;
import com.oner365.files.dto.SysFileStorageDto;

/**
 * 文件存储接口
 *
 * @author zhaoyong
 */
public interface IFileStorageClient {

  /**
   * 上传文件
   *
   * @param file      文件对象
   * @param directory 本地上传目录
   * @return 文件访问地址
   */
  String uploadFile(MultipartFile file, String directory);

  /**
   * 上传文件
   *
   * @param file      文件对象
   * @param directory 本地上传目录
   * @return 文件访问地址
   */
  String uploadFile(File file, String directory);

  /**
   * 下载文件
   *
   * @param fileUrl 文件url
   * @return byte[]
   */
  byte[] download(String fileUrl);

  /**
   * 删除文件
   *
   * @param fileUrl 文件访问地址
   * @return Boolean
   */
  Boolean deleteFile(String fileUrl);

  /**
   * 获取文件存储方式
   *
   * @return StorageEnum
   */
  StorageEnum getName();

  /**
   * 下载文件
   *
   * @param fileUrl  文件url
   * @param offSet   其实位置
   * @param fileSize 每次获取大小
   * @return byte[]
   */
  byte[] download(String fileUrl, long offSet, long fileSize);

  /**
   * 获取文件
   * 
   * @param id 主键
   * @return SysFileStorageDto
   */
  SysFileStorageDto getFile(String id);
  
}
