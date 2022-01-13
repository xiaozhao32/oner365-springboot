package com.oner365.files.storage;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.oner365.common.enums.StorageEnum;

/**
 * 文件存储接口
 *
 * @author zhaoyong
 */
public interface IFileStorageClient {

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param directory 本地上传目录
     * @return 文件访问地址
     */
    String uploadFile(MultipartFile file, String directory);

    /**
     * 上传文件
     *
     * @param file 文件对象
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
     */
    void deleteFile(String fileUrl);

    /**
     * 获取文件存储方式
     *
     * @return StorageEnum
     */
    StorageEnum getName();
    

    /**
     * 下载文件
     *
     * @param fileUrl 文件url
     * @param offSet 其实位置
     * @param fileSize 每次获取大小
     * @return byte[]
     */
    byte[] download(String fileUrl,long offSet,long fileSize);
    
    /**
     * 获取文件
     * @param groupName 组名称
     * @param path 地址
     * @return FileInfo
     */
    FileInfo getFile(String groupName, String path) ;

}
