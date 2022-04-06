package com.oner365.test.util;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.sys.entity.SysJob;
import com.oner365.util.GsonUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class GsonUtilsTest extends BaseUtilsTest {

  @Test
  void objectToBean() {
    SysJob entity = new SysJob();
    entity.setId("123");
    entity.setJobName("jobName");
    entity.setCreateTime(LocalDateTime.now());
    String str = GsonUtils.objectToJson(entity);
    logger.info("result:{}", str);
    SysJob result = GsonUtils.jsonToBean(str, SysJob.class);
    logger.info("result:{}", result);
    Assertions.assertEquals(entity.getJobName(), result.getJobName());
  }

}
