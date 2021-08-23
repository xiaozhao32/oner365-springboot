package com.oner365.gateway.rabbitmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oner365.gateway.rabbitmq.ISyncRouteService;
import com.oner365.gateway.service.DynamicRouteService;

/**
 * SyncRoute实现类
 * @author zhaoyong
 */
@Service
public class SyncRoutServiceImpl implements ISyncRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncRoutServiceImpl.class);

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @Override
    public void syncRoute(String gatewayIp) {
        LOGGER.info("MQ pull: {}", gatewayIp);
        dynamicRouteService.refreshRoute();
    }

}
