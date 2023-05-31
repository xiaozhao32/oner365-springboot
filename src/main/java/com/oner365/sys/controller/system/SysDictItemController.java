package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
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
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
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
@Api(tags = "系统管理 - 字典信息")
@RequestMapping("/system/dict")
public class SysDictItemController extends BaseController {

  @Resource
  private ISysDictItemTypeService sysDictItemTypeService;

  @Resource
  private ISysDictItemService sysDictItemService;

  /**
   * 获取类别列表
   *
   * @param data 查询参数
   * @return PageInfo<SysDictItemTypeDto>
   */
  @ApiOperation("1.获取类别列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/type/list")
  public PageInfo<SysDictItemTypeDto> pageTypeList(@RequestBody QueryCriteriaBean data) {
    return sysDictItemTypeService.pageList(data);
  }

  /**
   * 获取类别列表
   *
   * @param codes 参数
   * @return List<SysDictItemTypeDto>
   */
  @ApiOperation("2.获取类别列表")
  @ApiOperationSupport(order = 2)
  @PostMapping("/type/codes/list")
  public List<SysDictItemTypeDto> findListByCode(@RequestBody String... codes) {
    return sysDictItemTypeService.findListByCodes(Arrays.asList(codes));
  }

  /**
   * 获取类别
   *
   * @param id 编号
   * @return SysDictItemTypeDto
   */
  @ApiOperation("3.按id查询类别")
  @ApiOperationSupport(order = 3)
  @GetMapping("/type/get/{id}")
  public SysDictItemTypeDto getTypeById(@PathVariable String id) {
    return sysDictItemTypeService.getById(id);
  }

  /**
   * 判断类别id 类别是否存在
   *
   * @param checkCodeVo 查询参数
   * @return Boolean
   */
  @ApiOperation("4.判断字典类别是否存在")
  @ApiOperationSupport(order = 4)
  @PostMapping("/type/check")
  public Boolean checkTypeCode(@Validated @RequestBody CheckCodeVo checkCodeVo) {
    if (checkCodeVo != null) {
      return sysDictItemTypeService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
    }
    return Boolean.FALSE;
  }

  /**
   * 获取类别中字典列表
   *
   * @param typeId 类型id
   * @return List<SysDictItemDto>
   */
  @ApiOperation("5.按类别id查询列表")
  @ApiOperationSupport(order = 5)
  @GetMapping("/item/type/{typeId}")
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
  @ApiOperation("6.按类别编码查询字典列表")
  @ApiOperationSupport(order = 6)
  @PostMapping("/item/type/ids")
  public Map<String, Object> findItemByTypeIds(@RequestBody String... typeIds) {
    Map<String, Object> result = new HashMap<>(10);
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
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  @ApiOperation("7.修改类别状态")
  @ApiOperationSupport(order = 7)
  @PostMapping("/type/status/{id}")
  public Boolean editTypeStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysDictItemTypeService.editStatus(id, status);
  }

  /**
   * 字典类别保存
   *
   * @param sysDictItemTypeVo 字典类别对象
   * @return ResponseResult<SysDictItemTypeDto>
   */
  @ApiOperation("8.字典类别保存")
  @ApiOperationSupport(order = 8)
  @PutMapping("/type/save")
  public ResponseResult<SysDictItemTypeDto> saveDictItemType(@Validated @RequestBody SysDictItemTypeVo sysDictItemTypeVo) {
    if (sysDictItemTypeVo != null) {
      SysDictItemTypeDto entity = sysDictItemTypeService.save(sysDictItemTypeVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除字典类别
   *
   * @param ids 字典编号
   * @return List<Boolean>
   */
  @ApiOperation("9.删除字典类别")
  @ApiOperationSupport(order = 9)
  @DeleteMapping("/type/delete")
  public List<Boolean> deleteItemType(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysDictItemTypeService.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * 导出字典类型Excel
   *
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("10.导出字典类别")
  @ApiOperationSupport(order = 10)
  @PostMapping("/type/export")
  public ResponseEntity<byte[]> exportItemType(@RequestBody QueryCriteriaBean data) {
    List<SysDictItemTypeDto> list = sysDictItemTypeService.findList(data);

    String[] titleKeys = new String[] { "编号", "类型名称", "类型标识", "描述", "排序", "状态" };
    String[] columnNames = { "id", "typeName", "typeCode", "typeDes", "typeOrder", "status" };

    String fileName = SysDictItemTypeDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

  /**
   * 获取字典列表
   *
   * @param data 查询参数
   * @return PageInfo<SysDictItemDto>
   */
  @ApiOperation("11.获取字典列表")
  @ApiOperationSupport(order = 11)
  @PostMapping("/item/list")
  public PageInfo<SysDictItemDto> pageItemList(@RequestBody QueryCriteriaBean data) {
    return sysDictItemService.pageList(data);
  }

  /**
   * 获取字典信息
   *
   * @param id 字典编号
   * @return SysDictItemDto
   */
  @ApiOperation("12.按id查询字典")
  @ApiOperationSupport(order = 12)
  @GetMapping("/item/get/{id}")
  public SysDictItemDto getItemById(@PathVariable String id) {
    return sysDictItemService.getById(id);
  }

  /**
   * 判断类别id 字典是否存在
   *
   * @param checkTypeCodeVo 查询参数
   * @return Boolean
   */
  @ApiOperation("13.判断字典是否存在")
  @ApiOperationSupport(order = 13)
  @PostMapping("/item/check")
  public Boolean checkCode(@Validated @RequestBody CheckTypeCodeVo checkTypeCodeVo) {
    if (checkTypeCodeVo != null) {
      return sysDictItemService.checkCode(checkTypeCodeVo.getId(), checkTypeCodeVo.getTypeId(),
          checkTypeCodeVo.getCode());
    }
    return Boolean.FALSE;
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  @ApiOperation("14.修改字典状态")
  @ApiOperationSupport(order = 14)
  @PostMapping("/item/status/{id}")
  public Boolean editItemStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysDictItemService.editStatus(id, status);
  }

  /**
   * 保存字典信息
   *
   * @param sysDictItemVo 字典对象
   * @return ResponseResult<SysDictItemDto>
   */
  @ApiOperation("15.保存字典")
  @ApiOperationSupport(order = 15)
  @PutMapping("/item/save")
  public ResponseResult<SysDictItemDto> saveDictItem(@Validated @RequestBody SysDictItemVo sysDictItemVo) {
    if (sysDictItemVo != null) {
      SysDictItemDto entity = sysDictItemService.save(sysDictItemVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除字典信息
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @ApiOperation("16.删除字典")
  @ApiOperationSupport(order = 16)
  @DeleteMapping("/item/delete")
  public List<Boolean> deleteItem(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysDictItemService.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * 导出字典Excel
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("17.导出字典")
  @ApiOperationSupport(order = 17)
  @PostMapping("/item/export")
  public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
    List<SysDictItemDto> list = sysDictItemService.findList(data);

    String[] titleKeys = new String[] { "编号", "类型标识", "字典名称", "字典标识", "排序", "状态" };
    String[] columnNames = { "id", "typeId", "itemName", "itemCode", "itemOrder", "status" };

    String fileName = SysDictItemDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }
}
