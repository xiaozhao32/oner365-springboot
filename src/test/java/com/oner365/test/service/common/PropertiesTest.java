package com.oner365.test.service.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.files.config.properties.FileFdfsProperties;
import com.oner365.files.config.properties.FileLocalProperties;
import com.oner365.files.config.properties.MinioProperties;
import com.oner365.test.service.BaseServiceTest;

/**
 * 单元测试 - 获取属性配置
 *
 * @author zhaoyong
 */
@SpringBootTest
class PropertiesTest extends BaseServiceTest {

  @Autowired
  private FileLocalProperties fileLocalProperties;
  
  @Autowired
  private FileFdfsProperties fileFdfsProperties;
  
  @Autowired
  private MinioProperties minioProperties;
  
  @Test
  void fileLocalPropertiesTest() {
    Assertions.assertNotNull(fileLocalProperties);
    logger.info("Local properties:{}", JSON.toJSONString(fileLocalProperties));
  }
  
  @Test
  void fileFdfsPropertiesTest() {
    Assertions.assertNotNull(fileFdfsProperties);
    logger.info("Fdfs properties:{}", JSON.toJSONString(fileFdfsProperties));
  }
  
  @Test
  void fileMinioPropertiesTest() {
    Assertions.assertNotNull(minioProperties);
    logger.info("Minio properties:{}", JSON.toJSONString(minioProperties));
  }
}
