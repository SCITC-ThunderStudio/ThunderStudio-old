package com.scitc.nuyoahz.thunderstudiobackendspringboot.config;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 静态资源映射
 *
 * @author Nuyoahz
 * @date 2023/7/27 19:40
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 打卡图片路径
        registry.addResourceHandler(SystemConstant.IMG_NETWORK_PATH + "**").addResourceLocations(SystemConstant.IMG_MAPPING_PATH);
        // 周报路径
        registry.addResourceHandler(SystemConstant.WEEKLY_NETWORK_PATH + "**").addResourceLocations(SystemConstant.WEEKLY_MAPPING_PATH);
        // 头像路径
        registry.addResourceHandler(SystemConstant.AVATAR_NETWORK_PATH + "**").addResourceLocations(SystemConstant.AVATAR_MAPPING_PATH);
        super.addResourceHandlers(registry);
    }
}