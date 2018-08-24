package com.aoa.springwebservice.security;

import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User user = HttpSessionUtils.getUserFromSession(webRequest);

        if(Optional.ofNullable(user).isPresent()) {
            return user;
        }

        LoginUser loginUser = parameter.getParameterAnnotation(LoginUser.class);
        if (loginUser.required()) {
            throw new UnAuthorizedException("You're required Login!");
        }
        return user;
    }
}