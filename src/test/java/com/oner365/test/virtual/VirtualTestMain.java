package com.oner365.test.virtual;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

/**
 * 测试类
 * 
 * @author zhaoyong
 *
 */
public class VirtualTestMain {

  public static void main(String[] args) throws Exception {
    var uri = "http://localhost";
    var url = URI.create(uri).toURL();
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      var result = executor.submit(() -> fetchUrl(url));
      System.out.println(result);
    }
  }

  private static String fetchUrl(URL url) throws Exception {
    try (var in = url.openStream()) {
      String result = new String(in.readAllBytes(), StandardCharsets.UTF_8);
      System.out.println(result);
      return result;
    }
  }

}
