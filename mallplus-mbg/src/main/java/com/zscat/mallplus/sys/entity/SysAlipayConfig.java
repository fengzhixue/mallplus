package com.zscat.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-11-30
 */
@TableName("sys_alipay_config")
public class SysAlipayConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private String appId;

    /**
     * 编码
     */
    private String charset;

    /**
     * 类型 固定格式json
     */
    private String format;

    /**
     * 网关地址
     */
    @TableField("gateway_url")
    private String gatewayUrl;

    /**
     * 异步回调
     */
    @TableField("notify_url")
    private String notifyUrl;

    /**
     * 私钥
     */
    @TableField("private_key")
    private String privateKey;

    /**
     * 公钥
     */
    @TableField("public_key")
    private String publicKey;

    /**
     * 回调地址
     */
    @TableField("return_url")
    private String returnUrl;

    /**
     * 签名方式
     */
    @TableField("sign_type")
    private String signType;

    /**
     * 商户号
     */
    @TableField("sys_service_provider_id")
    private String sysServiceProviderId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSysServiceProviderId() {
        return sysServiceProviderId;
    }

    public void setSysServiceProviderId(String sysServiceProviderId) {
        this.sysServiceProviderId = sysServiceProviderId;
    }

    @Override
    public String toString() {
        return "SysAlipayConfig{" +
                ", id=" + id +
                ", appId=" + appId +
                ", charset=" + charset +
                ", format=" + format +
                ", gatewayUrl=" + gatewayUrl +
                ", notifyUrl=" + notifyUrl +
                ", privateKey=" + privateKey +
                ", publicKey=" + publicKey +
                ", returnUrl=" + returnUrl +
                ", signType=" + signType +
                ", sysServiceProviderId=" + sysServiceProviderId +
                "}";
    }
}
