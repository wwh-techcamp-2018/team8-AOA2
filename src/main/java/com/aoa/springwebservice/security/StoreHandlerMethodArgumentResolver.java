package com.aoa.springwebservice.security;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import com.aoa.springwebservice.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Slf4j
public class StoreHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private StoreService storeService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Store.class.isAssignableFrom(parameter.getParameterType())
                && parameter.hasParameterAnnotation(AuthorizedStore.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.debug("resolveArgument ");

        Map<String,String> pathVariables = getPathVariables(webRequest);
        log.debug("pathVariables  {} ", pathVariables);
        long storeId = Long.parseLong(pathVariables.get("storeId"));
        log.debug("storeId {}", storeId);


        AuthorizedStore authorizedStore = parameter.getParameterAnnotation(AuthorizedStore.class);
        if (!authorizedStore.required()) {
            return storeService.getStoreById(storeId);
        }

        Store store = storeService.getStoreById(storeId);
        User user = HttpSessionUtils.getUserFromSession(webRequest);

        if(user == null || !store.hasSameOwner(user))
            throw new RuntimeException("권한이 없는 유저가 가게에 접근하였습니다");
        log.debug("store isOpen {}", store.isOpen());
        if(authorizedStore.notClosed() && !store.isOpen())
            throw new RuntimeException("진행 중인 예약이 없습니다");
        if(authorizedStore.notOpen() && store.isOpen())
            throw new RuntimeException("이미 진행 중인 예약이 있습니다");
        return store;
    }

    private Map<String, String> getPathVariables(NativeWebRequest webRequest) {
        log.debug("getPathVariables ");
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        return (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }

}
