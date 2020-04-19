package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-10-21
 */
@TableName("tb_user_from_id")
public class TbUserFromId implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 小程序推送formId
     */
    @TableField("form_id")
    private String formId;

    /**
     * 来源 source 1，步数兑换海贝按钮；2，首页邀请按钮；3，步数拦截弹窗邀请按钮；4，兑换商品按钮；\n\n5，海贝不够邀请按钮；6，引导关注蒙层按钮；7，健康体验领取按钮 8，首页弹窗“马上领取”按钮
     */
    private Integer source;

    /**
     * 使用状态 1 未使用[默认] 2 已使用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 所属店铺
     */
    @TableField("store_id")
    private Integer storeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "TbUserFromId{" +
                ", id=" + id +
                ", userId=" + userId +
                ", formId=" + formId +
                ", source=" + source +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", storeId=" + storeId +
                "}";
    }
}
