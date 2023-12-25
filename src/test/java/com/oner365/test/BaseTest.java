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
<<<<<<< HEAD
@ActiveProfiles("lt")
=======
@ActiveProfiles("dev")
>>>>>>> e44257a97644017f539c7074c5062b089fd4dc26
public abstract class BaseTest {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

}
