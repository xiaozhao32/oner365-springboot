package com.oner365.sys.controller.datasource;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.entity.DataSourceConfig;
import com.oner365.sys.service.IDataSourceConfigService;
import com.oner365.sys.vo.DataSourceConfigVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据源
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/system/datasource")
@Api(tags = "数据源信息")
public class DataSourceConfigController extends BaseController {

    @Autowired
    private IDataSourceConfigService service;

    /**
     * 列表
     * @param data 查询参数
     * @return Page<DataSourceConfig>
     */
    @PostMapping("/list")
    @ApiOperation("列表")
    public Page<DataSourceConfig> findList(@RequestBody QueryCriteriaBean data) {
        return service.pageList(data);
    }

    /**
     * 按id获取信息
     * @param id 编号
     * @return DataSourceConfig
     */
    @GetMapping("/get/{id}")
    @ApiOperation("按id查询")
    public DataSourceConfig get(@PathVariable String id) {
        return service.getById(id);
    }

    /**
     * 按 connectName 获取信息
     * @param connectName 连接名称
     * @return DataSourceConfig
     */
    @GetMapping("/getConnectName")
    @ApiOperation("按连接名称查询")
    public DataSourceConfig getConnectName(@RequestParam String connectName) {
        return service.getConnectName(connectName);
    }

    /**
     * 保存
     * @param dataSourceConfigVo 数据源对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    @ApiOperation("保存")
    public Map<String, Object> save(@RequestBody DataSourceConfigVo dataSourceConfigVo) {
        DataSourceConfig dataSourceConfig = dataSourceConfigVo.toObject();
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (dataSourceConfig != null) {
            DataSourceConfig entity = service.save(dataSourceConfig);
            
            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 删除
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Integer delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = service.deleteById(id);
        }
        return code;
    }
}
