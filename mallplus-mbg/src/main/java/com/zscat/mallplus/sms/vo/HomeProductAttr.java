package com.zscat.mallplus.sms.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HomeProductAttr {
    private Long id;
    private Long productId;
    private String productImg;
    private String productName;
    private BigDecimal productPrice;
    /**
     * 限时购价格
     */
    @TableField("flash_promotion_price")
    private BigDecimal flashPromotionPrice;

    /**
     * 限时购数量
     */
    @TableField("flash_promotion_count")
    private Integer flashPromotionCount;

    /**
     * 每人限购数量
     */
    @TableField("flash_promotion_limit")
    private Integer flashPromotionLimit;
}
