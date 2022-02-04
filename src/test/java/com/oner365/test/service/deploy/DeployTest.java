package com.oner365.test.service.deploy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.service.DeployService;
import com.oner365.deploy.utils.DeployMethod;
import com.oner365.test.service.BaseServiceTest;

/**
 * 自动化本地项目
 *
 * 部署到本地：请先执行 maven build 生成jar到 target 下在运行
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class DeployTest extends BaseServiceTest {
  
  @Autowired
  private DeployService deployService;

  /**
   * 本地部署
   */
  @Test
  void deployNativeTest() {
    DeployEntity entity = deployService.getDeployEntity();
    Assertions.assertNotNull(entity);
    logger.info("Deploy project: {}", entity);
    // 部署目录
    DeployMethod.deployNative(entity);
  }

  /**
   * 服务器部署
   */
  @Test
  void deployServerTest() {
    DeployEntity deploy = deployService.getDeployEntity();
    ServerEntity server = deployService.getServerEntity();
    Assertions.assertNotNull(server);
    logger.info("Deploy project: {}", server);
    logger.info("Server: {}", server);
    // 部署服务器开关
    if (server.getIsDeploy()) {
      DeployMethod.deployServer(deploy, server);
    }
  }
}
