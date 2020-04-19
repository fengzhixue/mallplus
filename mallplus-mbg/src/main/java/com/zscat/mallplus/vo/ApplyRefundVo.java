package com.zscat.mallplus.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ApplyRefundVo {
    private Long itemId;
    private String items;
    private Integer type;
    private String desc;
    private String[] images;
    BigDecimal returnAmount;
}
