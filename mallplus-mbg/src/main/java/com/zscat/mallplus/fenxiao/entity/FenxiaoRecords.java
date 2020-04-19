package com.zscat.mallplus.fenxiao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mallplus
 * @date 2019-12-17
 * 分销记录
 */
@Data
@TableName("fenxiao_records")
public class FenxiaoRecords implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField("order_id")
    private Long orderId;


    @TableField("member_id")
    private Long memberId;
    @TableField("type")
    private Integer type;

    @TableField("invite_id")
    private Long inviteId;
    @TableField("goods_id")
    private Long goodsId;

    @TableField("money")
    private BigDecimal money;


    @TableField("level")
    private String level;


    @TableField("status")
    private String status;


    @TableField("create_time")
    private Date createTime;


}
