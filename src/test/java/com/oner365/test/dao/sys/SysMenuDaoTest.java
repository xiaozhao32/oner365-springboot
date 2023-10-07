package com.oner365.test.dao.sys;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.oner365.sys.dao.ISysMenuDao;
import com.oner365.sys.dto.SysUserDto;
import com.oner365.sys.entity.SysMenu;
import com.oner365.test.dao.BaseDaoTest;
import com.oner365.util.excel.ExportExcelUtils;

/**
 * Test SysMenuDao
 *
 * @author zhaoyong
 *
 */
@SpringBootTest
class SysMenuDaoTest extends BaseDaoTest {

  @Resource
  private ISysMenuDao dao;

  @Resource
  private JdbcTemplate jdbcTemplate;

  @Test
  void findMenuByTypeCode() {
    String typeCode = "nt_sys";
    List<SysMenu> result = dao.findMenuByTypeCode(typeCode);
    logger.info("result:{}", result.size());
    Assertions.assertNotEquals(0, result.size());

    String sql = "select m.* from nt_sys_menu m, nt_sys_menu_type t where m.menu_type_id=t.id and m.status=1 and t.type_code=?";
    List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, typeCode);
    logger.info("result:{}", resultList.size());
    Assertions.assertNotEquals(0, resultList.size());
  }
  
  @Test
  void testExportPage() {
    // 分页信息
    int size = 10000;
    int total = countSysUser();
    int page = total / size + 1;
    
    // 文件信息
    String filePath = System.getProperty("user.dir") + "/src/test/java/com/oner365/test/dao/sys/SysUser.xlsx";
    logger.info("file path: {}/{}", filePath);
    String[] titleKeys = new String[] { "编号", "用户标识", "用户名称", "姓名", "性别", "邮箱", "电话", "备注", "状态", "创建时间", "最后登录时间",
        "最后登录ip" };
    String[] columnNames = { "id", "userCode", "userName", "realName", "sex", "email", "phone", "remark", "status",
        "createTime", "lastTime", "lastIp" };

    // 导出文件
    try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(filePath)) {
      for (int i = 1; i <= page; i++) {
        Page<SysUserDto> dto = pageSysUser(total, i, size);
        logger.info("page: {}/{}", i, total);
        ExportExcelUtils.export(workbook, "sheet " + i, titleKeys, columnNames, dto.getContent());
      }
      workbook.write(fos);
      fos.flush();
    } catch (Exception e) {
      logger.error("error:", e);
    }

  }
  
  public int countSysUser() {
    return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM nt_sys_user", Integer.class);
  }

  public Page<SysUserDto> pageSysUser(int count, int page, int size) {
    int start = (page - 1) * size;
    List<SysUserDto> list = jdbcTemplate.query("SELECT * FROM nt_sys_user LIMIT ? OFFSET ?",
        BeanPropertyRowMapper.newInstance(SysUserDto.class), size, start);
    return new PageImpl<>(list, PageRequest.of(page, size), count);
  }

}
