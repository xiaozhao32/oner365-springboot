package com.oner365.test.util;

import java.io.File;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.common.config.properties.DefaultFileProperties;
import com.oner365.util.FileSms4Utils;

/**
 * 工具类测试
 *
 * @author liutao
 *
 */
@SpringBootTest
class FileSms4UtilsTest extends BaseUtilsTest {

  @Resource
  private DefaultFileProperties properties;

  /**
   *  加密文件
   *  部分加密，默认加密文件1MB内容，小于1MB文件加密全文
   */
  @Test
  void encodePartTest() {
    String filePath = properties.getDownload();
    File encodeFile = new File(filePath + "/test.png");
    String encodeFilePath = filePath + "/encodePart-test.png";
    FileSms4Utils.encodePart(encodeFile, encodeFilePath);
    Assertions.assertNotNull(encodeFilePath);

    File encodePartFile = new File(filePath + "/encodePart-test.png");
    String decodeFilePath = filePath + "/decodePart-test.png";
    FileSms4Utils.decodePart(encodePartFile, decodeFilePath);
  }

  /**
   *  加密文件
   *  全文加密，默认加密文件1MB内容，小于1MB文件加密全文
   */
  @Test
  void encodeTest() {
    String filePath = properties.getDownload();
    File encodeFile = new File(filePath + "/test.png");
    String encodeFilePath = filePath + "/encode-test.png";
    FileSms4Utils.encode(encodeFile, encodeFilePath);
    Assertions.assertNotNull(encodeFilePath);

    File encodePartFile = new File(filePath + "/encode-test.png");
    String decodeFilePath = filePath + "/decode-test.png";
    FileSms4Utils.decode(encodePartFile, decodeFilePath);
  }

  /**
   *  加密文件
   *  部分加密，默认加密文件1MB内容，小于1MB文件加密全文
   */
  @Test
  void encodePartNoPlaceholderTest() {
    String filePath = properties.getDownload();
    File encodeFile = new File(filePath + "/test.png");
    String encodeFilePath = filePath + "/encodePartPlace-test.png";
    FileSms4Utils.encodePartNoPlaceholder(encodeFile, encodeFilePath);
    Assertions.assertNotNull(encodeFilePath);

    File encodePartFile = new File(filePath + "/encodePartPlace-test.png");
    String decodeFilePath = filePath + "/decodePartPlace-test.png";
    FileSms4Utils.decodePartNoPlaceholder(encodePartFile, decodeFilePath);
  }

}
