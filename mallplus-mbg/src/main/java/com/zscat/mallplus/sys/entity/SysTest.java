package com.zscat.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mallplus
 * @date 2019-12-02
 * 测试
 */

@Data
@TableName("sys_test")
public class SysTest implements Serializable {

    @TableField("id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("create_time")
    private Date createTime;


}
