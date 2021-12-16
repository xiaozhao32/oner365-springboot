package com.oner365.monitor.controller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.utils.DeployMethod;
import com.oner365.deploy.utils.DeployUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 服务监控
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "监控 - 服务信息")
@RequestMapping("/monitor/service")
public class ServiceController extends BaseController {

  private static final String HOST = "localhost";

  @Value("${spring.application.name}")
  private String serviceId;
  @Value("${server.port}")
  private int port;
  @Value("${spring.profiles.active}")
  private String scheme;

  /**
   * 基本信息
   * 
   * @return List<List<Map<String, Object>>>
   */
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public List<List<Map<String, Object>>> index() {
    List<List<Map<String, Object>>> serviceList = new ArrayList<>();
    List<Map<String, Object>> serviceInstances = new ArrayList<>();

    // 获取服务中的实例列表
    Map<String, Object> map = new HashMap<>(10);
    map.put("serviceId", serviceId);
    map.put("host", HOST);
    map.put("port", port);
    map.put("uri", "http://" + HOST + ":" + port);
    map.put("instanceId", serviceId);
    map.put("scheme", scheme);
    serviceInstances.add(map);
    serviceList.add(serviceInstances);
    return serviceList;
  }

  /**
   * 动态刷新配置
   * 
   * @return String
   */
  @ApiOperation("2.动态刷新配置")
  @ApiOperationSupport(order = 2)
  @GetMapping("/refreshConfig")
  public String refreshConfig() {
    return ResultEnum.SUCCESS.getName();
  }

  /**
   * 获取信息
   * 
   * @return JSONObject
   */
  @ApiOperation("3.配置信息")
  @ApiOperationSupport(order = 3)
  @PostMapping("/getActuatorEnv")
  public JSONObject getActuatorEnv() {
    JSONObject result = new JSONObject();
    JSONArray profiles = new JSONArray();
    profiles.add(scheme);
    result.put("activeProfiles", profiles);
    result.put("propertySources", null);
    return result;
  }

  /**
   * 上传服务
   *
   * @param multipartFile 文件
   * @return String
   */
  @ApiOperation("4.上传服务")
  @ApiOperationSupport(order = 4)
  @PostMapping("/uploadService")
  public String uploadService() {
    DeployEntity deploy = DeployUtils.getDeployEntity();
    ServerEntity server = DeployUtils.getServerEntity();
    LOGGER.info("Deploy project: {}", server);
    LOGGER.info("Server: {}", server);
    // 部署服务器开关
    if (Boolean.TRUE.equals(server.getIsDeploy())) {
      DeployMethod.deployServer(deploy, server);
    }
    return ResultEnum.SUCCESS.getName();
  }

  /**
   * 重启服务
   * 
   * @param deployServer 对象
   * @return String
   */
  @ApiOperation("5.重启服务")
  @ApiOperationSupport(order = 5)
  @PostMapping("/resetService")
  public String resetService() {
    return ResultEnum.SUCCESS.getName();
  }

}
