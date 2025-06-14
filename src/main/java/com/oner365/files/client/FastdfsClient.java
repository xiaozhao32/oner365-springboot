package com.oner365.files.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StorageEnum;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.deploy.utils.DeployUtils;
import com.oner365.files.config.properties.FileFdfsProperties;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.storage.condition.FdfsStorageCondition;
import com.oner365.files.vo.SysFileStorageVo;

import ch.ethz.ssh2.SFTPv3DirectoryEntry;

/**
 * fastdfs工具类
 *
 * @author zhaoyong
 */
@Component
@Conditional(FdfsStorageCondition.class)
public class FastdfsClient implements IFileStorageClient {

    private final Logger logger = LoggerFactory.getLogger(FastdfsClient.class);

    @Resource
    private FileFdfsProperties fileFdfsProperties;

    @Resource
    private FastFileStorageClient fastFileStorageClient;

    @Resource
    private FdfsWebServer fdfsWebServer;

    @Resource
    private IFileStorageService fileStorageService;

    @Override
    public String uploadFile(MultipartFile file, String directory) {
        try (InputStream in = file.getInputStream()) {
            StorePath storePath = fastFileStorageClient.uploadFile(in, file.getSize(),
                    getExtName(file.getOriginalFilename(), file.getContentType()), null);
            String url = getResAccessUrl(storePath);
            SysFileStorageVo vo = saveFileStorage(url, file.getOriginalFilename(), file.getSize());
            return vo.getId();
        }
        catch (IOException e) {
            logger.error("upload MultipartFile IOException:", e);
        }
        return null;
    }

    @Override
    public String uploadFile(File file, String directory) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(),
                    getExtName(file.getName(), null), null);
            String url = getResAccessUrl(storePath);
            SysFileStorageVo vo = saveFileStorage(url, file.getName(), file.length());
            return vo.getId();
        }
        catch (IOException e) {
            logger.error("upload File IOException:", e);
        }
        return null;
    }

    private SysFileStorageVo saveFileStorage(String url, String fileName, long fileSize) {
        // save
        SysFileStorageVo entity = new SysFileStorageVo();
        entity.setFastdfsUrl(PublicConstants.FILE_HTTP + fileFdfsProperties.getIp());
        entity.setId(StringUtils.replace(url, entity.getFastdfsUrl() + PublicConstants.DELIMITER, ""));
        entity.setCreateTime(LocalDateTime.now());
        entity.setDirectory(false);
        entity.setFileStorage(getName());
        entity.setFilePath(url);
        entity.setFileName(StringUtils.substringAfterLast(url, PublicConstants.DELIMITER));
        entity.setDisplayName(fileName);
        entity.setFileSuffix(DataUtils.getExtension(fileName));
        entity.setSize(DataUtils.convertFileSize(fileSize));
        fileStorageService.save(entity);
        return entity;
    }

    /**
     * 获取扩展名
     * @param fileName 文件名称
     * @param contentType contentType
     * @return String
     */
    private String getExtName(String fileName, String contentType) {
        String result = FilenameUtils.getExtension(fileName);
        if (DataUtils.isEmpty(result)) {
            return StringUtils.substringAfterLast(contentType, PublicConstants.DELIMITER);
        }
        return result;
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension 扩展名
     * @return String
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.defaultCharset());
        try (ByteArrayInputStream stream = new ByteArrayInputStream(buff)) {
            StorePath storePath = fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
            return getResAccessUrl(storePath);
        }
        catch (IOException e) {
            logger.error("upload File IOException:", e);
        }
        return null;
    }

    /**
     * 封装完整URL地址
     * @param storePath 文件地址
     * @return String
     */
    private String getResAccessUrl(StorePath storePath) {
        return fdfsWebServer.getWebServerUrl() + PublicConstants.DELIMITER + storePath.getFullPath();
    }

    @Override
    public byte[] download(String path) {
        String group = path.substring(0, path.indexOf(PublicConstants.DELIMITER));
        String downloadPath = path.substring(path.indexOf(PublicConstants.DELIMITER) + 1);
        return fastFileStorageClient.downloadFile(group, downloadPath, new DownloadByteArray());
    }

    @Override
    public String downloadPath(String path) {
        // 负载下载地址路径
        return null;
    }

    @Override
    public Long getFileSize(String path) {
        // 文件大小
        return 0L;
    }

    @Override
    public Boolean deleteFile(String fileUrl) {
        if (DataUtils.isEmpty(fileUrl)) {
            return Boolean.FALSE;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            return fileStorageService.deleteById(fileUrl);
        }
        catch (FdfsUnsupportStorePathException e) {
            logger.error("delete File FdfsUnSupportStorePathException:", e);
        }
        return Boolean.FALSE;
    }

    @Override
    public SysFileStorageDto getFile(String id) {
        return fileStorageService.getById(id);
    }

    @Override
    public StorageEnum getName() {
        return StorageEnum.FDFS;
    }

    public List<SysFileStorageDto> findList(String fileDirectory) {
        final String directory;
        if (!DataUtils.isEmpty(fileDirectory)) {
            directory = fileFdfsProperties.getPath() + "/M00/00/" + fileDirectory;
        }
        else {
            directory = fileFdfsProperties.getPath();
        }

        List<SFTPv3DirectoryEntry> vector = DeployUtils.directoryList(fileFdfsProperties.getIp(),
                fileFdfsProperties.getPort(), fileFdfsProperties.getUser(), fileFdfsProperties.getPassword(),
                directory);
        return vector.stream().map(entry -> {
            SysFileStorageDto fastdfsFile = new SysFileStorageDto();
            fastdfsFile.setId(StringUtils.replace(directory, fileFdfsProperties.getPath(), "group1")
                    + PublicConstants.DELIMITER + entry.filename);
            fastdfsFile.setCreateTime(DateUtil.dateToLocalDateTime(new Date(entry.attributes.mtime * 1000L)));
            fastdfsFile.setFileName(entry.filename);
            fastdfsFile.setDirectory(entry.attributes.isDirectory());
            fastdfsFile.setFileSuffix(DataUtils.getExtension(entry.filename));
            fastdfsFile.setFilePath(directory);
            fastdfsFile.setFastdfsUrl(PublicConstants.FILE_HTTP + fileFdfsProperties.getIp());
            fastdfsFile.setSize(DataUtils.convertFileSize(entry.attributes.size));
            return fastdfsFile;
        }).collect(Collectors.toList());
    }

}
