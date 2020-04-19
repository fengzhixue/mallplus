package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 发货单详情表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@TableName("bill_delivery_items")
public class BillDeliveryItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发货单号 关联bill_delivery.id
     */
    @TableField("delivery_id")
    private String deliveryId;

    /**
     * 订单明细ID 关联order_items.id
     */
    @TableField("order_items_id")
    private Integer orderItemsId;

    /**
     * 发货数量
     */
    private Integer nums;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getOrderItemsId() {
        return orderItemsId;
    }

    public void setOrderItemsId(Integer orderItemsId) {
        this.orderItemsId = orderItemsId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    @Override
    public String toString() {
        return "BillDeliveryItems{" +
                ", id=" + id +
                ", deliveryId=" + deliveryId +
                ", orderItemsId=" + orderItemsId +
                ", nums=" + nums +
                "}";
    }
}
