package com.zscat.mallplus.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 运费模版
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("pms_feight_template")
public class PmsFeightTemplate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 计费类型:0->按重量；1->按件数
     */
    @TableField("charge_type")
    private Integer chargeType;

    /**
     * 首重kg
     */
    @TableField("first_weight")
    private BigDecimal firstWeight;

    /**
     * 首费（元）
     */
    @TableField("first_fee")
    private BigDecimal firstFee;

    @TableField("continue_weight")
    private BigDecimal continueWeight;

    @TableField("continme_fee")
    private BigDecimal continmeFee;

    /**
     * 目的地（省、市）
     */
    private String dest;
    @TableField("create_time")
    private Date createTime;
}
