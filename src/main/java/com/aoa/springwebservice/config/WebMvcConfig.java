package com.aoa.springwebservice.config;

import com.aoa.springwebservice.security.LoginUserHandlerMethodArgumentResolver;
import com.aoa.springwebservice.security.StoreHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/result/success").setViewName("registMenuSuccess");
        registry.addViewController("/admin/store/fail").setViewName("fail");
    }

    @Bean
    public LoginUserHandlerMethodArgumentResolver loginUserArgumentResolver() {
        return new LoginUserHandlerMethodArgumentResolver();
    }
    @Bean
    public StoreHandlerMethodArgumentResolver storeHandlerMethodArgumentResolver(){
        return new StoreHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver());
        argumentResolvers.add(storeHandlerMethodArgumentResolver());
    }
}
