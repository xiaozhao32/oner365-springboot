package com.oner365.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.oner365.common.xss.XssHttpServletRequestWrapper;
import com.oner365.util.DataUtils;

/**
 * XSS 过滤器
 *
 * @author zhaoyong
 */
@Component
public class XssFilter implements Filter {

  private static final List<String> EXCLUDES_LIST = new ArrayList<>();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    String tempExcludes = filterConfig.getInitParameter("excludes");
    if (!DataUtils.isEmpty(tempExcludes)) {
      String[] url = tempExcludes.split(",");
      EXCLUDES_LIST.addAll(Arrays.asList(url));
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    if (handleExcludeUrl(req)) {
      chain.doFilter(request, response);
      return;
    }
    XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
    chain.doFilter(xssRequest, response);
  }

  private boolean handleExcludeUrl(HttpServletRequest request) {
    String url = request.getServletPath();
    String method = request.getMethod();
    // GET DELETE 不过滤
    if (method == null || method.matches(HttpMethod.GET.name()) || method.matches(HttpMethod.DELETE.name())) {
      return true;
    }
    return DataUtils.matches(url, EXCLUDES_LIST);
  }

}
