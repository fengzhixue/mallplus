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
 * @date 2019-12-07
 * 短信模版
 */
@Data
@TableName("sms_content")
public class SmsContent implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

    @TableField("meno")
    private String meno;

    @TableField("code")
    private String code;

    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private Date createTime;


}
