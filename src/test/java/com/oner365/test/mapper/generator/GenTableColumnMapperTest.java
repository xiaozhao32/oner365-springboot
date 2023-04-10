package com.oner365.test.mapper.generator;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.generator.entity.GenTableColumn;
import com.oner365.generator.mapper.GenTableColumnMapper;
import com.oner365.test.mapper.BaseMapperTest;

/**
 * Mapper 单元测试
 * 
 * @author zhaoyong
 *
 */
class GenTableColumnMapperTest extends BaseMapperTest {

  @Test
  void selectGenTableList() {
    String tableName = "nt_sys_user";
    List<GenTableColumn> list = getMapper(GenTableColumnMapper.class).selectDbTableColumnsByName(tableName);
    logger.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }

}
