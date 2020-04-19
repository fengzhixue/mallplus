package com.zscat.mallplus.build.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("build_repair")
public class BuildRepair implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房屋编号
     */
    @TableField("room_id")
    private Long roomId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 解决时间
     */
    @TableField("solve_time")
    private Date solveTime;

    /**
     * 报修原因
     */
    private String resons;

    /**
     * 报修者电话
     */
    private String phone;

    /**
     * 解决记录
     */
    @TableField("solve_resons")
    private String solveResons;

    /**
     * 房屋具体位置
     */
    @TableField("room_desc")
    private String roomDesc;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 1 申请 2 处理中 3 已处理
     */
    private Integer status;


}
