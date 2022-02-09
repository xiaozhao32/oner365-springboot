package com.oner365.files.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StorageEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.deploy.utils.DeployUtils;
import com.oner365.files.config.properties.FileFdfsProperties;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.vo.DownloadVo;
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
@Api(tags = "文件上传")
@RequestMapping("/files/storage")
public class FileStorageController extends BaseController {

  @Autowired
  private FileFdfsProperties fileFdfsProperties;

  @Autowired
  private IFileStorageClient fileStorageClient;

  @Autowired
  private IFileStorageService fileStorageService;

  /**
   * 查询列表
   *
   * @param data 查询参数
   * @return PageInfo<SysFileStorageDto>
   */
  @ApiOperation("1.查询列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public PageInfo<SysFileStorageDto> list(@RequestBody QueryCriteriaBean data) {
    return fileStorageService.pageList(data);
  }

  /**
   * 获取fdfs目录 - fdfs专用
   *
   * @param fileDirectory 目录
   * @return List<FastdfsFile>
   */
  @ApiOperation("2.获取目录 - fdfs专用")
  @ApiOperationSupport(order = 2)
  @GetMapping("/directory")
  public List<SysFileStorageDto> directory(@RequestParam("fileDirectory") String fileDirectory) {
    final String directory;
    if (!DataUtils.isEmpty(fileDirectory)) {
      directory = fileFdfsProperties.getPath() + "/M00/00/" + fileDirectory;
    } else {
      directory = fileFdfsProperties.getPath();
    }
    
    List<SFTPv3DirectoryEntry> vector = DeployUtils.directoryList(fileFdfsProperties.getIp(), fileFdfsProperties.getPort(),
        fileFdfsProperties.getUser(), fileFdfsProperties.getPassword(), directory);
    return vector.stream().map(entry -> {
      SysFileStorageDto fastdfsFile = new SysFileStorageDto();
      fastdfsFile.setId(StringUtils.replace(directory, fileFdfsProperties.getPath(), "group1") + PublicConstants.DELIMITER + entry.filename);
      fastdfsFile.setCreateTime(new Date(entry.attributes.mtime * 1000L));
      fastdfsFile.setFileName(entry.filename);
      fastdfsFile.setDirectory(entry.attributes.isDirectory());
      fastdfsFile.setFileSuffix(DataUtils.getExtension(entry.filename));
      fastdfsFile.setFilePath(directory);
      fastdfsFile.setFastdfsUrl("http://" + fileFdfsProperties.getIp());
      fastdfsFile.setSize(DataUtils.convertFileSize(entry.attributes.size));
      return fastdfsFile;
    }).collect(Collectors.toList());
  }

  /**
   * 文件上传 File 类型
   *
   * @param file    File
   * @param dictory 目录
   * @return ResponseResult<String>
   */
  @ApiOperation("3.文件上传")
  @ApiOperationSupport(order = 3)
  @PostMapping("/upload")
  public ResponseResult<String> uploadFile(
      @ApiParam(name = "file", value = "文件") @RequestPart("file") MultipartFile file,
      @ApiParam(name = "dictory", value = "上传目录") @RequestParam(name = "dictory", required = false) String dictory) {
    String targetDictory = dictory;
    if (DataUtils.isEmpty(targetDictory)) {
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
  @ApiOperation("4.文件下载 - 写出")
  @ApiOperationSupport(order = 4)
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
      logger.error("download UnsupportedEncodingException:", e);
    }

    // 写出
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      IOUtils.write(data, outputStream);
    } catch (IOException e) {
      logger.error("download IOException:", e);
    }

  }

  /**
   * 文件流下载
   *
   * @param fileUrl url 开头从组名开始
   * @return byte[]
   */
  @ApiOperation("5.文件下载 - byte[]流方式")
  @ApiOperationSupport(order = 5)
  @GetMapping("/byte/download")
  public byte[] downloadFile(@RequestParam("fileUrl") String fileUrl) {
    return fileStorageClient.download(fileUrl);
  }

  /**
   * 删除文件
   *
   * @param ids 文件id
   * @return String
   */
  @ApiOperation("6.删除文件")
  @ApiOperationSupport(order = 6)
  @DeleteMapping("/delete")
  public String delete(@RequestBody String... ids) {
    if (ids != null) {
      Arrays.stream(ids).forEach(id -> fileStorageClient.deleteFile(id));
    }
    return ResultEnum.SUCCESS.getName();
  }

  /**
   * 获取文件存储方式
   *
   * @return String
   */
  @ApiOperation("7.获取文件存储方式")
  @ApiOperationSupport(order = 7)
  @GetMapping("/name")
  public String getStorageName() {
    StorageEnum result = fileStorageClient.getName();
    return result.getCode();
  }

  /**
   * 文件流下载
   *
   * @param downloadVo 文件
   * @return byte[]
   */
  @ApiOperation("8.文件分段下载 - ResponseData方式")
  @PostMapping("/part/download")
  public ResponseData<byte[]> downloadFilePart(@RequestBody DownloadVo downloadVo) {
      return ResponseData.success(fileStorageClient.download(downloadVo.getFileUrl(),downloadVo.getOffset(),downloadVo.getFileSize()));
  }

  /**
   * 文件流下载
   *
   * @param downloadVo 文件
   * @return byte[]
   */
  @ApiOperation("9.文件分段下载 - byte[]流方式")
  @PostMapping("/part/byte/download")
  public byte[] downloadFilePartByte(@RequestBody DownloadVo downloadVo) {
      return fileStorageClient.download(downloadVo.getFileUrl(),downloadVo.getOffset(),downloadVo.getFileSize());
  }

  /**
   * 获取文件信息
   *
   * @param id 主键
   * @return SysFileStorageDto
   */
  @ApiOperation("10.获取文件信息")
  @PostMapping("/info/{id}")
  public ResponseData<SysFileStorageDto> getFileInfo(@PathVariable String id) {
    SysFileStorageDto entity = fileStorageClient.getFile(id);
    return ResponseData.success(entity);
  }

}
