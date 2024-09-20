package com.scitc.nuyoahz.thunderstudiobackendspringboot.config;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.filter.GlobalFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author Nuyoahz
 * @date 2024/1/11 16:30
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<GlobalFilter> globalFilter() {
        FilterRegistrationBean<GlobalFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new GlobalFilter());
        // 过滤路径
        registration.addUrlPatterns("/public/*");
        registration.addUrlPatterns("/user/*");
        registration.addUrlPatterns("/everyMan/*");
        registration.addUrlPatterns("/admin/*");
        // 过滤器名称
        registration.setName("GlobalFilter");
        registration.setOrder(1);
        return registration;
    }
}
