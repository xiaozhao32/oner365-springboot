package com.oner365.common.resolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.util.RequestUtils;

/**
 * 参数注解 CurrentUser
 *
 * @author zhaoyong
 */
@Order(2)
@Configuration
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentUser.class);
  }

  @Override
  public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
      @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    return RequestUtils.getAuthUser();
  }
}
