package com.oner365.test.util;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.druid.pool.DruidDataSource;
import com.oner365.common.config.properties.DefaultFileProperties;
import com.oner365.common.constants.PublicConstants;
import com.oner365.datasource.dynamic.DynamicDataSource;
import com.oner365.swagger.config.properties.SwaggerProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;

/**
 * 生成数据库文档 - 测试
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class ScrewUtilsTest extends BaseUtilsTest {

  @Resource
  private DataSource dataSource;

  @Resource
  private DefaultFileProperties fileProperties;
  
  @Resource
  private SwaggerProperties swaggerProperties;

  @Test
  void screwUtilsTest() {
    Assertions.assertEquals("ScrewUtilsTest", ScrewUtilsTest.class.getSimpleName());
    documentGeneration();
  }

  /**
   * 文档生成
   */
  private void documentGeneration() {
    DynamicDataSource dynamicDataSource = (DynamicDataSource) dataSource;
    DruidDataSource druidDataSource = (DruidDataSource)dynamicDataSource.getResolvedDefaultDataSource();
    // 数据源
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(druidDataSource.getDriverClassName());
    hikariConfig.setJdbcUrl(druidDataSource.getUrl());
    hikariConfig.setUsername(druidDataSource.getUsername());
    hikariConfig.setPassword(druidDataSource.getPassword());
    // 设置可以获取tables remarks信息
    hikariConfig.addDataSourceProperty(PublicConstants.NAME, Boolean.TRUE.toString());
    hikariConfig.setMinimumIdle(druidDataSource.getMinIdle());
    hikariConfig.setMaximumPoolSize(druidDataSource.getMaxActive());

    DataSource dataSource = new HikariDataSource(hikariConfig);

    // 生成配置
    EngineConfig engineConfig = EngineConfig.builder()
        // 生成文件路径
        .fileOutputDir(fileProperties.getDownload())
        // 打开目录
        .openOutputDir(true)
        // 文件类型
        .fileType(EngineFileType.HTML)
        // 生成模板实现
        .produceType(EngineTemplateType.freemarker)
        // 自定义文件名称
        .fileName(PublicConstants.NAME + "-database").build();

    // 忽略表
    ArrayList<String> ignoreTableName = new ArrayList<>();
    // 忽略表前缀
    ArrayList<String> ignorePrefix = new ArrayList<>();
    // 忽略表后缀
    ArrayList<String> ignoreSuffix = new ArrayList<>();
    ProcessConfig processConfig = ProcessConfig.builder()
        // 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
        // 根据名称指定表生成
        .designatedTableName(new ArrayList<>())
        // 根据表前缀生成
        .designatedTablePrefix(new ArrayList<>())
        // 根据表后缀生成
        .designatedTableSuffix(new ArrayList<>())
        // 忽略表名
        .ignoreTableName(ignoreTableName)
        // 忽略表前缀
        .ignoreTablePrefix(ignorePrefix)
        // 忽略表后缀
        .ignoreTableSuffix(ignoreSuffix).build();
    // 配置
    Configuration config = Configuration.builder()
        // 版本
        .version(swaggerProperties.getVersion())
        // 描述
        .description(swaggerProperties.getDescription())
        // 数据源
        .dataSource(dataSource)
        // 生成配置
        .engineConfig(engineConfig)
        // 生成配置
        .produceConfig(processConfig).build();
    // 执行生成
    new DocumentationExecute(config).execute();
  }
}
