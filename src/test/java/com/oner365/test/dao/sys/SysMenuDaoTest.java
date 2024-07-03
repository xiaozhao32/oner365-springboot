package com.oner365.test.dao.sys;

import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import jakarta.annotation.Resource;

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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.alibaba.fastjson.JSON;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.commons.util.excel.ExportExcelUtils;
import com.oner365.sys.dao.ISysMenuDao;
import com.oner365.sys.dto.SysUserDto;
import com.oner365.sys.entity.SysMenu;
import com.oner365.test.dao.BaseDaoTest;

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
  void testGetById() {
    SysUserDto result = jdbcTemplate.queryForObject("SELECT * FROM nt_sys_user where id='1' ",
        BeanPropertyRowMapper.newInstance(SysUserDto.class));
    Assertions.assertNotNull(result);
    logger.info("result:{}", JSON.toJSONString(result));
  }

//  @Test
  void testInsert() {
    String sql = "insert into nt_test_date(name, phone, price, description, test_date, status, create_time, update_time) values(?,?,?,?,?,?,?,?)";
    String result = insertAutoIncrementId(sql,
        new Object[] { "test", 999, 1.2d, "abc", new Date(), 1, DateUtil.getCurrentTime(), DateUtil.getCurrentTime() });
    logger.info("result:{}", JSON.toJSONString(result));
  }

  public String insertAutoIncrementId(String sql, Object[] params) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      IntStream.range(0, params.length).forEach( i -> {
        try {
          ps.setObject(i+1, params[i]);
        } catch (SQLException e) {
          logger.error("insertAutoIncrementId error", e);
        }
      });
      return ps;
    }, keyHolder);
    return keyHolder.getKey().toString();
  }

//  @Test
  void testExportPage() {
    // 分页信息
    int size = 10000;
    int total = countSysUser();
    int page = total / size + 1;

    // 文件信息
    String filePath = System.getProperty("user.dir") + "/src/test/java/com/oner365/test/dao/sys/SysUser.xlsx";
    logger.info("file path: {}", filePath);
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
