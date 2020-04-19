package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 会员等级表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("ums_member_level")
public class UmsMemberLevel extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    @TableField("growth_point")
    private Integer growthPoint;

    /**
     * 是否为默认等级：0->不是；1->是
     */
    @TableField("default_status")
    private Integer defaultStatus;

    /**
     * 免运费标准
     */
    @TableField("free_freight_point")
    private BigDecimal freeFreightPoint;

    /**
     * 每次评价获取的成长值
     */
    @TableField("comment_growth_point")
    private Integer commentGrowthPoint;

    /**
     * 是否有免邮特权
     */
    @TableField("priviledge_free_freight")
    private Integer priviledgeFreeFreight;

    /**
     * 是否有签到特权
     */
    @TableField("priviledge_sign_in")
    private Integer priviledgeSignIn;

    /**
     * 是否有评论获奖励特权
     */
    @TableField("priviledge_comment")
    private Integer priviledgeComment;

    /**
     * 是否有专享活动特权
     */
    @TableField("priviledge_promotion")
    private Integer priviledgePromotion;

    /**
     * 是否有会员价格特权
     */
    @TableField("priviledge_member_price")
    private Integer priviledgeMemberPrice;

    /**
     * 是否有生日特权
     */
    @TableField("priviledge_birthday")
    private Integer priviledgeBirthday;

    /**
     * 可发文章数
     */
    private Integer articlecount;

    /**
     * 可发商品数
     */
    private Integer goodscount;

    /**
     * 成为会员的价格
     */
    private BigDecimal price;

    private String note;
    private String icon;
    private String pic;

}
