package com.oner365.data.web.resolver;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.oner365.data.commons.auth.annotation.CurrentUser;
import com.oner365.data.web.utils.RequestUtils;

/**
 * 注册用户信息
 *
 * @author zhaoyong
 */
@Order(2)
@Configuration
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public @NonNull Object resolveArgument(@NonNull MethodParameter parameter,
            @NonNull ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest,
            @NonNull WebDataBinderFactory binderFactory) {
        return RequestUtils.getAuthUser();
    }

}
