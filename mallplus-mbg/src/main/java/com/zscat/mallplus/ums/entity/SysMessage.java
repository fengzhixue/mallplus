package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 站内信
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@TableName("sys_message")
public class SysMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 消息编码
     */
    private String code;

    /**
     * 参数
     */
    private String params;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 查看时间
     */
    private Long utime;

    /**
     * 1未查看，2已查看
     */
    private Boolean status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysMessage{" +
                ", id=" + id +
                ", userId=" + userId +
                ", code=" + code +
                ", params=" + params +
                ", content=" + content +
                ", ctime=" + ctime +
                ", utime=" + utime +
                ", status=" + status +
                "}";
    }
}
