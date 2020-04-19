package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 提货单表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@TableName("bill_lading")
public class BillLading implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 提货单号
     */
    private String id;

    /**
     * 订单号
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 提货门店ID
     */
    @TableField("store_id")
    private Integer storeId;

    /**
     * 提货人姓名
     */
    private String name;

    /**
     * 提货手机号
     */
    private String mobile;

    /**
     * 处理店员ID
     */
    @TableField("clerk_id")
    private Integer clerkId;

    /**
     * 提货时间
     */
    private Long ptime;

    /**
     * 提货状态1=未提货 2=已提货
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 更新时间
     */
    private Long utime;

    /**
     * 删除时间
     */
    private Long isdel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getClerkId() {
        return clerkId;
    }

    public void setClerkId(Integer clerkId) {
        this.clerkId = clerkId;
    }

    public Long getPtime() {
        return ptime;
    }

    public void setPtime(Long ptime) {
        this.ptime = ptime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }

    public Long getIsdel() {
        return isdel;
    }

    public void setIsdel(Long isdel) {
        this.isdel = isdel;
    }

    @Override
    public String toString() {
        return "BillLading{" +
                ", id=" + id +
                ", orderId=" + orderId +
                ", storeId=" + storeId +
                ", name=" + name +
                ", mobile=" + mobile +
                ", clerkId=" + clerkId +
                ", ptime=" + ptime +
                ", status=" + status +
                ", ctime=" + ctime +
                ", utime=" + utime +
                ", isdel=" + isdel +
                "}";
    }
}
