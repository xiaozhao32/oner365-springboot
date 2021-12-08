package com.oner365.sys.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.service.ISysMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 消息通知
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/message")
@Api(tags = "系统管理 - 消息")
public class SysMessageController extends BaseController {

    @Autowired
    private ISysMessageService sysMessageService;

    /**
     * 查询结果 有返回 true 并且删除
     * @param messageType 消息类型
     * @return ResponseResult<Boolean>
     */
    @GetMapping("/refresh")
    @ApiOperation("刷新结果")
    public ResponseResult<Boolean> refresh(@RequestParam("messageType") String messageType) {
        List<SysMessageDto> list = sysMessageService.findList(messageType);
        if (!list.isEmpty()) {
            list.forEach(entity -> sysMessageService.deleteById(entity.getId()));
            return ResponseResult.success(Boolean.TRUE);
        }
        return ResponseResult.success(Boolean.FALSE);
    }
}
