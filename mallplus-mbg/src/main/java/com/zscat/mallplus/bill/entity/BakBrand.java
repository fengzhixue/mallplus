package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 品牌商表
 * </p>
 *
 * @author zscat
 * @since 2019-09-18
 */
@Data
@TableName("bak_brand")
public class BakBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 品牌商名称
     */
    private String name;

    /**
     * 品牌商简介
     */
    private String descs;

    /**
     * 品牌商页的品牌商图片
     */
    @TableField("pic_url")
    private String picUrl;

    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 品牌商的商品低价，仅用于页面展示
     */
    @TableField("floor_price")
    private BigDecimal floorPrice;

    /**
     * 创建时间
     */
    @TableField("add_time")
    private LocalDateTime addTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Boolean deleted;


}
