package com.oner365.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.exception.ProjectException;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.generator.config.GenConfig;
import com.oner365.generator.constants.GenConstants;
import com.oner365.generator.entity.GenTable;
import com.oner365.generator.entity.GenTableColumn;
import com.oner365.generator.mapper.GenTableColumnMapper;
import com.oner365.generator.mapper.GenTableMapper;
import com.oner365.generator.service.IGenTableService;
import com.oner365.generator.util.GenUtils;
import com.oner365.generator.util.VelocityInitializer;
import com.oner365.generator.util.VelocityUtils;

/**
 * 业务 服务层实现
 *
 * @author zhaoyong
 */
@Service
public class GenTableServiceImpl implements IGenTableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Resource
    private GenTableMapper genTableMapper;

    @Resource
    private GenTableColumnMapper genTableColumnMapper;

    @Resource
    private GenConfig genConfig;

    /**
     * 查询业务信息
     * @param id 业务ID
     * @return 业务信息
     */
    @Override
    public GenTable selectGenTableById(Long id) {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 查询业务列表
     * @param genTable 业务信息
     * @return 业务集合
     */
    @Override
    public List<GenTable> selectGenTableList(GenTable genTable) {
        return genTableMapper.selectGenTableList(genTable);
    }

    /**
     * 查询据库列表
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableList(GenTable genTable) {
        return genTableMapper.selectDbTableList(genTable);
    }

    /**
     * 查询据库列表
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 修改业务
     * @param genTable 业务信息
     */
    @Override
    @Transactional(rollbackFor = ProjectException.class)
    public Boolean updateGenTable(GenTable genTable) {
        String options = JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        genTable.setUpdateTime(DateUtil.getDate());
        int row = genTableMapper.updateGenTable(genTable);
        if (row > 0) {
            genTable.getColumns().forEach(cenTableColumn -> {
                cenTableColumn.setUpdateTime(DateUtil.getDate());
                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            });
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 删除业务对象
     * @param tableIds 需要删除的数据ID
     */
    @Override
    @Transactional(rollbackFor = ProjectException.class)
    public Boolean deleteGenTableByIds(Long[] tableIds) {
        genTableMapper.deleteGenTableByIds(tableIds);
        genTableColumnMapper.deleteGenTableColumnByIds(tableIds);
        return Boolean.TRUE;
    }

    /**
     * 导入表结构
     * @param tableList 导入表列表
     */
    @Override
    @Transactional(rollbackFor = ProjectException.class)
    public Boolean importGenTable(List<GenTable> tableList, String operName) {
        try {
            tableList.forEach(table -> {
                String tableName = table.getTableName();
                GenUtils.initTable(genConfig, table, operName);
                table.setCreateTime(DateUtil.getDate());
                int row = genTableMapper.insertGenTable(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    genTableColumns.forEach(column -> {
                        GenUtils.initColumnField(column, table);
                        column.setCreateTime(DateUtil.getDate());
                        genTableColumnMapper.insertGenTableColumn(column);
                    });
                }
            });
            return Boolean.TRUE;
        }
        catch (Exception e) {
            LOGGER.error("导入失败：", e);
        }
        return Boolean.FALSE;
    }

    /**
     * 预览代码
     * @param tableId 表编号
     * @return 预览数据列表
     */
    @Override
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableById(tableId);
        setPkColumn(table, table.getColumns());
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(table);
        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        // 渲染模板
        templates.forEach(template -> {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Charset.defaultCharset().name());
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        });
        return dataMap;
    }

    /**
     * 生成代码（下载方式）
     * @param tableName 表名称
     * @return 数据
     */
    @Override
    public byte[] downloadCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(outputStream)) {
            generatorCode(tableName, zip);
        }
        catch (IOException e) {
            LOGGER.error("downloadCode error:", e);
        }
        return outputStream.toByteArray();
    }

    /**
     * 生成代码（自定义路径）
     * @param tableName 表名称
     */
    @Override
    public Boolean generatorCode(String tableName) {
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        setPkColumn(table, table.getColumns());
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(table);
        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        // 渲染模板
        templates.stream()
            .filter(template -> !StringUtils.containsAny(template, "sql.vm", "api.js.vm", "index.vue.vm",
                    "index-tree.vue.vm"))
            .forEach(template -> {
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, Charset.defaultCharset().name());
                tpl.merge(context, sw);
                try {
                    String path = getGenPath(table, template);
                    FileUtils.writeStringToFile(Objects.requireNonNull(DataUtils.getFile(path)), sw.toString(),
                            Charset.defaultCharset().name());
                }
                catch (Exception e) {
                    LOGGER.error("渲染模板失败，表名：{}", table.getTableName());
                }
            });
        return Boolean.TRUE;
    }

    /**
     * 同步数据库
     * @param tableName 表名称
     */
    @Override
    @Transactional(rollbackFor = ProjectException.class)
    public Boolean syncDb(String tableName) {
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        List<GenTableColumn> tableColumns = table.getColumns();
        List<String> tableColumnNames = tableColumns.stream()
            .map(GenTableColumn::getColumnName)
            .collect(Collectors.toList());

        List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
        List<String> dbTableColumnNames = dbTableColumns.stream()
            .map(GenTableColumn::getColumnName)
            .collect(Collectors.toList());

        dbTableColumns.forEach(column -> {
            if (!tableColumnNames.contains(column.getColumnName())) {
                GenUtils.initColumnField(column, table);
                table.setCreateTime(DateUtil.getDate());
                genTableColumnMapper.insertGenTableColumn(column);
            }
        });

        List<GenTableColumn> delColumns = tableColumns.stream()
            .filter(column -> !dbTableColumnNames.contains(column.getColumnName()))
            .collect(Collectors.toList());
        if (!DataUtils.isEmpty(delColumns)) {
            genTableColumnMapper.deleteGenTableColumns(delColumns);
        }
        return Boolean.TRUE;
    }

    /**
     * 批量生成代码（下载方式）
     * @param tableNames 表数组
     * @return 数据
     */
    @Override
    public byte[] downloadCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(outputStream)) {
            Arrays.stream(tableNames).forEach(tableName -> generatorCode(tableName, zip));
        }
        catch (IOException e) {
            LOGGER.error("downloadCode error:", e);
        }
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip) {
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        setPkColumn(table, table.getColumns());
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(table);
        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        // 渲染模板
        templates.forEach(template -> {
            try (StringWriter sw = new StringWriter()) {
                Template tpl = Velocity.getTemplate(template, Charset.defaultCharset().name());
                tpl.merge(context, sw);

                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Charset.defaultCharset().name());
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e) {
                LOGGER.error("渲染模板失败，表名：{}", table.getTableName());
            }
        });
    }

    /**
     * 修改保存参数校验
     * @param genTable 业务信息
     */
    @Override
    public void validateEdit(GenTable genTable) {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSON.parseObject(options);
            if (DataUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
                LOGGER.error("树编码字段不能为空");
            }
            else if (DataUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
                LOGGER.error("树父编码字段不能为空");
            }
            else if (DataUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
                LOGGER.error("树名称字段不能为空");
            }
        }
    }

    /**
     * 设置主键列信息
     * @param table 业务表信息
     * @param columns 业务字段列表
     */
    public void setPkColumn(GenTable table, List<GenTableColumn> columns) {
        columns.stream().filter(GenTableColumn::isPk).findFirst().ifPresent(table::setPkColumn);
        if (DataUtils.isEmpty(table.getPkColumn())) {
            table.setPkColumn(columns.get(0));
        }
    }

    /**
     * 设置代码生成其他选项值
     * @param genTable 设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSON.parseObject(genTable.getOptions());
        if (!DataUtils.isEmpty(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
            String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);

            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
            genTable.setParentMenuId(parentMenuId);
            genTable.setParentMenuName(parentMenuName);
        }
    }

    /**
     * 获取代码生成地址
     * @param table 业务表信息
     * @param template 模板文件路径
     * @return 生成地址
     */
    public static String getGenPath(GenTable table, String template) {
        String genPath = table.getGenPath();
        if (StringUtils.equals(genPath, PublicConstants.DELIMITER)) {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator
                    + VelocityUtils.getFileName(template, table);
        }
        return genPath + File.separator + VelocityUtils.getFileName(template, table);
    }

}
