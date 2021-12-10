package com.oner365.monitor.controller.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.controller.BaseController;
import com.oner365.monitor.entity.Server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 服务器监控
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "监控 - 服务器信息")
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {

  /**
   * 当前服务器信息
   */
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public Server index() {
    Server server = new Server();
    server.copyTo();
    return server;
  }
}
