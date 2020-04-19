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
@TableName("build_wuye_price")
public class BuildWuyePrice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房屋
     */
    @TableField("room_id")
    private Long roomId;

    @TableField("price_id")
    private Long priceId;

    /**
     * 收费名称
     */
    @TableField("price_name")
    private String priceName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 用量
     */
    private BigDecimal amount;

    /**
     * 总额
     */
    private BigDecimal moneys;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 社区编号
     */
    @TableField("community_id")
    private Long communityId;

    @TableField("room_desc")
    private String roomDesc;


}
