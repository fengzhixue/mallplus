package com.zscat.mallplus.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by mallplus on 2019/4/8.
 */
@Configuration
@MapperScan({"com.zscat.mallplus.search.mapper", "com.zscat.mallplus.search.dao"})
public class MyBatisConfig {
}
