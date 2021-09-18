package com.oner365.files.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.google.common.collect.Lists;
import com.oner365.common.ResponseResult;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.deploy.utils.DeployUtils;
import com.oner365.files.client.FastdfsClient;
import com.oner365.files.entity.FastdfsFile;
import com.oner365.files.service.IFastdfsFileService;
import com.oner365.util.DataUtils;

import ch.ethz.ssh2.SFTPv3DirectoryEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 文件上传
 * @author zhaoyong
 */
@RestController
@RequestMapping("/files/fdfs")
@Api(tags = "Fastdfs 文件上传")
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
    private FastdfsClient fastdfsClient;
    
    @Autowired
    private IFastdfsFileService fastdfsFileService;
    
    /**
     * 查询列表
     *
     * @param data 查询参数
     * @return Page<FastdfsFile>
     */
    @PostMapping("/list")
    @ApiOperation("查询列表")
    public Page<FastdfsFile> list(@RequestBody QueryCriteriaBean data) {
        return fastdfsFileService.pageList(data);
    }
    
    /**
     * 获取目录
     * 
     * @param fileDirectory 目录
     * @return List<FastdfsFile>
     */
    @GetMapping("/directory")
    @ApiOperation("获取目录")
    public List<FastdfsFile> directory(@RequestParam("fileDirectory") String fileDirectory) {
        String directory = path;
        if (!DataUtils.isEmpty(fileDirectory)) {
            directory = path + "/M00/00/" + fileDirectory;
        }
        List<SFTPv3DirectoryEntry> vector = DeployUtils.directoryList(ip, port, user, password, directory);
        List<FastdfsFile> result = Lists.newArrayList();
        for (SFTPv3DirectoryEntry entry : vector) {
            FastdfsFile fastdfsFile = new FastdfsFile();
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
     * @param file MultipartFile
     * @return Map<String, Object>
     */
    @PostMapping("/uploadMultipartFile")
    @ApiOperation("文件上传 - MultipartFile")
    public ResponseResult<String> uploadMultipartFile(@RequestBody MultipartFile file) {
        String url = fastdfsClient.uploadFile(file);
        return ResponseResult.success(url);
    }

    /**
     * 文件上传 File 类型
     * 
     * @param file File
     * @return Map<String, Object>
     */
    @PostMapping("/uploadFile")
    @ApiOperation("文件上传 - File")
    public ResponseResult<String> uploadFile(@RequestBody File file) {
        String url = fastdfsClient.uploadFile(file);
        return ResponseResult.success(url);
    }

    /**
     * 文件下载
     * 
     * @param fileUrl  url 开头从组名开始
     * @param filename 文件名称
     * @param response HttpServletResponse
     */
    @GetMapping("/download")
    @ApiOperation("文件下载")
    public void download(@RequestParam("fileUrl") String fileUrl, String filename, 
            HttpServletResponse response) {
        byte[] data = fastdfsClient.download(fileUrl);
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
    @GetMapping("/downloadFile")
    @ApiOperation("文件下载")
    public byte[] downloadFile(@RequestParam("fileUrl") String fileUrl) {
        return fastdfsClient.download(fileUrl);
    }

    /**
     * 删除文件
     * 
     * @param ids 文件id
     * @return String
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除文件")
    public String delete(@RequestBody String... ids) {
        if (ids != null) {
            for (String id : ids) {
                fastdfsClient.deleteFile(id);
            }
        }
        return PublicConstants.SUCCESS;
    }

    /**
     * 获取文件
     * 
     * @param groupName 组名称
     * @param path      地址
     * @return FileInfo
     */
    @GetMapping("/getFile")
    @ApiOperation("获取文件信息")
    public FileInfo getFile(@RequestParam("groupName") String groupName, @RequestParam("path") String path) {
        return fastdfsClient.getFile(groupName, path);
    }

}
