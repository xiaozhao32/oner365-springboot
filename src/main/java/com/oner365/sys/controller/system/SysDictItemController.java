package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.SysDictItemDto;
import com.oner365.sys.dto.SysDictItemTypeDto;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.sys.vo.SysDictItemTypeVo;
import com.oner365.sys.vo.SysDictItemVo;
import com.oner365.sys.vo.check.CheckCodeVo;
import com.oner365.sys.vo.check.CheckTypeCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 字典信息
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/dict")
@Api(tags = "系统管理 - 字典信息")
public class SysDictItemController extends BaseController {

  @Autowired
  private ISysDictItemTypeService sysDictItemTypeService;

  @Autowired
  private ISysDictItemService sysDictItemService;

  /**
   * 字典类别保存
   *
   * @param sysDictItemTypeVo 字典类别对象
   * @return ResponseResult<SysDictItemTypeDto>
   */
  @PutMapping("/saveDictItemType")
  @ApiOperation("字典类别保存")
  public ResponseResult<SysDictItemTypeDto> saveDictItemType(@RequestBody SysDictItemTypeVo sysDictItemTypeVo) {
    if (sysDictItemTypeVo != null) {
      SysDictItemTypeDto entity = sysDictItemTypeService.save(sysDictItemTypeVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 判断类别id 是否存在
   *
   * @param checkCodeVo 查询参数
   * @return Long
   */
  @PostMapping("/checkTypeCode")
  @ApiOperation("判断字典类别是否存在")
  public Long checkTypeCode(@RequestBody CheckCodeVo checkCodeVo) {
    if (checkCodeVo != null) {
      return sysDictItemTypeService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 判断类别id 是否存在
   *
   * @param checkTypeCodeVo 查询参数
   * @return Long
   */
  @PostMapping("/checkCode")
  @ApiOperation("判断字典是否存在")
  public Long checkCode(@RequestBody CheckTypeCodeVo checkTypeCodeVo) {
    if (checkTypeCodeVo != null) {
      return sysDictItemService.checkCode(checkTypeCodeVo.getId(), checkTypeCodeVo.getTypeId(),
          checkTypeCodeVo.getCode());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 获取类别
   *
   * @param id 编号
   * @return SysDictItemTypeDto
   */
  @GetMapping("/getTypeById/{id}")
  @ApiOperation("按id查询类别")
  public SysDictItemTypeDto getTypeById(@PathVariable String id) {
    return sysDictItemTypeService.getById(id);
  }

  /**
   * 获取类别中字典列表
   *
   * @param typeId 类型id
   * @return List<SysDictItemDto>
   */
  @GetMapping("/findTypeInfoById/{typeId}")
  @ApiOperation("按类别id查询列表")
  public List<SysDictItemDto> findTypeInfoById(@PathVariable String typeId) {
    QueryCriteriaBean data = new QueryCriteriaBean();
    List<AttributeBean> whereList = new ArrayList<>();
    AttributeBean attribute = new AttributeBean(SysConstants.TYPE_ID, typeId);
    whereList.add(attribute);
    data.setWhereList(whereList);
    return sysDictItemService.findList(data);
  }

  /**
   * 获取类别字典信息
   *
   * @param typeIds 字典参数
   * @return Map<String, Object>
   */
  @PostMapping("/findItemByTypeIds")
  @ApiOperation("按类别编码查询字典列表")
  public Map<String, Object> findItemByTypeIds(@RequestBody String... typeIds) {
    Map<String, Object> result = Maps.newHashMap();
    Arrays.stream(typeIds).forEach(typeId -> {
      QueryCriteriaBean data = new QueryCriteriaBean();
      List<AttributeBean> whereList = new ArrayList<>();
      AttributeBean attribute = new AttributeBean(SysConstants.TYPE_ID, typeId);
      whereList.add(attribute);
      data.setWhereList(whereList);
      List<SysDictItemDto> itemList = sysDictItemService.findList(data);
      result.put(typeId, itemList);
    });
    return result;
  }

  /**
   * 获取类别列表
   *
   * @param data 查询参数
   * @return Page<SysDictItemTypeDto>
   */
  @PostMapping("/findTypeList")
  @ApiOperation("获取类别列表")
  public Page<SysDictItemTypeDto> findTypeList(@RequestBody QueryCriteriaBean data) {
    return sysDictItemTypeService.pageList(data);
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/editTypeStatus/{id}")
  @ApiOperation("修改类别状态")
  public Integer editTypeStatus(@PathVariable String id, @RequestParam("status") String status) {
    return sysDictItemTypeService.editStatus(id, status);
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/editItemStatus/{id}")
  @ApiOperation("修改字典状态")
  public Integer editItemStatus(@PathVariable String id, @RequestParam("status") String status) {
    return sysDictItemService.editStatus(id, status);
  }

  /**
   * 获取字典列表
   *
   * @param data 查询参数
   * @return Page<SysDictItemDto>
   */
  @PostMapping("/findItemList")
  @ApiOperation("获取字典列表")
  public Page<SysDictItemDto> findItemList(@RequestBody QueryCriteriaBean data) {
    return sysDictItemService.pageList(data);
  }

  /**
   * 保存字典信息
   *
   * @param sysDictItemVo 字典对象
   * @return ResponseResult<SysDictItemDto>
   */
  @PutMapping("/saveDictItem")
  @ApiOperation("保存字典")
  public ResponseResult<SysDictItemDto> saveDictItem(@RequestBody SysDictItemVo sysDictItemVo) {
    if (sysDictItemVo != null) {
      SysDictItemDto entity = sysDictItemService.save(sysDictItemVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 获取字典信息
   *
   * @param id 字典编号
   * @return SysDictItemDto
   */
  @GetMapping("/getItemById/{id}")
  @ApiOperation("按id查询字典")
  public SysDictItemDto getItemById(@PathVariable String id) {
    return sysDictItemService.getById(id);
  }

  /**
   * 删除字典信息
   *
   * @param ids 编号
   * @return Integer
   */
  @DeleteMapping("/deleteItem")
  @ApiOperation("删除字典")
  public Integer deleteItem(@RequestBody String... ids) {
    int code = 0;
    for (String id : ids) {
      code = sysDictItemService.deleteById(id);
    }
    return code;
  }

  /**
   * 删除字典类别
   *
   * @param ids 字典编号
   * @return Integer
   */
  @DeleteMapping("/deleteItemType")
  @ApiOperation("删除字典类别")
  public Integer deleteItemType(@RequestBody String... ids) {
    int code = 0;
    for (String id : ids) {
      code = sysDictItemTypeService.deleteById(id);
    }
    return code;
  }

  /**
   * 获取类别列表
   *
   * @param codes 参数
   * @return List<SysDictItemType>
   */
  @PostMapping("/findListByCodes")
  @ApiOperation("获取类别列表")
  public List<SysDictItemTypeDto> findListByCode(@RequestBody String... codes) {
    return sysDictItemTypeService.findListByCodes(Arrays.asList(codes));
  }

  /**
   * 导出字典类型Excel
   * 
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/exportItemType")
  @ApiOperation("导出字典类别")
  public ResponseEntity<byte[]> exportItemType(@RequestBody QueryCriteriaBean data) {
    List<SysDictItemTypeDto> list = sysDictItemTypeService.findList(data);

    String[] titleKeys = new String[] { "编号", "类型名称", "类型标识", "描述", "排序", "状态" };
    String[] columnNames = { "id", "typeName", "typeCode", "typeDes", "typeOrder", "status" };

    String fileName = SysDictItemTypeDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

  /**
   * 导出字典Excel
   * 
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/exportItem")
  @ApiOperation("导出字典")
  public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
    List<SysDictItemDto> list = sysDictItemService.findList(data);

    String[] titleKeys = new String[] { "编号", "类型标识", "字典名称", "字典标识", "排序", "状态" };
    String[] columnNames = { "id", "typeId", "itemName", "itemCode", "itemOrder", "status" };

    String fileName = SysDictItemDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }
}
