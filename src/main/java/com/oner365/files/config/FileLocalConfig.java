package com.oner365.files.config;

import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.enums.StorageEnum;
import com.oner365.files.config.properties.FileLocalProperties;
import com.oner365.files.storage.condition.LocalStorageCondition;

/**
 * File Local Config
 * 
 * @author zhaoyong
 */
@Configuration
@Conditional(LocalStorageCondition.class)
@EnableConfigurationProperties({ FileLocalProperties.class })
public class FileLocalConfig {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(FileLocalConfig.class);
  
  @Resource
  private FileLocalProperties fileLocalProperties;

  public FileLocalConfig() {
    LOGGER.info("Storage Type: {}, Properties: {}", StorageEnum.LOCAL, fileLocalProperties);
  }
  
}
