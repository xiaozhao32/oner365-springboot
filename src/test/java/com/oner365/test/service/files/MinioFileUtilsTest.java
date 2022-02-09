package com.oner365.test.service.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
class MinioFileUtilsTest extends BaseServiceTest {
  
  @Test
  void test() {
    Assertions.assertEquals("MinioFileUtilsTest", MinioFileUtilsTest.class.getSimpleName());
  }

//  @Test
  void testMinio() throws Exception {
    String url = "http://localhost:9000";
    // 上传根目录
    String bucketName = "oner365";

    MinioClient minioClient = MinioClient.builder().endpoint(url).credentials("root", "e8818da9cc9").build();
    Assertions.assertNotNull(minioClient);
    // 创建文件夹
    BucketExistsArgs bucket = BucketExistsArgs.builder().bucket(bucketName).build();
    if (minioClient.bucketExists(bucket)) {
      logger.info("bucket exists:{}", bucketName);
    } else {
      minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }

    // 文件名称
    String fileName = "12345.jpg";
    // 上传路径
    String path = "images/"+fileName;
    // 本地文件
    String localFileName = "d:/1.jpg";
    // 上传文件
    File file = DataUtils.getFile(localFileName);
    try (InputStream inputStream = new FileInputStream(file)) {
      ObjectWriteResponse writeResponse = minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(path)
          .stream(inputStream, file.length(), -1).contentType(ContentType.IMAGE_JPEG.getMimeType()).build());
      logger.info("file path: {}", writeResponse.object());
    } catch (IOException e) {
      e.printStackTrace();
    }

    // 获取文件
    GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(path).build());
    logger.info("file get: {}", objectResponse);
    objectResponse.close();

    // 下载文件
    String downloadPath = "d:/11111.jpg"; // 重新命名
    minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(path).filename(downloadPath).build());

    // 删除文件
    minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(path).build());
  }
}
