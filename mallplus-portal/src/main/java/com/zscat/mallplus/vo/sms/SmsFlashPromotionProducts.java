package com.zscat.mallplus.vo.sms;


import com.zscat.mallplus.sms.vo.HomeProductAttr;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 秒杀场次信息和商品
 */
@Data
public class SmsFlashPromotionProducts implements Serializable {
    private BigDecimal flashPromotionPrice;
    private Integer flashPromotionCount;
    private Integer flashPromotionLimit;
    private Integer sort;
    private HomeProductAttr product;
}
