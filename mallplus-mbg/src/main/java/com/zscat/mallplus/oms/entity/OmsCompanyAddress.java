package com.zscat.mallplus.oms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mallplus
 * @date 2019-12-07
 * 发货地址
 */
@Data
@TableName("oms_company_address")
public class OmsCompanyAddress implements Serializable {

    @TableField("id")
    private Long id;

    @TableField("address_name")
    private String addressName;

    @TableField("send_status")
    private Integer sendStatus;

    @TableField("receive_status")
    private Integer receiveStatus;

    @TableField("name")
    private String name;

    @TableField("phone")
    private String phone;

    @TableField("province")
    private String province;

    @TableField("city")
    private String city;

    @TableField("region")
    private String region;

    @TableField("detail_address")
    private String detailAddress;

    @TableField("store_id")
    private Integer storeId;


}
