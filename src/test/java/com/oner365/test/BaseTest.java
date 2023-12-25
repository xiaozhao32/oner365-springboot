package com.oner365.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

/**
 * 单元测试父类
 * 
 * @author zhaoyong
 *
 */
@ActiveProfiles("lt")
public abstract class BaseTest {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

}
