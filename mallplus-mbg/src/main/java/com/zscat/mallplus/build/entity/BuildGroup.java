package com.zscat.mallplus.build.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.pms.entity.PmsProduct;
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
@TableName("build_group")
public class BuildGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    PmsProduct goods;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("goods_id")
    private Long goodsId;
    /**
     * 商品
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 商品价格
     */
    @TableField("origin_price")
    private BigDecimal originPrice;
    /**
     * 拼团价格
     */
    @TableField("group_price")
    private BigDecimal groupPrice;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 成团人数
     */
    private Integer peoples;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 拼团总人数
     */
    @TableField("max_people")
    private Integer maxPeople;
    /**
     * 所属店铺
     */
    @TableField("community_id")
    private Long communityId;

}
