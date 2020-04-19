package com.zscat.mallplus.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mallplus
 * @date 2019-12-17
 * 短信记录
 */
@Data
@TableName("sms_content_msg")
public class SmsContentMsg implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField("phone")
    private String phone;


    @TableField("status")
    private Integer status;


    @TableField("content")
    private String content;


    @TableField("result")
    private String result;


    @TableField("create_time")
    private Date createTime;


    @TableField("content_id")
    private Integer contentId;


    @TableField("type")
    private Integer type;


}
