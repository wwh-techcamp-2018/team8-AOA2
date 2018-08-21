package com.aoa.springwebservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/").setViewName("header");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/result/success").setViewName("registMenuSuccess");
        registry.addViewController("/owner/menu").setViewName("displayMenu");
        registry.addViewController("/client").setViewName("client");
    }
}
