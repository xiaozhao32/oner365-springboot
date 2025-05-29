package com.oner365.test.service.files;

import java.util.List;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.files.dto.SysFileStorageDto;
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

    @Resource
    private IFileStorageService service;

    @RepeatedTest(value = 2)
    void findList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysFileStorageDto> list = service.findList(paramData);
        logger.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        PageInfo<SysFileStorageDto> list = service.pageList(paramData);
        logger.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    void getById() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        List<SysFileStorageDto> list = service.findList(paramData);
        if (!list.isEmpty()) {
            SysFileStorageDto entity = service.getById(list.get(0).getId());
            logger.info("getById:{}", JSON.toJSONString(entity));
            Assertions.assertNotNull(entity);
        }
    }

}
