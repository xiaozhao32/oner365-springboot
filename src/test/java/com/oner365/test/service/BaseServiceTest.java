package com.oner365.test.service;

import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.test.BaseTest;

/**
 * 单元测试父类 - 服务类
 * 
 * @author zhaoyong
 *
 */
@Rollback
@Transactional(rollbackFor = ProjectRuntimeException.class)
public abstract class BaseServiceTest extends BaseTest {

}
