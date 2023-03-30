package com.oner365.test.mapper;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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
public abstract class BaseMapperTest extends BaseTest {

  protected <T> T getMapper(Class<T> clazz) {
    try (InputStream in = Resources.getResourceAsStream("config/sqlMapConfig.xml")) {
      SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
      SqlSessionFactory factory = builder.build(in);
      SqlSession session = factory.openSession();
      return session.getMapper(clazz);
    } catch (Exception e) {
      logger.error("mapper error", e);
    }
    return null;
  }
}
