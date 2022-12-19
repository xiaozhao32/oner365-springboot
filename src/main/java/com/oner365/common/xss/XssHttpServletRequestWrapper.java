package com.oner365.common.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.oner365.util.html.EscapeUtil;

import java.util.Arrays;

/**
 * XSS 过滤器
 *
 * @author zhaoyong
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

  /**
   * @param request HttpServletRequest
   */
  public XssHttpServletRequestWrapper(HttpServletRequest request) {
    super(request);
  }

  @Override
  public String[] getParameterValues(String name) {
    String[] values = super.getParameterValues(name);
    if (values != null) {
      // 防xss攻击和过滤前后空格
      return Arrays.stream(values).map(value -> EscapeUtil.clean(value).trim()).toArray(String[]::new);
    }
    return super.getParameterValues(name);
  }
}
