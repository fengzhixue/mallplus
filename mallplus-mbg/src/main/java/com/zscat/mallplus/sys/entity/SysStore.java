package com.zscat.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.pms.entity.PmsProductAttributeCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-05-18
 */
@Data
@TableName("sys_store")
public class SysStore implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;

    private Integer status;

    private Long uid;

    private Integer type;

    @TableField("contact_qq")
    private String contactQq;

    @TableField("sms_quantity")
    private Long smsQuantity;

    @TableField("register_type")
    private Integer registerType;
    private String logo;

    @TableField("expire_time")
    private Date expireTime;

    @TableField("try_time")
    private Date tryTime;

    @TableField("contact_mobile")
    private String contactMobile;

    @TableField("address_province")
    private String addressProvince;

    @TableField("address_lat")
    private String addressLat;

    @TableField("address_detail")
    private String addressDetail;

    @TableField("address_city")
    private String addressCity;

    @TableField("address_lng")
    private String addressLng;

    @TableField("address_area")
    private String addressArea;

    @TableField("buy_plan_times")
    private Long buyPlanTimes;

    @TableField("create_time")
    private Date createTime;

    @TableField("is_checked")
    private Integer isChecked;

    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField("service_phone")
    private String servicePhone;

    @TableField("contact_name")
    private String contactName;

    @TableField("delete_time")
    private Date deleteTime;

    @TableField("diy_profile")
    private String diyProfile;

    @TableField("industry_two")
    private Long industryTwo;

    @TableField("is_star")
    private Integer isStar;

    @TableField("is_try")
    private Integer isTry;


    @TableField("plan_id")
    private Long planId;
    /**
     * 应用执照pic
     */
    @TableField("support_name")
    private String supportName;


    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 联系电话
     */
    @TableField("support_phone")
    private String supportPhone;

    /**
     * 二维码
     */
    @TableField("contact_qrcode")
    private String contactQrcode;

    private String description;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("industry_one")
    private Long industryOne;

    @TableField(exist = false)
    private List<PmsProductAttributeCategory> list;

    private Integer hit;

    private Integer collect;
    @TableField("is_boutique")
    private Integer isBoutique;
    @TableField(exist = false)
    private Integer goodsCount;

}
