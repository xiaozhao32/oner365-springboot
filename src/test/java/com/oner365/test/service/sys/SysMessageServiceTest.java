package com.oner365.test.service.sys;

import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.oner365.data.jpa.query.AttributeBean;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.enums.MessageTypeEnum;
import com.oner365.sys.service.ISysMessageService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test SysMessageService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysMessageServiceTest extends BaseServiceTest {

    @Resource
    private ISysMessageService service;

    @Test
    void getById() {
        QueryCriteriaBean data = new QueryCriteriaBean();
        data.setWhereList(Collections.singletonList(new AttributeBean("messageType", MessageTypeEnum.DEFAULT)));
        List<SysMessageDto> list = service.findList(data);
        if (!list.isEmpty()) {
            SysMessageDto entity = service.getById(list.get(0).getId());
            logger.info("getById:{}", JSON.toJSONString(entity));
            Assertions.assertNotNull(entity);
        }
    }

}
