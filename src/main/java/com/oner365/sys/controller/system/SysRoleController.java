package com.oner365.sys.controller.system;

import java.util.List;

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

import com.oner365.common.ResponseResult;
import com.oner365.common.constants.ErrorInfo;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.entity.SysRole;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.vo.SysRoleVo;
import com.oner365.sys.vo.check.CheckRoleNameVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色管理
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "系统管理 - 角色")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    /**
     * 获取信息
     * 
     * @param id 编号
     * @return SysRole
     */
    @GetMapping("/get/{id}")
    @ApiOperation("按id查询")
    public SysRole get(@PathVariable String id) {
        return roleService.getById(id);
    }

    /**
     * 列表
     * 
     * @param data 查询参数
     * @return Page<SysRole>
     */
    @PostMapping("/list")
    @ApiOperation("获取列表")
    public Page<SysRole> list(@RequestBody QueryCriteriaBean data) {
        return roleService.pageList(data);
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
            code = roleService.deleteById(id);
        }
        return code;
    }

    /**
     * 修改状态
     *
     * @param id     主键
     * @param status 状态
     * @return Integer
     */
    @PostMapping("/editStatus/{id}")
    @ApiOperation("修改状态")
    public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
        return roleService.editStatus(id, status);
    }

    /**
     * 判断是否存在
     * 
     * @param checkRoleNameVo 查询参数
     * @return Long
     */
    @PostMapping("/checkRoleName")
    @ApiOperation("判断角色名称存在")
    public Long checkRoleName(@RequestBody CheckRoleNameVo checkRoleNameVo) {
        if (checkRoleNameVo != null) {
            return roleService.checkRoleName(checkRoleNameVo.getId(), checkRoleNameVo.getRoleName());
        }
        return Long.valueOf(PublicConstants.ERROR_CODE);
    }

    /**
     * 角色权限保存
     * 
     * @param sysRoleVo 参数
     * @return ResponseResult<Integer>
     */
    @PutMapping("/save")
    @ApiOperation("保存")
    public ResponseResult<Integer> save(@RequestBody SysRoleVo sysRoleVo) {
        if (sysRoleVo != null) {
            // 保存角色
            SysRole entity = roleService.save(sysRoleVo.toObject());
            if (entity != null) {
                // 保存权限
                int code = roleService.saveAuthority(sysRoleVo.getMenuType(), sysRoleVo.getMenuIds(), entity.getId());
                return ResponseResult.success(code);
            }
        }
        return ResponseResult.error(ErrorInfo.ERR_SAVE_ERROR);
    }

    /**
     * 导出Excel
     * 
     * @param data 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/export")
    @ApiOperation("导出")
    public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
        List<SysRole> list = roleService.findList(data);

        String[] titleKeys = new String[] { "编号", "角色标识", "角色名称", "角色描述", "状态", "创建时间", "更新时间" };
        String[] columnNames = { "id", "roleCode", "roleName", "roleDes", "status", "createTime", "updateTime" };

        String fileName = SysRole.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
