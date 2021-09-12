package com.oner365.monitor.controller.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 首页信息
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/monitor/dashboard")
@Api(tags = "监控 - 门户信息")
public class DashboardController extends BaseController {

    /**
     * 首页信息
     * @return String
     */
    @GetMapping("/index")
    @ApiOperation("首页")
    public String index() {
        return PublicConstants.SUCCESS;
    }
}
