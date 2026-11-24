package com.oner365.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

/**
 * 单元测试父类
 * 
 * @author zhaoyong
 *
 */
@ActiveProfiles("dev")
@TestPropertySource(properties = "ONER365-HOST=192.168.1.174")
public abstract class BaseTest {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

}
