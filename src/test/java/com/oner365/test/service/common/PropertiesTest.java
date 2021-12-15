package com.oner365.test.service.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.files.config.properties.FileFdfsProperties;
import com.oner365.files.config.properties.FileLocalProperties;
import com.oner365.files.config.properties.FileMinioProperties;
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
  private FileMinioProperties fileMinioProperties;
  
  @Test
  void fileLocalPropertiesTest() {
    LOGGER.info("Local properties:{}", JSON.toJSONString(fileLocalProperties));
  }
  
  @Test
  void fileFdfsPropertiesTest() {
    LOGGER.info("Fdfs properties:{}", JSON.toJSONString(fileFdfsProperties));
  }
  
  @Test
  void fileMinioPropertiesTest() {
    LOGGER.info("Minio properties:{}", JSON.toJSONString(fileMinioProperties));
  }
}
