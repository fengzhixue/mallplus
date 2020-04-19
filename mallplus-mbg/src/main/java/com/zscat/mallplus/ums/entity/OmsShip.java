package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 配送方式表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@Data
@TableName("oms_ship")
public class OmsShip implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配送方式名称
     */
    private String name;

    /**
     * 是否货到付款 1=不是货到付款 2=是货到付款
     */
    @TableField("has_cod")
    private Integer hasCod;

    /**
     * 首重
     */
    private Integer firstunit;

    /**
     * 续重
     */
    private Integer continueunit;

    /**
     * 按地区设置配送费用是否启用默认配送费用 1=启用 2=不启用
     */
    @TableField("def_area_fee")
    private Integer defAreaFee;

    /**
     * 地区类型 1=全部地区 2=指定地区
     */
    private Integer type;

    /**
     * 首重费用
     */
    @TableField("firstunit_price")
    private BigDecimal firstunitPrice;

    /**
     * 续重费用
     */
    @TableField("continueunit_price")
    private BigDecimal continueunitPrice;

    /**
     * 配送费用计算表达式
     */
    private String exp;

    /**
     * 物流公司名称
     */
    @TableField("logi_name")
    private String logiName;

    /**
     * 物流公司编码
     */
    @TableField("logi_code")
    private String logiCode;

    /**
     * 是否默认 1=默认 2=不默认
     */
    @TableField("is_def")
    private Integer isDef;

    /**
     * 配送方式排序 越小越靠前
     */
    private Integer sort;

    /**
     * 状态 1=正常 2=停用
     */
    private Integer status;

    /**
     * 是否包邮，1包邮，2不包邮
     */
    @TableField("free_postage")
    private Integer freePostage;

    /**
     * 地区配送费用
     */
    @TableField("area_fee")
    private String areaFee;

    /**
     * 商品总额满多少免运费
     */
    private BigDecimal goodsmoney;


}
