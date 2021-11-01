package com.oner365.test.service.files;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.files.entity.SysFileStorage;
import com.oner365.files.service.IFileStorageService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test FastdfsFileService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class FastdfsFileServiceTest extends BaseServiceTest {

    @Autowired
    private IFileStorageService service;

    @RepeatedTest(value = 2)
    void findList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysFileStorage> list = service.findList(paramData);
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<SysFileStorage> list = service.pageList(paramData);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    void getById() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysFileStorage> list = service.findList(paramData);
        if (!list.isEmpty()) {
            SysFileStorage entity = service.getById(list.get(0).getId());
            LOGGER.info("getById:{}", JSON.toJSONString(entity));
            Assertions.assertNotNull(entity);
        }
    }

}
