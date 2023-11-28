package com.oner365.common.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

/**
 * 请求跨域处理
 * 
 * nginx 需要处理
 * proxy_set_header Access-Control-Allow-Origin *; 
 * proxy_set_header Access-Control-Allow-Methods 'POST,GET,OPTIONS,DELETE,PUT';
 * 
 * @author zhaoyong
 *
 */
@Order(1)
@WebFilter(filterName = "corsFilter", urlPatterns = { "/*" })
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,DELETE,PUT");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type,Authorization");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
