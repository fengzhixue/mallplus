/*
 * Copyright (c) 2018. 951449465 All Rights Reserved.
 * 项目名称：mallcloud快速搭建企业级分布式微服务平台
 * 类名称：MdcWebMvcConfig.java
 * 创建人：mallcloud
 * 联系方式：951449465@gmail.com
 * 开源地址: https://github.com/mallcloud
 * 博客地址: http://blog.951449465
 * 项目官网: http://951449465
 */

package com.zscat.mallplus.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * The class Mdc web mvc config.
 *
 * @author 951449465 @gmail.com
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
