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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysDictItem;
import com.oner365.sys.entity.SysDictItemType;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.sys.vo.SysDictItemTypeVo;
import com.oner365.sys.vo.SysDictItemVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 字典信息
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
     * @return Map<String, Object>
     */
    @PutMapping("/saveDictItemType")
    @ApiOperation("字典类别保存")
    public Map<String, Object> saveDictItemType(@RequestBody SysDictItemTypeVo sysDictItemTypeVo) {
        SysDictItemType sysDictItemType = sysDictItemTypeVo.toObject();
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysDictItemType != null) {
            SysDictItemType entity = sysDictItemTypeService.save(sysDictItemType);
            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 判断类别id 是否存在
     *
     * @param paramJson 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkTypeId")
    @ApiOperation("判断列表是否存在")
    public Map<String, Object> checkTypeId(@RequestBody JSONObject paramJson) {
        String id = paramJson.getString(SysConstants.ID);
        String code = paramJson.getString(PublicConstants.CODE);
        
        int check = sysDictItemTypeService.checkTypeId(id, code);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, check);
        return result;
    }

    /**
     * 获取类别
     *
     * @param id 编号
     * @return SysDictItemType
     */
    @GetMapping("/getTypeById/{id}")
    @ApiOperation("按id查询类别")
    public SysDictItemType getTypeById(@PathVariable String id) {
        return sysDictItemTypeService.getById(id);
    }

    /**
     * 获取类别中字典列表
     *
     * @param typeId 类型id
     * @return List<SysDictItem>
     */
    @GetMapping("/findTypeInfoById/{typeId}")
    @ApiOperation("按类别id查询列表")
    public List<SysDictItem> findTypeInfoById(@PathVariable String typeId) {
        JSONObject paramJson = new JSONObject();
        paramJson.put(SysConstants.TYPE_ID, typeId);
        return sysDictItemService.findList(paramJson);
    }

    /**
     * 获取类别字典信息
     *
     * @param paramJson 字典参数
     * @return Map<String, Object>
     */
    @PostMapping("/findTypeInfoByCodes")
    @ApiOperation("按类别编码查询字典列表")
    public Map<String, Object> findTypeInfoByCodes(@RequestBody JSONObject paramJson) {
        JSONArray array = paramJson.getJSONArray("codes");
        Map<String, Object> result = Maps.newHashMap();
        array.forEach(obj -> {
            JSONObject json = new JSONObject();
            json.put(SysConstants.TYPE_ID, obj);
            List<SysDictItem> itemList = sysDictItemService.findList(json);
            result.put((String)obj, itemList);
        });
        return result;
    }

    /**
     * 获取类别列表
     *
     * @param paramJson 参数
     * @return Page<SysDictItemType>
     */
    @PostMapping("/findTypeList")
    @ApiOperation("获取类别列表")
    public Page<SysDictItemType> findTypeList(@RequestBody JSONObject paramJson) {
        return sysDictItemTypeService.pageList(paramJson);
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
     * @param paramJson 参数
     * @return Page<SysDictItem>
     */
    @PostMapping("/findItemList")
    @ApiOperation("获取字典列表")
    public Page<SysDictItem> findItemList(@RequestBody JSONObject paramJson) {
        return sysDictItemService.pageList(paramJson);
    }

    /**
     * 保存字典信息
     *
     * @param sysDictItemVo 字典对象
     * @return Map<String, Object>
     */
    @PutMapping("/saveDictItem")
    @ApiOperation("保存字典")
    public Map<String, Object> saveDictItem(@RequestBody SysDictItemVo sysDictItemVo) {
        SysDictItem sysDictItem = sysDictItemVo.toObject();
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysDictItem != null) {
            SysDictItem entity = sysDictItemService.save(sysDictItem);

            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 获取字典信息
     *
     * @param id 字典编号
     * @return SysDictItem
     */
    @GetMapping("/getItemById/{id}")
    @ApiOperation("按id查询字典")
    public SysDictItem getItemById(@PathVariable String id) {
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
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/findListByCodes")
    @ApiOperation("获取类别列表")
    public Map<String, Object> findListByCode(@RequestBody JSONObject json) {
        List<SysDictItemType> list = sysDictItemTypeService.findListByCodes(json);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.PARAM_LIST, list);
        return result;
    }

    /**
     * 导出字典类型Excel
     * @param paramJson 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/exportItemType")
    @ApiOperation("导出字典类别")
    public ResponseEntity<byte[]> exportItemType(@RequestBody JSONObject paramJson){
        List<SysDictItemType> list = sysDictItemTypeService.findList(paramJson);

        String[] titleKeys = new String[]{"编号","类型名称","类型标识","描述","排序","状态"};
        String[] columnNames = {"id","typeName","typeCode","typeDes","typeOrder","status"};

        String fileName = SysDictItemType.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

    /**
     * 导出字典Excel
     * @param paramJson 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/exportItem")
    @ApiOperation("导出字典")
    public ResponseEntity<byte[]> exportItem(@RequestBody JSONObject paramJson){
        List<SysDictItem> list = sysDictItemService.findList(paramJson);

        String[] titleKeys = new String[]{"编号","类型标识","字典名称","字典标识","排序","状态"};
        String[] columnNames = {"id","typeId","itemName","itemCode","itemOrder","status"};

        String fileName = SysDictItem.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }
}
