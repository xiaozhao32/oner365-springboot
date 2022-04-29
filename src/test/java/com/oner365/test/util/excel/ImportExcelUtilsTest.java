package com.oner365.test.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oner365.test.util.BaseUtilsTest;
import com.oner365.util.DataUtils;
import com.oner365.util.excel.ExcelData;
import com.oner365.util.excel.ImportExcelUtils;

/**
 * 工具类测试
 *
 * @author liutao
 *
 */
class ImportExcelUtilsTest extends BaseUtilsTest {

    @Test
    void test() throws IOException {
      File file  = new File(System.getProperty("user.dir")+"/src/test/java/com/oner365/test/util/excel/0408最新台账.xlsx");
      try (InputStream is = new FileInputStream(file);){
        ExcelData<BindingAppleDeviceDto> excelData = ImportExcelUtils.readExcel(is, 0, 1, DataUtils.getExtension(file.getName()), BindingAppleDeviceDto.class);
        logger.info("code:{},message:{}",excelData.getCode(),excelData.getMessage());
        excelData.getDataList().stream().forEach(dto -> logger.info("资产编号: {},用户名: {},部门: {},工号: {},序列号: {},",dto.getAssetsNo(), dto.getUserName(),
        dto.getDepartment(), dto.getJobNumber() , dto.getSerialNumber()));
      }catch(Exception e) {
          e.printStackTrace();
      }
    }
    @Test
    void testFilter() {
      List<String> list = new ArrayList<>();
      //添加测试数据
      list.add("test1");list.add("test2");list.add("test3");
      list.add("test4");list.add("test5");list.add("test6");
      list.add("test7");list.add("test8");list.add("test9");
      AtomicInteger index = new AtomicInteger(0);
      final List<Integer> indexList = Lists.newArrayList();
      list.stream()
          //指定匹配逻辑
          .filter(s -> {
              //每⽐对⼀个元素，数值加1
              index.getAndIncrement();
              if(s.equals("test3") || s.equals("test5") || s.equals("test7")) {
                indexList.add(index.intValue());
                return true;
              }
              return false;
          }).findFirst();
      indexList.stream().forEach(i -> logger.info("i:{}",i));
      logger.info("index:{}",index.get());
          
      List<String> newList = list.stream().filter(s -> s.equals("test19")).collect(Collectors.toList());
      logger.info("list size:{}",newList.size());
      logger.info("boolean:{}",DataUtils.isEmpty(new StringBuilder()));
      
 
    }
    
    @Test
    void testSet() {
      List<String> list = new ArrayList<>();
      //添加测试数据
      list.add("test1");list.add("test1");
      list.stream().filter(s -> s.equals("test1")).collect(Collectors.toList());
      final List<String> finalList = Lists.newArrayList();
      list.stream().forEach(s -> finalList.add(s));
      finalList.addAll(new ArrayList<String>());
      finalList.stream().forEach(s -> logger.info("s:{}",s));
      AtomicInteger index = new AtomicInteger(0);
      Map<String,String> map = new HashMap<String,String>();
      list.stream().forEach(s -> {
        logger.info("s string1:{}",s);
        index.getAndIncrement();
        getMap(list,s,index.intValue(),map);
        
      });
      logger.info("map size :{}",map.keySet().size());
      map.keySet().stream().forEach(key -> logger.info("map:{}",map.get(key)));
    }
    
   void getMap(List<String> list, String filed, int i, Map<String, String> map) {
     AtomicInteger index = new AtomicInteger(0);
     list.stream().forEach(s -> {
       index.getAndIncrement();
       if ((filed.equals(s) && index.intValue() != i) && (!map.containsKey(filed+"-"+index+"-"+i)
           && !map.containsKey(filed+"-"+i+"-"+index))) {
         logger.info("s string:{}",s);
         map.put(filed+"-"+index+"-"+i, filed+"字段第"+i+"与"+index);
       }
       
     });
      
    }
}
