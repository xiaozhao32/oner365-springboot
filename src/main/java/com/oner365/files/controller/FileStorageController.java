package com.oner365.files.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StorageEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 文件上传
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "文件上传")
@RequestMapping("/files/storage")
public class FileStorageController extends BaseController {

  @Resource
  private IFileStorageClient fileStorageClient;

  @Resource
  private IFileStorageService fileStorageService;

  /**
   * 查询列表
   *
   * @param data 查询参数
   * @return PageInfo<SysFileStorageDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysFileStorageDto> pageList(@RequestBody QueryCriteriaBean data) {
    return fileStorageService.pageList(data);
  }

  /**
   * 文件上传 File 类型
   *
   * @param file    File
   * @param dictory 目录
   * @return ResponseResult<String>
   */
  @ApiOperation("2.文件上传")
  @ApiOperationSupport(order = 2)
  @PostMapping("/upload")
  public ResponseResult<String> uploadFile(
      @ApiParam(name = "file", value = "文件") @RequestPart("file") MultipartFile file,
      @ApiParam(name = "dictory", value = "上传目录") @RequestParam(name = "dictory", required = false) String dictory) {
    String targetDirectory = dictory;
    if (DataUtils.isEmpty(targetDirectory)) {
      targetDirectory = DateUtil.getCurrentDate();
    }
    String url = fileStorageClient.uploadFile(file, targetDirectory);
    return ResponseResult.success(url);
  }

  /**
   * 文件下载
   *
   * @param fileUrl  url 开头从组名开始
   * @param filename 文件名称
   * @param response HttpServletResponse
   */
  @ApiOperation("3.文件下载 - 写出")
  @ApiOperationSupport(order = 3)
  @GetMapping("/download")
  public void download(@RequestParam("fileUrl") String fileUrl, String filename, HttpServletResponse response) {
    byte[] data = fileStorageClient.download(fileUrl);
    if (data == null) {
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return;
    }
    if (DataUtils.isEmpty(filename)) {
      filename = StringUtils.substringAfterLast(fileUrl, PublicConstants.DELIMITER);
    }

    // 写出
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      response.setCharacterEncoding(Charset.defaultCharset().name());
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
          "attachment;filename=" + URLEncoder.encode(filename, Charset.defaultCharset().name()));
      IOUtils.write(data, outputStream);
    } catch (IOException e) {
      logger.error("download IOException:", e);
    }

  }

  /**
   * 文件下载地址
   *
   * @param path url 开头从组名开始
   * @return 文件下载地址
   */
  @ApiOperation("4.文件下载 - 提供下载地址")
  @ApiOperationSupport(order = 4)
  @GetMapping("/path")
  public String downloadPath(@RequestParam("path") String path) {
    return fileStorageClient.downloadPath(path);
  }

  /**
   * 删除文件
   *
   * @param ids 文件id
   * @return String
   */
  @ApiOperation("5.删除文件")
  @ApiOperationSupport(order = 5)
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> fileStorageClient.deleteFile(id)).collect(Collectors.toList());
  }

  /**
   * 获取文件存储方式
   *
   * @return StorageEnum
   */
  @ApiOperation("6.获取文件存储方式")
  @ApiOperationSupport(order = 6)
  @GetMapping("/name")
  public StorageEnum getStorageName() {
    return fileStorageClient.getName();
  }

  /**
   * 获取文件信息
   *
   * @param id 主键
   * @return SysFileStorageDto
   */
  @ApiOperation("7.获取文件信息")
  @ApiOperationSupport(order = 7)
  @PostMapping("/info/{id}")
  public ResponseData<SysFileStorageDto> getFileInfo(@PathVariable String id) {
    SysFileStorageDto entity = fileStorageClient.getFile(id);
    return ResponseData.success(entity);
  }

}
