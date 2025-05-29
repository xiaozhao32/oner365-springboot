package com.oner365.test.mapper.generator;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.DateUtil;
import com.oner365.generator.entity.GenTable;
import com.oner365.generator.mapper.GenTableMapper;
import com.oner365.test.mapper.BaseMapperTest;

/**
 * Mapper 单元测试
 *
 * @author zhaoyong
 *
 */
class GenTableMapperTest extends BaseMapperTest {

    @Test
    void selectGenTableList() {
        GenTable genTable = new GenTable();
        genTable.setTableName("test");
        List<GenTable> list = getMapper(GenTableMapper.class).selectGenTableList(genTable);
        logger.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void selectDbTableList() {
        GenTable genTable = new GenTable();
        genTable.setTableName("user");
        List<GenTable> list = getMapper(GenTableMapper.class).selectDbTableList(genTable);
        logger.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void selectDbTableListByNames() {
        String[] tableNames = new String[] { "nt_sys_user" };
        List<GenTable> list = getMapper(GenTableMapper.class).selectDbTableListByNames(tableNames);
        logger.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void selectGenTableByName() {
        String tableName = "nt_test_date";
        GenTable genTable = getMapper(GenTableMapper.class).selectGenTableByName(tableName);
        logger.info("entity:{}", genTable);
        Assertions.assertNotNull(genTable);
    }

    @Test
    void insertGenTable() {
        GenTable entity = new GenTable();
        entity.setTableName("testTableName");
        entity.setTableComment("testTableComment");
        entity.setClassName("Test");
        entity.setTplCategory("crud");
        entity.setPackageName("com.oner365.test");
        entity.setModuleName("module");
        entity.setBusinessName("business");
        entity.setFunctionName("Generator");
        entity.setFunctionAuthor("oner365");
        entity.setGenType("0");
        entity.setGenPath("/");
        entity.setCreateBy("admin");
        entity.setCreateTime(DateUtil.getDate());

        int isCreate = getMapper(GenTableMapper.class).insertGenTable(entity);
        logger.info("insert success: {} tableId: {}", isCreate, entity.getTableId());
        Assertions.assertNotEquals(0, isCreate);
    }

}
