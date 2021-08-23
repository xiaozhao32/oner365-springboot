package com.oner365.deploy;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.deploy.entity.SshServer;
import com.oner365.deploy.utils.DeployMethod;
import com.oner365.deploy.utils.DeployUtils;

/**
 * 自动化本地项目
 *
 * 部署到本地：请先执行 maven build 生成jar到 target 下在运行
 *
 * @author zhaoyong
 *
 */
public class DeployServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployServer.class);

    /**
     * @param args 测试
     */
    public static void main(String[] args) {
        Properties properties = DeployUtils.getProperties();
        String project = properties.get("deploy.project").toString();
        String[] projectNames = StringUtils.split(project, ",");

        // 服务器信息
        List<SshServer> deployServerList = DeployMethod.getServerList(properties);
        LOGGER.info("Deploy Server name: {}", properties.getProperty("servers.name"));
        LOGGER.info("Deploy Server project: {}", deployServerList);
        
        // 部署目录
        DeployMethod.deployNative(projectNames, properties.getProperty("deploy.name"),
                properties.getProperty("deploy.version"), properties.getProperty("deploy.suffix"), 
                properties.getProperty("deploy.location"));
        
        // 部署服务器目录
        DeployMethod.deployServer(deployServerList, projectNames, properties.getProperty("deploy.name"),
                properties.getProperty("servers.name"), properties.getProperty("deploy.version"),
                properties.getProperty("deploy.suffix"), properties.getProperty("deploy.lib"));
    }

}
