package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 发货单表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@TableName("bill_delivery")
public class BillDelivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("delivery_id")
    private String deliveryId;

    /**
     * 订单ID 关联order.id
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 用户id 关联user.id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 物流公司编码
     */
    @TableField("logi_code")
    private String logiCode;

    /**
     * 物流单号
     */
    @TableField("logi_no")
    private String logiNo;

    /**
     * 快递物流信息
     */
    @TableField("logi_information")
    private String logiInformation;

    /**
     * 0快递信息可能更新  1快递信息不更新了
     */
    @TableField("logi_status")
    private Integer logiStatus;

    /**
     * 收货地区ID
     */
    @TableField("ship_area_id")
    private Integer shipAreaId;

    /**
     * 收货详细地址
     */
    @TableField("ship_address")
    private String shipAddress;

    /**
     * 收货人姓名
     */
    @TableField("ship_name")
    private String shipName;

    /**
     * 收货电话
     */
    @TableField("ship_mobile")
    private String shipMobile;

    /**
     * 确认s收货时间
     */
    @TableField("confirm_time")
    private Long confirmTime;

    /**
     * 状态 1=准备发货 2=已发货 3=已确认 4=其他
     */
    private Boolean status;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 更新时间
     */
    private Long utime;


    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogiCode() {
        return logiCode;
    }

    public void setLogiCode(String logiCode) {
        this.logiCode = logiCode;
    }

    public String getLogiNo() {
        return logiNo;
    }

    public void setLogiNo(String logiNo) {
        this.logiNo = logiNo;
    }

    public String getLogiInformation() {
        return logiInformation;
    }

    public void setLogiInformation(String logiInformation) {
        this.logiInformation = logiInformation;
    }

    public Integer getLogiStatus() {
        return logiStatus;
    }

    public void setLogiStatus(Integer logiStatus) {
        this.logiStatus = logiStatus;
    }

    public Integer getShipAreaId() {
        return shipAreaId;
    }

    public void setShipAreaId(Integer shipAreaId) {
        this.shipAreaId = shipAreaId;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile;
    }

    public Long getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Long confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    @Override
    public String toString() {
        return "BillDelivery{" +
                ", deliveryId=" + deliveryId +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", logiCode=" + logiCode +
                ", logiNo=" + logiNo +
                ", logiInformation=" + logiInformation +
                ", logiStatus=" + logiStatus +
                ", shipAreaId=" + shipAreaId +
                ", shipAddress=" + shipAddress +
                ", shipName=" + shipName +
                ", shipMobile=" + shipMobile +
                ", confirmTime=" + confirmTime +
                ", status=" + status +
                ", memo=" + memo +
                ", ctime=" + ctime +
                ", utime=" + utime +
                "}";
    }
}
