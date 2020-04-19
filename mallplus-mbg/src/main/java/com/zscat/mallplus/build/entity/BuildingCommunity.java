package com.zscat.mallplus.build.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 小区
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
@Setter
@Getter
@TableName("building_community")
public class BuildingCommunity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小区ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ID
     */
    @TableField("store_id")
    private Long storeId;

    /**
     * 小区名称
     */
    @Excel(name = "名称", orderNum = "1", width = 30)
    private String name;

    private String pic;

    @Excel(name = "物业编号", orderNum = "1", width = 30)
    @TableField("company_id")
    private Long companyId;

    /**
     * 小区栋数
     */
    @Excel(name = "小区栋数", orderNum = "2", width = 30)
    private String counts;

    /**
     * 小区地址
     */
    @Excel(name = "小区地址", orderNum = "3", width = 30)
    private String address;

    /**
     * 根据定位获取城市编码
     */
    @TableField("city_code")
    private String cityCode;

    /**
     * 地标，如王府井北60米
     */
    @TableField("nearby_landmarks")
    @Excel(name = "地标", orderNum = "4", width = 30)
    private String nearbyLandmarks;

    /**
     * 地区 纬度
     */
    @Excel(name = "纬度", orderNum = "5", width = 30)
    private String latitude;

    /**
     * 地区 经度
     */
    @Excel(name = "经度", orderNum = "6", width = 30)
    private String longitude;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd", orderNum = "9", width = 30)
    @TableField("create_time")
    private Date createTime;

    /**
     * 1申请 2审核失败 3 审核成功
     */
    @Excel(name = "状态", orderNum = "8", width = 30)
    private Integer status;
    @Excel(name = "电话", orderNum = "7", width = 30)
    private String phone;

    @TableField("status_desc")
    private String statusDesc;

}
