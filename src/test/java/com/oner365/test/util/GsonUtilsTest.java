package com.oner365.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.GsonUtils;
import com.oner365.sys.entity.SysJob;

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
        String str = GsonUtils.objectToJson(entity);
        logger.info("result:{}", str);
        SysJob result = GsonUtils.jsonToBean(str, SysJob.class);
        logger.info("result:{}", result);
        Assertions.assertEquals(entity.getJobName(), result.getJobName());
    }

}
