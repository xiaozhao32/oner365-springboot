package com.oner365.test.util;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.oner365.util.FileSMS4Utils;

/**
 * 工具类测试
 *
 * @author liutao
 *
 */
class FileSMS4UtilsTest extends BaseUtilsTest {

  /**
   *  加密文件
   *  部分加密，默认加密文件1MB内容，小于1MB文件加密全文
   */
  @Test
  void encodePartTest() {
    File file = new File("/Users/taoliu/Downloads/test.png");
    String encodeFilePath = "/Users/taoliu/Downloads/encodePart-test.png";
    FileSMS4Utils.encodePart(file,encodeFilePath);
  }
  
  /**
   *  解密文件
   *  部分解密，默认解密文件1MB内容，小于1MB文件解密全文
   */
  @Test
  void decodePartTest() {
    File file = new File("/Users/taoliu/Downloads/encodePart-test.png");
    String encodeFilePath = "/Users/taoliu/Downloads/decodePart-test.png";
    FileSMS4Utils.decodePart(file, encodeFilePath);
  }
  
  /**
   *  加密文件
   *  全文加密，默认加密文件1MB内容，小于1MB文件加密全文
   */
  @Test
  void encodeTest() {
    File file = new File("/Users/taoliu/Downloads/test.png");
    String encodeFilePath = "/Users/taoliu/Downloads/encode-test.png";
    FileSMS4Utils.encode(file,encodeFilePath);
  }
  
  /**
   *  解密文件
   *  全文解密，默认解密文件1MB内容，小于1MB文件解密全文
   */
  @Test
  void decodeTest() {
    File file = new File("/Users/taoliu/Downloads/encode-test.png");
    String encodeFilePath = "/Users/taoliu/Downloads/decode-test.png";
    FileSMS4Utils.decode(file, encodeFilePath);
  }
  
}
