package com.oner365.files.client;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.enums.StorageEnum;
import com.oner365.files.entity.FastdfsFile;
import com.oner365.files.service.IFastdfsFileService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.storage.condition.LocalStorageCondition;
import com.oner365.files.util.FileUploadUtils;
import com.oner365.util.DataUtils;

/**
 * 本地上传工具类
 * 
 * @author zhaoyong
 */
@Component
@Conditional(LocalStorageCondition.class)
public class LocalClient implements IFileStorageClient {

    private final Logger logger = LoggerFactory.getLogger(FastdfsClient.class);

    @Value("${file.local.web:''}")
    private String fileWeb;

    @Value("${file.local.upload:''}")
    private String filePath;

    @Autowired
    private IFastdfsFileService fastdfsFileService;

    @Override
    public String uploadFile(MultipartFile file, String dictory) {
        try {
            FastdfsFile fastdfsFile = FileUploadUtils.upload(file, getName(), fileWeb, filePath, dictory, file.getSize() + 1);
            fastdfsFileService.save(fastdfsFile);
            return fastdfsFile.getFilePath();
        } catch (Exception e) {
            logger.error("upload MultipartFile IOException:", e);
        }
        return null;
    }

    @Override
    public String uploadFile(File file, String dictory) {
        try {
            MultipartFile multipartFile = DataUtils.convertMultipartFile(file);
            FastdfsFile fastdfsFile = FileUploadUtils.upload(multipartFile, getName(), fileWeb, filePath, dictory,
                    file.length() + 1);
            fastdfsFileService.save(fastdfsFile);
            return fastdfsFile.getFilePath();
        } catch (Exception e) {
            logger.error("upload MultipartFile IOException:", e);
        }
        return null;
    }

    @Override
    public byte[] download(String fileUrl) {
        return FileUploadUtils.download(filePath, fileUrl);
    }

    @Override
    public void deleteFile(String id) {
        fastdfsFileService.deleteById(id);
    }

    @Override
    public StorageEnum getName() {
        return StorageEnum.LOCAL;
    }

}
