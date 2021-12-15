package com.oner365.test.service.deploy;

import org.junit.jupiter.api.Test;

import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.utils.DeployMethod;
import com.oner365.deploy.utils.DeployUtils;
import com.oner365.test.service.BaseServiceTest;

/**
 * 自动化本地项目
 *
 * 部署到本地：请先执行 maven build 生成jar到 target 下在运行
 *
 * @author zhaoyong
 *
 */
class DeployTest extends BaseServiceTest {

  /**
   * 本地部署
   */
  @Test
  public void deployNativeTest() {
    DeployEntity entity = DeployUtils.getDeployEntity();
    LOGGER.info("Deploy project: {}", entity);
    // 部署目录
    DeployMethod.deployNative(entity);
  }

  /**
   * 服务器部署
   */
  @Test
  public void deployServerTest() {
    DeployEntity deploy = DeployUtils.getDeployEntity();
    ServerEntity server = DeployUtils.getServerEntity();
    LOGGER.info("Deploy project: {}", server);
    LOGGER.info("Server: {}", server);
    // 部署服务器开关
    if (server.getIsDeploy()) {
      DeployMethod.deployServer(deploy, server);
    }
  }
}
