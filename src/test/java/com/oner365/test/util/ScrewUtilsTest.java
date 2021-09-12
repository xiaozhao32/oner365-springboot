package com.oner365.test.util;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.druid.pool.DruidDataSource;
import com.oner365.common.constants.PublicConstants;
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
public class ScrewUtilsTest extends BaseUtilsTest {
    
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${file.path}")
    private String filePath;
    
    @Autowired
    private DruidDataSource druidDataSource; 

    @Test
    public void screwUtilsTest() {
        documentGeneration();
    }

    /**
     * 文档生成
     */
    private void documentGeneration() {
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
                .fileOutputDir(filePath)
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
                .version("1.0.0")
                // 描述
                .description("数据库设计文档")
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