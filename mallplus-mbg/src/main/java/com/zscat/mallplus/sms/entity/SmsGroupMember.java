package com.zscat.mallplus.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("sms_group_member")
public class SmsGroupMember extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("group_record_id")
    private Long groupRecordId;

    @TableField("member_id")
    private Long memberId;

    @TableField("create_time")
    private Date createTime;
    @TableField("exipre_time")
    private Long exipreTime;


    private String name;

    @TableField("goods_id")
    private Long goodsId;

    /**
     * 状态 1
     */
    private Integer status;

    @TableField("order_id")
    private String orderId;

    private String pic;

}
