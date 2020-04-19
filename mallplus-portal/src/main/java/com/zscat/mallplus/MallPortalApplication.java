package com.zscat.mallplus;


import com.zscat.mallplus.pms.mapper.PmsProductMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan({"com.zscat.mallplus.mapper", "com.zscat.mallplus.ums.mapper", "com.zscat.mallplus.sms.mapper", "com.zscat.mallplus.cms.mapper", "com.zscat.mallplus.sys.mapper", "com.zscat.mallplus.oms.mapper", "com.zscat.mallplus.pms.mapper"})
@EnableTransactionManagement
@EnableScheduling
public class MallPortalApplication {

    @Resource
    PmsProductMapper mapper;

    public static void main(String[] args) {
        System.out.println("start-------------");
        SpringApplication.run(MallPortalApplication.class, args);
        System.out.println("end-------------");
    }


}
