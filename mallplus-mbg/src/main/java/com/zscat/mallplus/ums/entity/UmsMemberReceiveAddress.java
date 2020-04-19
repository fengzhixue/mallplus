package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 会员收货地址表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Setter
@Getter
@TableName("ums_member_receive_address")
public class UmsMemberReceiveAddress extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("member_id")
    private Long memberId;

    /**
     * 收货人名称
     */
    private String name;

    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 是否为默认
     */
    @TableField("default_status")
    private Integer defaultStatus;

    /**
     * 邮政编码
     */
    @TableField("post_code")
    private String postCode;

    /**
     * 省份/直辖市
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 详细地址(街道)
     */
    @TableField("detail_address")
    private String detailAddress;

    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
}
