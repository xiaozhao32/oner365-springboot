package com.oner365.sys.controller.datasource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

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
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.log.annotation.SysLog;
import com.oner365.sys.dto.DataSourceConfigDto;
import com.oner365.sys.service.IDataSourceConfigService;
import com.oner365.sys.vo.DataSourceConfigVo;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * 数据源
 *
 * @author zhaoyong
 *
 */
@RestController
@Tag(name = "数据源信息")
@RequestMapping("/system/datasource")
public class DataSourceConfigController extends BaseController {

    @Resource
    private IDataSourceConfigService service;

    /**
     * 列表
     * @param data 查询参数
     * @return PageInfo<DataSourceConfigDto>
     */
    @Operation(summary = "1.获取列表")
    @ApiOperationSupport(order = 1)
    @PostMapping("/list")
    public PageInfo<DataSourceConfigDto> pageList(@RequestBody QueryCriteriaBean data) {
        return service.pageList(data);
    }

    /**
     * 按id获取信息
     * @param id 编号
     * @return DataSourceConfigDto
     */
    @Operation(summary = "2.按id查询")
    @ApiOperationSupport(order = 2)
    @GetMapping("/get/{id}")
    public DataSourceConfigDto get(@PathVariable String id) {
        return service.getById(id);
    }

    /**
     * 按 connectName 获取信息
     * @param connectName 连接名称
     * @return DataSourceConfigDto
     */
    @Operation(summary = "3.按连接名称查询")
    @ApiOperationSupport(order = 3)
    @GetMapping("/name")
    public DataSourceConfigDto getConnectName(@RequestParam String connectName) {
        return service.getConnectName(connectName);
    }

    /**
     * 保存
     * @param dataSourceConfigVo 数据源对象
     * @return DataSourceConfigDto
     */
    @Operation(summary = "4.保存")
    @ApiOperationSupport(order = 4)
    @SysLog("数据源保存")
    @PutMapping("/save")
    public DataSourceConfigDto save(@Validated @RequestBody DataSourceConfigVo dataSourceConfigVo) {
        return service.save(dataSourceConfigVo);
    }

    /**
     * 删除
     * @param ids 编号
     * @return List<Boolean>
     */
    @Operation(summary = "5.删除")
    @ApiOperationSupport(order = 5)
    @SysLog("数据源删除")
    @DeleteMapping("/delete")
    public List<Boolean> delete(@RequestBody String... ids) {
        return Arrays.stream(ids).map(id -> service.deleteById(id)).collect(Collectors.toList());
    }

}
