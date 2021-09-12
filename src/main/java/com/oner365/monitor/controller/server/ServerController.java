package com.oner365.monitor.controller.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/monitor/server")
@Api(tags = "监控 - 服务器信息")
public class ServerController extends BaseController {

    /**
     * 当前服务器信息
     */
    @GetMapping("/index")
    @ApiOperation("首页信息")
    public Server index() {
        Server server = new Server();
        server.copyTo();
        return server;
    }
}
