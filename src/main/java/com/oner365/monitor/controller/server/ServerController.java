package com.oner365.monitor.controller.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.web.controller.BaseController;
import com.oner365.monitor.entity.Server;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 服务器监控
 * 
 * @author zhaoyong
 */
@RestController
@Tag(name = "监控 - 服务器信息")
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {

  /**
   * 当前服务器信息
   */
  @Operation(summary = "1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public Server index() {
    Server server = new Server();
    server.copyTo();
    return server;
  }
}
