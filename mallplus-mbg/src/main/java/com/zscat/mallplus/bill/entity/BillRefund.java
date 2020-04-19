package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 退款单表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@TableName("bill_refund")
public class BillRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("refund_id")
    private String refundId;

    /**
     * 售后单id
     */
    @TableField("aftersales_id")
    private String aftersalesId;

    /**
     * 退款金额
     */
    private BigDecimal money;

    /**
     * 用户ID 关联user.id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 资源id，根据type不同而关联不同的表
     */
    @TableField("source_id")
    private String sourceId;

    /**
     * 资源类型1=订单,2充值单
     */
    private Integer type;

    /**
     * 退款支付类型编码 默认原路返回 关联支付单表支付编码
     */
    @TableField("payment_code")
    private String paymentCode;

    /**
     * 第三方平台交易流水号
     */
    @TableField("trade_no")
    private String tradeNo;

    /**
     * 状态 1=未退款 2=已退款 3=退款失败，可以再次退款，4退款拒绝
     */
    private Boolean status;

    /**
     * 退款失败原因
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


    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getAftersalesId() {
        return aftersalesId;
    }

    public void setAftersalesId(String aftersalesId) {
        this.aftersalesId = aftersalesId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
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
        return "BillRefund{" +
                ", refundId=" + refundId +
                ", aftersalesId=" + aftersalesId +
                ", money=" + money +
                ", userId=" + userId +
                ", sourceId=" + sourceId +
                ", type=" + type +
                ", paymentCode=" + paymentCode +
                ", tradeNo=" + tradeNo +
                ", status=" + status +
                ", memo=" + memo +
                ", ctime=" + ctime +
                ", utime=" + utime +
                "}";
    }
}
