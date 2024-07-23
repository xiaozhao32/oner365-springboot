package com.oner365.monitor.controller.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.config.properties.CommonProperties;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.web.controller.BaseController;
import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.service.DeployService;
import com.oner365.deploy.utils.DeployMethod;
import com.oner365.monitor.dto.ServiceInfoDto;
import com.oner365.queue.service.IQueueSendService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 服务监控
 *
 * @author zhaoyong
 */
@RestController
@Tag(name = "监控 - 服务信息")
@RequestMapping("/monitor/service")
public class ServiceController extends BaseController {

  @Resource
  private CommonProperties commonProperties;

  @Resource
  private DeployService deployService;
  
  @Resource
  private IQueueSendService queueSendService;

  /**
   * 基本信息
   *
   * @return List<List<ServiceInfoDto>>
   */
  @Operation(summary = "1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public List<List<ServiceInfoDto>> index() {
    List<List<ServiceInfoDto>> serviceList = new ArrayList<>();
    List<ServiceInfoDto> serviceInstances = new ArrayList<>();

    // 获取服务中的实例列表
    ServiceInfoDto serviceInfoDto = new ServiceInfoDto();
    serviceInfoDto.setServiceId(commonProperties.getServiceId());
    serviceInfoDto.setHost(commonProperties.getHost());
    serviceInfoDto.setPort(commonProperties.getPort());
    serviceInfoDto.setUri(PublicConstants.FILE_HTTP + commonProperties.getHost() + PublicConstants.COLON + commonProperties.getPort());
    serviceInfoDto.setInstanceId(commonProperties.getServiceId());
    serviceInfoDto.setScheme(commonProperties.getScheme());
    serviceInstances.add(serviceInfoDto);
    serviceList.add(serviceInstances);
    return serviceList;
  }

  /**
   * 动态刷新配置
   *
   * @return ResponseResult<String>
   */
  @Operation(summary = "2.动态刷新配置")
  @ApiOperationSupport(order = 2)
  @GetMapping("/refresh")
  public ResponseResult<String> refreshConfig() {
    queueSendService.syncRoute();
    return ResponseResult.success(ResultEnum.SUCCESS.getName());
  }

  /**
   * 获取信息
   *
   * @return JSONObject
   */
  @Operation(summary = "3.配置信息")
  @ApiOperationSupport(order = 3)
  @PostMapping("/info")
  public JSONObject getActuatorEnv() {
    JSONObject result = new JSONObject();
    JSONArray profiles = new JSONArray();
    profiles.add(commonProperties.getScheme());
    result.put("activeProfiles", profiles);
    result.put("propertySources", null);
    return result;
  }

  /**
   * 上传服务
   *
   * @return ResponseResult<String>
   */
  @Operation(summary = "4.上传服务")
  @ApiOperationSupport(order = 4)
  @PostMapping("/upload")
  public ResponseResult<String> uploadService() {
    DeployEntity deploy = deployService.getDeployEntity();
    ServerEntity server = deployService.getServerEntity();
    logger.info("Deploy project: {}", server);
    logger.info("Server: {}", server);
    // 部署服务器开关
    if (Boolean.TRUE.equals(server.getIsDeploy())) {
      DeployMethod.deployServer(deploy, server);
    }
    return ResponseResult.success(ResultEnum.SUCCESS.getName());
  }

  /**
   * 重启服务
   *
   * @return ResponseResult<String>
   */
  @Operation(summary = "5.重启服务")
  @ApiOperationSupport(order = 5)
  @PostMapping("/reset")
  public ResponseResult<String> resetService() {
    return ResponseResult.success(ResultEnum.SUCCESS.getName());
  }

}
