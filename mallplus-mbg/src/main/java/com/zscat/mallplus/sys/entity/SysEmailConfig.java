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
@TableName("sys_email_config")
public class SysEmailConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收件人
     */
    @TableField("from_user")
    private String fromUser;

    /**
     * 邮件服务器SMTP地址
     */
    private String host;

    /**
     * 密码
     */
    private String pass;

    /**
     * 端口
     */
    private String port;

    /**
     * 发件者用户名
     */
    private String user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SysEmailConfig{" +
                ", id=" + id +
                ", fromUser=" + fromUser +
                ", host=" + host +
                ", pass=" + pass +
                ", port=" + port +
                ", user=" + user +
                "}";
    }
}
