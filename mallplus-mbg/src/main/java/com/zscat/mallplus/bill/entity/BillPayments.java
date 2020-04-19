package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 支付单表
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@TableName("bill_payments")
public class BillPayments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付单号
     */
    @TableId("payment_id")
    private String paymentId;

    /**
     * 支付金额
     */
    private BigDecimal money;

    /**
     * 用户ID 关联user.id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 资源类型1=订单,2充值单
     */
    private Integer type;

    /**
     * 支付状态 1=未支付 2=支付成功 3=其他
     */
    private Boolean status;

    /**
     * 支付类型编码 关联payments.code
     */
    @TableField("payment_code")
    private String paymentCode;

    /**
     * 支付单生成IP
     */
    private String ip;

    /**
     * 支付的时候需要的参数，存的是json格式的一维数组
     */
    private String params;

    /**
     * 支付回调后的状态描述
     */
    @TableField("payed_msg")
    private String payedMsg;

    /**
     * 第三方平台交易流水号
     */
    @TableField("trade_no")
    private String tradeNo;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 更新时间
     */
    private Long utime;


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getPayedMsg() {
        return payedMsg;
    }

    public void setPayedMsg(String payedMsg) {
        this.payedMsg = payedMsg;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
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
        return "BillPayments{" +
                ", paymentId=" + paymentId +
                ", money=" + money +
                ", userId=" + userId +
                ", type=" + type +
                ", status=" + status +
                ", paymentCode=" + paymentCode +
                ", ip=" + ip +
                ", params=" + params +
                ", payedMsg=" + payedMsg +
                ", tradeNo=" + tradeNo +
                ", ctime=" + ctime +
                ", utime=" + utime +
                "}";
    }
}
