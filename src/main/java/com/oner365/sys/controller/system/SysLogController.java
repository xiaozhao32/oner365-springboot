package com.oner365.sys.controller.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.entity.SysLog;
import com.oner365.sys.service.ISysLogService;
import com.oner365.sys.vo.SysLogVo;
import com.oner365.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统日志控制器
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/log")
@Api(tags = "系统观察 - 日志")
public class SysLogController extends BaseController {

    @Autowired
    private ISysLogService logService;

    /**
     * 保存
     * 
     * @param sysLogVo 菜单类型对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    @ApiOperation("保存")
    public Map<String, Object> save(@RequestBody SysLogVo sysLogVo) {
        SysLog sysLog = sysLogVo.toObject();
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysLog != null) {
            logService.save(sysLog);
            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, sysLog);
        }
        return result;
    }

    /**
     * 获取信息
     * 
     * @param id 编号
     * @return SysLog
     */
    @GetMapping("/get/{id}")
    @ApiOperation("按id查询")
    public SysLog get(@PathVariable String id) {
        return logService.getById(id);
    }

    /**
     * 列表
     * 
     * @param data 查询参数
     * @return Page<SysLog>
     */
    @PostMapping("/list")
    @ApiOperation("获取列表")
    public Page<SysLog> list(@RequestBody QueryCriteriaBean data) {
        return logService.pageList(data);
    }

    /**
     * 删除
     * 
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Integer delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = logService.deleteById(id);
        }
        return code;
    }

    /**
     * 按日期删除日志
     * 
     * @param date 日期
     * @return Integer
     */
    @DeleteMapping("/deleteLog")
    @ApiOperation("删除日志")
    public Integer deleteLog(@RequestParam("date") String date) {
        return logService.deleteLog(DateUtil.stringToDate(date, DateUtil.FULL_DATE_FORMAT));
    }

    /**
     * 导出日志
     * 
     * @param data 查询参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/export")
    @ApiOperation("导出")
    public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
        List<SysLog> list = logService.findList(data);

        String[] titleKeys = new String[] { "编号", "请求IP", "请求方法", "服务名称", "请求地址", "请求内容", "创建时间" };
        String[] columnNames = { "id", "operationIp", "methodName", "operationName", "operationPath",
                "operationContext", "createTime" };

        String fileName = SysLog.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
