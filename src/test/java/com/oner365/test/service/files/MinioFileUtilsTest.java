package com.oner365.test.service.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oner365.files.config.properties.MinioProperties;
import com.oner365.test.service.BaseServiceTest;
import com.oner365.util.DataUtils;

import io.minio.BucketExistsArgs;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

/**
 * Test MinioFile Service
 *
 * @author zhaoyong
 *
 */
//@SpringBootTest
@Disabled
class MinioFileUtilsTest extends BaseServiceTest {
    
  @Autowired
  private MinioProperties minioProperties;

  @Test
  void test() {
    Assertions.assertEquals("MinioFileUtilsTest", MinioFileUtilsTest.class.getSimpleName());
  }

  @Test
  @Disabled
  void testMinio() throws Exception {
    MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getUrl())
            .credentials(minioProperties.getUsername(), minioProperties.getPassword()).build();
    Assertions.assertNotNull(minioClient);
    // 创建文件夹
    BucketExistsArgs bucket = BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build();
    if (minioClient.bucketExists(bucket)) {
      logger.info("bucket exists:{}", minioProperties.getBucket());
    } else {
      minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
    }

    // 文件名称
    String fileName = "12345.jpg";
    // 上传路径
    String path = "images/"+fileName;
    // 本地文件
    String localFileName = "d:/1.jpg";
    // 上传文件
    File file = DataUtils.getFile(localFileName);
    try (InputStream inputStream = new FileInputStream(Objects.requireNonNull(file))) {
      ObjectWriteResponse writeResponse = minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(path)
          .stream(inputStream, file.length(), -1).contentType(ContentType.IMAGE_JPEG.getMimeType()).build());
      logger.info("file path: {}", writeResponse.object());
    } catch (IOException e) {
      logger.error("upload file error:", e);
    }

    // 获取文件
    GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).build());
    logger.info("file get: {}", objectResponse);
    objectResponse.close();

    // 下载文件 重新命名
    String downloadPath = "d:/11111.jpg";
    minioClient.downloadObject(DownloadObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).filename(downloadPath).build());

    // 删除文件
    minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).build());
  }
}
