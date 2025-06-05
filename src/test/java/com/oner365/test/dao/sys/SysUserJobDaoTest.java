package com.oner365.test.dao.sys;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.sys.dao.ISysUserJobDao;
import com.oner365.test.dao.BaseDaoTest;

/**
 * Test SysUserJobDao
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysUserJobDaoTest extends BaseDaoTest {

    @Resource
    private ISysUserJobDao dao;

    @Test
    void findUserJobByUserId() {
        String userId = "1";
        List<String> result = dao.findUserJobByUserId(userId);
        logger.info("result:{}", result.size());
        Assertions.assertNotEquals(0, result.size());
    }

}
