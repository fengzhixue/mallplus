package com.zscat.mallplus.sms.entity;

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
 * 优惠券使用、领取历史表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("sms_coupon_history")
public class SmsCouponHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("coupon_id")
    private Long couponId;
    /**
     * 使用门槛；0表示无门槛
     */
    @TableField("min_point")
    private BigDecimal minPoint;

    @TableField("member_id")
    private Long memberId;

    @TableField("coupon_code")
    private String couponCode;

    private BigDecimal amount;
    /**
     * 领取人昵称
     */
    @TableField("member_nickname")
    private String memberNickname;

    /**
     * 获取类型：0->后台赠送；1->主动获取
     */
    @TableField("get_type")
    private Integer getType;

    @TableField("create_time")
    private Date createTime;

    /**
     * 使用状态：0->未使用；1->已使用；2->已过期
     */
    @TableField("use_status")
    private Integer useStatus;

    /**
     * 使用时间
     */
    @TableField("use_time")
    private Date useTime;

    /**
     * 订单编号
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 订单号码
     */
    @TableField("order_sn")
    private String orderSn;

    @TableField("start_time")
    private Date startTime;

    @TableField("end_time")
    private Date endTime;

    private String note;


}
