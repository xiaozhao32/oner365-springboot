package com.oner365.sys.controller.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysMenuOperation;
import com.oner365.sys.service.ISysMenuOperationService;
import com.oner365.sys.vo.SysMenuOperationVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 菜单操作权限
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/system/menuOperation")
@Api(tags = "系统管理 - 菜单操作权限")
public class SysMenuOperationController extends BaseController {

    @Autowired
    private ISysMenuOperationService menuOperationService;

    /**
     * 保存
     * 
     * @param sysMenuOperationVo 操作对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    @ApiOperation("保存")
    public Map<String, Object> save(@RequestBody SysMenuOperationVo sysMenuOperationVo) {
        SysMenuOperation sysMenuOperation = sysMenuOperationVo.toObject();
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysMenuOperation != null) {
            SysMenuOperation entity = menuOperationService.save(sysMenuOperation);

            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 获取信息
     * 
     * @param id 编号
     * @return SysMenuOperation
     */
    @GetMapping("/get/{id}")
    @ApiOperation("按id查询")
    public SysMenuOperation getById(@PathVariable String id) {
        return menuOperationService.getById(id);
    }

    /**
     * 列表
     * 
     * @param data 查询参数
     * @return Page<SysMenuOperation>
     */
    @PostMapping("/list")
    @ApiOperation("获取列表")
    public Page<SysMenuOperation> findList(@RequestBody QueryCriteriaBean data) {
        return menuOperationService.pageList(data);
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
            code = menuOperationService.deleteById(id);
        }
        return code;
    }

    /**
     * 判断是否存在
     * 
     * @param paramJson 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkType")
    @ApiOperation("判断是否存在")
    public Map<String, Object> checkType(@RequestBody JSONObject paramJson) {
        String id = paramJson.getString(SysConstants.ID);
        String type = paramJson.getString(SysConstants.CODE);

        int code = menuOperationService.checkType(id, type);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }
}
