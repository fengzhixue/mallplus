package com.zscat.mallplus.sms.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 首页当前秒杀场次信息
 * https://github.com/shenzhuan/mallplus on 2019/1/28.
 */
@Getter
@Setter
public class HomeFlashPromotion {
    //属于该秒杀活动的商品
    List<SmsFlashSessionInfo> flashSessionInfoList;
    private Long id;
    private String startTime;
    private String endTime;
    private Date nextStartTime;
    private Date nextEndTime;
    private String flashName;
}
