package com.oner365.test.service.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.data.commons.config.properties.AccessTokenProperties;
import com.oner365.test.service.BaseServiceTest;

import jakarta.annotation.Resource;
import tools.jackson.databind.ObjectMapper;

/**
 * 单元测试 - 获取属性配置
 *
 * @author zhaoyong
 */
@SpringBootTest
class PropertiesTest extends BaseServiceTest {

    @Resource
    private AccessTokenProperties properties;
    
    @Resource 
    private ObjectMapper objectMapper;
    
    @Test
    void filePropertiesTest() {
        logger.info("properties:{}", objectMapper.writeValueAsString(properties));
        Assertions.assertNotNull(properties);
    }

}
