package com.oner365.deploy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.deploy.config.properties.LocalDeployProperties;
import com.oner365.deploy.config.properties.ServerDeployProperties;
import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.DeployServer;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.service.DeployService;

/**
 * 部署服务
 *
 * @author zhaoyong
 *
 */
@Service
public class DeployServiceImpl implements DeployService {

    @Resource
    private LocalDeployProperties localDeployProperties;

    @Resource
    private ServerDeployProperties serverDeployProperties;

    @Override
    public DeployEntity getDeployEntity() {
        DeployEntity result = new DeployEntity();
        result.setActive(localDeployProperties.getActive());
        result.setLocation(localDeployProperties.getLocation());
        result.setName(localDeployProperties.getName());
        result.setSuffix(localDeployProperties.getSuffix());
        result.setVersion(localDeployProperties.getVersion());
        result.setProjects(localDeployProperties.getProjects());
        result.setLibs(localDeployProperties.getLibs());
        return result;
    }

    @Override
    public ServerEntity getServerEntity() {
        ServerEntity result = new ServerEntity();
        result.setIsDeploy(serverDeployProperties.getDeploy());
        result.setServerName(serverDeployProperties.getName());
        List<DeployServer> serverList = new ArrayList<>();
        List<String> servers = serverDeployProperties.getServers();
        servers.forEach(server -> {
            String[] splitServer = StringUtils.split(server, "@");
            if (splitServer.length > 1) {
                DeployServer deployServer = new DeployServer();
                deployServer.setIp(StringUtils.substringBefore(splitServer[0], PublicConstants.COLON));
                deployServer
                    .setPort(Integer.parseInt(StringUtils.substringAfter(splitServer[0], PublicConstants.COLON)));
                deployServer.setUsername(StringUtils.substringBefore(splitServer[1], PublicConstants.COLON));
                deployServer.setPassword(StringUtils.substringAfter(splitServer[1], PublicConstants.COLON));
                serverList.add(deployServer);
            }
        });
        result.setServerList(serverList);
        return result;
    }

}
