package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.AttributeBean;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.log.annotation.SysLog;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.SysMenuTypeDto;
import com.oner365.sys.service.ISysMenuTypeService;
import com.oner365.sys.vo.SysMenuTypeVo;
import com.oner365.sys.vo.check.CheckCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 菜单类型管理
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "系统管理 - 菜单类型")
@RequestMapping("/system/menu/type")
public class SysMenuTypeController extends BaseController {

    @Resource
    private ISysMenuTypeService menuTypeService;

    /**
     * 列表
     * @param data 参数
     * @return PageInfo<SysMenuTypeDto>
     */
    @ApiOperation("1.获取分页列表")
    @ApiOperationSupport(order = 1)
    @PostMapping("/list")
    public PageInfo<SysMenuTypeDto> pageList(@RequestBody QueryCriteriaBean data) {
        return menuTypeService.pageList(data);
    }

    /**
     * 列表
     * @return List<SysMenuTypeDto>
     */
    @ApiOperation("2.获取全部有效类型")
    @ApiOperationSupport(order = 2)
    @GetMapping("/all")
    public List<SysMenuTypeDto> findAll() {
        QueryCriteriaBean data = new QueryCriteriaBean();
        List<AttributeBean> whereList = new ArrayList<>();
        AttributeBean attribute = new AttributeBean(SysConstants.STATUS, StatusEnum.YES);
        whereList.add(attribute);
        data.setWhereList(whereList);
        return menuTypeService.findList(data);
    }

    /**
     * 获取信息
     * @param id 编号
     * @return SysMenuTypeDto
     */
    @ApiOperation("3.按id查询")
    @ApiOperationSupport(order = 3)
    @GetMapping("/get/{id}")
    public SysMenuTypeDto get(@PathVariable String id) {
        return menuTypeService.getById(id);
    }

    /**
     * 修改状态
     * @param id 主键
     * @param status 状态
     * @return Boolean
     */
    @ApiOperation("4.修改状态")
    @ApiOperationSupport(order = 4)
    @SysLog("修改菜单类型状态")
    @PostMapping("/status/{id}")
    public Boolean editStatus(@PathVariable String id, @RequestParam StatusEnum status) {
        return menuTypeService.editStatus(id, status);
    }

    /**
     * 判断是否存在
     * @param checkCodeVo 查询参数
     * @return Boolean
     */
    @ApiOperation("5.判断是否存在")
    @ApiOperationSupport(order = 5)
    @PostMapping("/check")
    public Boolean checkCode(@Validated @RequestBody CheckCodeVo checkCodeVo) {
        if (checkCodeVo != null) {
            return menuTypeService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
        }
        return Boolean.FALSE;
    }

    /**
     * 保存
     * @param sysMenuTypeVo 菜单类型对象
     * @return SysMenuTypeDto
     */
    @ApiOperation("6.保存")
    @ApiOperationSupport(order = 6)
    @SysLog("保存菜单类型")
    @PutMapping("/save")
    public SysMenuTypeDto save(@Validated @RequestBody SysMenuTypeVo sysMenuTypeVo) {
        return menuTypeService.save(sysMenuTypeVo);
    }

    /**
     * 删除
     * @param ids 编号
     * @return List<Boolean>
     */
    @ApiOperation("7.删除")
    @ApiOperationSupport(order = 7)
    @SysLog("删除菜单类型")
    @DeleteMapping("/delete")
    public List<Boolean> delete(@RequestBody String... ids) {
        return Arrays.stream(ids).map(id -> menuTypeService.deleteById(id)).collect(Collectors.toList());
    }

}
