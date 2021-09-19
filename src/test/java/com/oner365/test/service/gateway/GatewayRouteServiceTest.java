package com.oner365.test.service.gateway;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.gateway.entity.GatewayRoute;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.test.service.BaseServiceTest;

/**
 * Test GatewayRouteService
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
public class GatewayRouteServiceTest extends BaseServiceTest {

    @Autowired
    private DynamicRouteService service;

    @RepeatedTest(value = 2)
    public void findList() {
        List<GatewayRoute> list = service.findList();
        LOGGER.info("findList:{}", list.size());
        Assertions.assertNotEquals(0, list.size());
    }

    @Test
    public void pageList() {
        QueryCriteriaBean paramData = new QueryCriteriaBean();
        Page<GatewayRoute> list = service.pageList(paramData);
        LOGGER.info("pageList:{}", list.getSize());
        Assertions.assertNotEquals(0, list.getSize());
    }

    @Test
    public void getById() {
        List<GatewayRoute> list = service.findList();
        if (!list.isEmpty()) {
            GatewayRoute entity = service.getById(list.get(0).getId());
            LOGGER.info("getById:{}", JSON.toJSONString(entity));
            Assertions.assertNotNull(entity);
        }
    }

}
