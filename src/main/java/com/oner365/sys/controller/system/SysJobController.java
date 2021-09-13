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

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.sys.entity.SysJob;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.vo.SysJobVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户职位信息
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/job")
@Api(tags = "系统管理 - 用户职位")
public class SysJobController extends BaseController {

    @Autowired
    private ISysJobService sysJobService;

    /**
     * 用户职位保存
     * 
     * @param sysJobVo 职位对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    @ApiOperation("保存")
    public Map<String, Object> save(@RequestBody SysJobVo sysJobVo) {
        SysJob sysJob = sysJobVo.toObject();
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysJob != null) {
            SysJob entity = sysJobService.save(sysJob);
            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 获取用户职位
     * 
     * @param id 编号
     * @return SysJob
     */
    @GetMapping("/get/{id}")
    @ApiOperation("按id查询")
    public SysJob get(@PathVariable String id) {
        return sysJobService.getById(id);
    }

    /**
     * 用户职位列表
     * 
     * @param paramJson 参数
     * @return Page<SysJob>
     */
    @PostMapping("/list")
    @ApiOperation("获取列表")
    public Page<SysJob> list(@RequestBody JSONObject paramJson) {
        return sysJobService.pageList(paramJson);
    }

    /**
     * 删除用户职位
     * 
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Integer delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = sysJobService.deleteById(id);
        }
        return code;
    }

    /**
     * 修改职位状态
     *
     * @param id 主键
     * @param status 状态
     * @return Integer
     */
    @PostMapping("/editStatus/{id}")
    @ApiOperation("修改状态")
    public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
        return sysJobService.editStatus(id, status);
    }

    /**
     * 导出Excel
     * 
     * @param paramJson 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/export")
    @ApiOperation("导出")
    public ResponseEntity<byte[]> export(@RequestBody JSONObject paramJson) {
        List<SysJob> list = sysJobService.findList(paramJson);

        String[] titleKeys = new String[] { "编号", "职位名称", "职位描述", "排序", "状态", "创建时间", "更新时间" };
        String[] columnNames = { "id", "jobName", "jobInfo", "jobOrder", "status", "createTime", "updateTime" };

        String fileName = SysJob.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
