package com.oner365.test.mapper.sys;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.sys.mapper.SysOrganizationMapper;
import com.oner365.test.mapper.BaseMapperTest;

/**
 * Mapper 单元测试
 * 
 * @author zhaoyong
 *
 */
public class SysOrganizationMapperTest extends BaseMapperTest {

  @Test
  void selectListByUserId() {
    String userId = "1";
    List<String> list = getMapper(SysOrganizationMapper.class).selectListByUserId(userId);
    logger.info("findList:{}", list.size());
    Assertions.assertNotEquals(0, list.size());
  }
  
}
