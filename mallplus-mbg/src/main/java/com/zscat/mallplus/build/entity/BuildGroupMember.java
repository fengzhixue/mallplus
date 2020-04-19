package com.zscat.mallplus.build.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-12-02
 */
@Setter
@Getter
@TableName("build_group_member")
public class BuildGroupMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("group_id")
    private Long groupId;

    @TableField("member_id")
    private String memberId;

    @TableField("create_time")
    private Date createTime;

    /**
     * 发起人
     */
    @TableField("apply_id")
    private Long applyId;

    private String name;

    @TableField("goods_id")
    private Long goodsId;

    /**
     * 状态
     */
    private Integer status;

    @TableField("order_id")
    private String orderId;

    @TableField("exipre_time")
    private Long exipreTime;


}
