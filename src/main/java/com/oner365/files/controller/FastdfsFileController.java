package com.oner365.files.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseResult;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StorageEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.deploy.utils.DeployUtils;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

import ch.ethz.ssh2.SFTPv3DirectoryEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 文件上传
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "Fastdfs 文件上传")
@RequestMapping("/files/fdfs")
public class FastdfsFileController extends BaseController {

  @Value("${fdfs.storage.path}")
  private String path;
  @Value("${fdfs.ip}")
  private String ip;
  @Value("${fdfs.port}")
  private int port;
  @Value("${fdfs.user}")
  private String user;
  @Value("${fdfs.password}")
  private String password;

  @Autowired
  private IFileStorageClient fileStorageClient;

  @Autowired
  private IFileStorageService fileStorageService;

  /**
   * 查询列表
   *
   * @param data 查询参数
   * @return Page<SysFileStorageDto>
   */
  @ApiOperation("1.查询列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public Page<SysFileStorageDto> list(@RequestBody QueryCriteriaBean data) {
    return fileStorageService.pageList(data);
  }

  /**
   * 获取目录
   * 
   * @param fileDirectory 目录
   * @return List<FastdfsFile>
   */
  @ApiOperation("2.获取目录")
  @ApiOperationSupport(order = 2)
  @GetMapping("/directory")
  public List<SysFileStorageDto> directory(@RequestParam("fileDirectory") String fileDirectory) {
    String directory = path;
    if (!DataUtils.isEmpty(fileDirectory)) {
      directory = path + "/M00/00/" + fileDirectory;
    }
    List<SFTPv3DirectoryEntry> vector = DeployUtils.directoryList(ip, port, user, password, directory);
    List<SysFileStorageDto> result = new ArrayList<>();
    for (SFTPv3DirectoryEntry entry : vector) {
      SysFileStorageDto fastdfsFile = new SysFileStorageDto();
      fastdfsFile.setId(StringUtils.replace(directory, path, "group1") + PublicConstants.DELIMITER + entry.filename);
      fastdfsFile.setCreateTime(new Date(entry.attributes.mtime * 1000L));
      fastdfsFile.setFileName(entry.filename);
      fastdfsFile.setDirectory(entry.attributes.isDirectory());
      fastdfsFile.setFileSuffix(DataUtils.getExtension(entry.filename));
      fastdfsFile.setFilePath(directory);
      fastdfsFile.setFastdfsUrl("http://" + ip);
      fastdfsFile.setSize(DataUtils.convertFileSize(entry.attributes.size));
      result.add(fastdfsFile);
    }
    return result;
  }

  /**
   * 文件上传
   * 
   * @param file    MultipartFile
   * @param dictory 目录
   * @return Map<String, Object>
   */
  @ApiOperation("3.文件上传 - MultipartFile")
  @ApiOperationSupport(order = 3)
  @PostMapping("/uploadMultipartFile")
  public ResponseResult<String> uploadMultipartFile(@RequestBody MultipartFile file,
      @ApiParam(name = "dictory", value = "上传目录") @RequestParam(name = "dictory", required = false) String dictory) {
    String targetDictory = null;
    if (DataUtils.isEmpty(dictory)) {
      targetDictory = DateUtil.getCurrentDate();
    }
    String url = fileStorageClient.uploadFile(file, targetDictory);
    return ResponseResult.success(url);
  }

  /**
   * 文件上传 File 类型
   * 
   * @param file    File
   * @param dictory 目录
   * @return Map<String, Object>
   */
  @ApiOperation("4.文件上传 - 参数")
  @ApiOperationSupport(order = 4)
  @PostMapping("/uploadFile")
  public ResponseResult<String> uploadFile(
      @ApiParam(name = "file", value = "文件") @RequestPart("file") MultipartFile file,
      @ApiParam(name = "dictory", value = "上传目录") @RequestParam(name = "dictory", required = false) String dictory) {
    String targetDictory = null;
    if (DataUtils.isEmpty(dictory)) {
      targetDictory = DateUtil.getCurrentDate();
    }
    String url = fileStorageClient.uploadFile(file, targetDictory);
    return ResponseResult.success(url);
  }

  /**
   * 文件下载
   * 
   * @param fileUrl  url 开头从组名开始
   * @param filename 文件名称
   * @param response HttpServletResponse
   */
  @ApiOperation("5.文件下载 - 写出")
  @ApiOperationSupport(order = 5)
  @GetMapping("/download")
  public void download(@RequestParam("fileUrl") String fileUrl, String filename, HttpServletResponse response) {
    byte[] data = fileStorageClient.download(fileUrl);
    if (DataUtils.isEmpty(filename)) {
      filename = StringUtils.substringAfterLast(fileUrl, PublicConstants.DELIMITER);
    }

    try {
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
          "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("download UnsupportedEncodingException:", e);
    }

    // 写出
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      IOUtils.write(data, outputStream);
    } catch (IOException e) {
      LOGGER.error("download IOException:", e);
    }

  }

  /**
   * 文件流下载
   * 
   * @param fileUrl url 开头从组名开始
   * @return byte[]
   */
  @ApiOperation("6.文件下载 - byte[]流方式")
  @ApiOperationSupport(order = 6)
  @GetMapping("/downloadFile")
  public byte[] downloadFile(@RequestParam("fileUrl") String fileUrl) {
    return fileStorageClient.download(fileUrl);
  }

  /**
   * 删除文件
   * 
   * @param ids 文件id
   * @return String
   */
  @ApiOperation("7.删除文件")
  @ApiOperationSupport(order = 7)
  @DeleteMapping("/delete")
  public String delete(@RequestBody String... ids) {
    if (ids != null) {
      for (String id : ids) {
        fileStorageClient.deleteFile(id);
      }
    }
    return ResultEnum.SUCCESS.getName();
  }

  /**
   * 获取文件存储方式
   * 
   * @return String
   */
  @ApiOperation("8.获取文件存储方式")
  @ApiOperationSupport(order = 8)
  @GetMapping("/getStorageName")
  public String getStorageName() {
    StorageEnum result = fileStorageClient.getName();
    return result.getCode();
  }

}
