package com.zscat.mallplus.sms.vo;

import lombok.Data;

import java.util.List;

@Data
public class SmsFlashSessionInfo {
    private Long id;
    private String name;
    private String startTime;
    private String endTime;
    //属于该秒杀活动的商品
    private List<HomeProductAttr> productList;
}
