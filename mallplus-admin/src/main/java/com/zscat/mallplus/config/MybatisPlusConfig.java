package com.zscat.mallplus.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.zscat.mallplus.ApiContext;
import com.zscat.mallplus.enums.ConstansValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

//Spring boot方式
@EnableTransactionManagement
@Configuration
@MapperScan("com.zscat.mallplus.*.mapper*")
public class MybatisPlusConfig {
    private static final List<String> IGNORE_TENANT_TABLES = ConstansValue.IGNORE_TENANT_TABLES;
    @Autowired
    private ApiContext apiContext;

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        paginationInterceptor.setDialectType("mysql");

        return paginationInterceptor;
    }


    /**
     * 性能分析拦截器，不建议生产使用
     * 用来观察 SQL 执行情况及执行时长
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
}
