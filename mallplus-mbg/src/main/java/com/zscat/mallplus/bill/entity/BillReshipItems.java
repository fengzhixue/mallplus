package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 退货单明细表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@TableName("bill_reship_items")
public class BillReshipItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 退款单单id
     */
    @TableField("reship_id")
    private String reshipId;

    /**
     * 订单明细ID 关联order_items.id
     */
    @TableField("order_items_id")
    private Integer orderItemsId;

    /**
     * 商品ID 关联goods.id
     */
    @TableField("goods_id")
    private Integer goodsId;

    /**
     * 货品ID 关联products.id
     */
    @TableField("product_id")
    private Integer productId;

    /**
     * 货品编码
     */
    private String sn;

    /**
     * 商品编码
     */
    private String bn;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 图片
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 数量
     */
    private Integer nums;

    /**
     * 货品明细序列号存储
     */
    private String addon;

    /**
     * 更新时间
     */
    private Long utime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReshipId() {
        return reshipId;
    }

    public void setReshipId(String reshipId) {
        this.reshipId = reshipId;
    }

    public Integer getOrderItemsId() {
        return orderItemsId;
    }

    public void setOrderItemsId(Integer orderItemsId) {
        this.orderItemsId = orderItemsId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }

    @Override
    public String toString() {
        return "BillReshipItems{" +
                ", id=" + id +
                ", reshipId=" + reshipId +
                ", orderItemsId=" + orderItemsId +
                ", goodsId=" + goodsId +
                ", productId=" + productId +
                ", sn=" + sn +
                ", bn=" + bn +
                ", name=" + name +
                ", imageUrl=" + imageUrl +
                ", nums=" + nums +
                ", addon=" + addon +
                ", utime=" + utime +
                "}";
    }
}
