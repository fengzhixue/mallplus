package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 退货单表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@TableName("bill_reship")
public class BillReship implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("reship_id")
    private String reshipId;

    /**
     * 订单ID 关联order.id
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 售后单id
     */
    @TableField("aftersales_id")
    private String aftersalesId;

    /**
     * 用户ID 关联user.id
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
     * 状态 1=审核通过待发货 2=已发退货 3=已收退货
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


    public String getReshipId() {
        return reshipId;
    }

    public void setReshipId(String reshipId) {
        this.reshipId = reshipId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAftersalesId() {
        return aftersalesId;
    }

    public void setAftersalesId(String aftersalesId) {
        this.aftersalesId = aftersalesId;
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
        return "BillReship{" +
                ", reshipId=" + reshipId +
                ", orderId=" + orderId +
                ", aftersalesId=" + aftersalesId +
                ", userId=" + userId +
                ", logiCode=" + logiCode +
                ", logiNo=" + logiNo +
                ", status=" + status +
                ", memo=" + memo +
                ", ctime=" + ctime +
                ", utime=" + utime +
                "}";
    }
}
