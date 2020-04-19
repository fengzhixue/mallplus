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
@TableName("sys_qiniu_config")
public class SysQiniuConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * accessKey
     */
    @TableField("access_key")
    private String accessKey;

    /**
     * Bucket 识别符
     */
    private String bucket;

    /**
     * 外链域名
     */
    private String host;

    /**
     * secretKey
     */
    @TableField("secret_key")
    private String secretKey;

    /**
     * 空间类型
     */
    private String type;

    /**
     * 机房
     */
    private String zone;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "SysQiniuConfig{" +
                ", id=" + id +
                ", accessKey=" + accessKey +
                ", bucket=" + bucket +
                ", host=" + host +
                ", secretKey=" + secretKey +
                ", type=" + type +
                ", zone=" + zone +
                "}";
    }
}
