package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 退货单表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@Data
@TableName("bill_aftersales")
public class BillAftersales implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    List<BillAftersalesItems> itemList;
    /**
     * 售后单id
     */
    @TableId("aftersales_id")
    private String aftersalesId;
    /**
     * 订单ID 关联order.id
     */
    @TableField("order_id")
    private String orderId;
    /**
     * 用户ID 关联user.id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 售后类型，1=只退款，2退款退货
     */
    private Boolean type;
    /**
     * 退款金额
     */
    private BigDecimal refund;
    /**
     * 状态 1=未审核 2=审核通过 3=审核拒绝
     */
    private Boolean status;
    /**
     * 退款原因
     */
    private String reason;
    /**
     * 卖家备注，如果审核失败了，会显示到前端
     */
    private String mark;
    /**
     * 创建时间
     */
    private Long ctime;
    /**
     * 更新时间
     */
    private Long utime;


}
