package com.aoa.springwebservice;

import com.aoa.springwebservice.security.LoginUserHandlerMethodArgumentResolver;
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
        registry.addViewController("/").setViewName("signin");
        //registry.addViewController("/owner/stores/form").setViewName("registStore");
        //registry.addViewController("/owner/menus/form").setViewName("registMenu");
        //registry.addViewController("/owner/menus").setViewName("displayMenu");
        //registry.addViewController("/owner/reservations/form").setViewName("openReservation");
        //registry.addViewController("/stores/{storeId}/orders/form").setViewName("client");
        registry.addViewController("/result/success").setViewName("registMenuSuccess");
        //registry.addViewController("/owner/menu").setViewName("displayMenu");
       // registry.addViewController("/owner/menu/form").setViewName("registMenu");
        registry.addViewController("/admin/store/fail").setViewName("fail");

    }

    @Bean
    public LoginUserHandlerMethodArgumentResolver loginUserArgumentResolver() {
        return new LoginUserHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver());
    }

}
