package com.oner365.test.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

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
        ExcelData<BindingAppleDeviceDto> excelData = ImportExcelUtils.readExcel(is, 1, 1, DataUtils.getExtension(file.getName()), BindingAppleDeviceDto.class);
        excelData.getDataList().stream().forEach(dto -> logger.info("资产编号: {},用户名: {},部门: {},工号: {},序列号: {},",dto.getAssetsNo(), dto.getUserName(),
        dto.getDepartment(), dto.getJobNumber() , dto.getSerialNumber()));
      }catch(Exception e) {
          e.printStackTrace();
      }
    }
}
