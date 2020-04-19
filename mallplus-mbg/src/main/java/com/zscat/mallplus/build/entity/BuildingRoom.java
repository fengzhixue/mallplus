package com.zscat.mallplus.build.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 小区
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
@Setter
@Getter
@TableName("building_room")
public class BuildingRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房屋ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房屋编号
     */
    @Excel(name = "房屋编号", orderNum = "1", width = 30)
    @TableField("room_num")
    private String roomNum;

    @TableField("room_desc")
    private String roomDesc;


    @Excel(name = "小区编号", orderNum = "2", width = 30)
    @TableField("community_id")
    private Long communityId;

    @Excel(name = "楼编号", orderNum = "3", width = 30)
    @TableField("floor_id")
    private Long floorId;
    /**
     * 单元ID
     */
    @Excel(name = "单元编号", orderNum = "4", width = 30)
    @TableField("unit_id")
    private String unitId;

    /**
     * 层数
     */
    @Excel(name = "层数", orderNum = "5", width = 30)
    private Integer layer;

    /**
     * 室
     */
    @Excel(name = "室", orderNum = "6", width = 30)
    private String section;

    /**
     * 户型
     */
    @Excel(name = "户型", orderNum = "7", width = 30)
    private String apartment;

    /**
     * 建筑面积
     */
    @Excel(name = "建筑面积", orderNum = "8", width = 30)
    @TableField("built_up_area")
    private BigDecimal builtUpArea;

    /**
     * 每平米单价
     */
    @Excel(name = "每平米单价", orderNum = "9", width = 30)
    @TableField("unit_price")
    private BigDecimal unitPrice;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "10", width = 30)
    private String remark;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd", orderNum = "11", width = 30)
    @TableField("create_time")
    private Date createTime;

    /**
     * 数据状态，详细参考c_status表，S 保存，0, 在用 1失效
     */
    @TableField("status_cd")
    private String statusCd;
    private String pic;
    private String pics;
    /**
     * 房屋状态，如房屋出售等，请查看state 表
     */
    private String state;

    @TableField(exist = false)
    private Long pid;
    @TableField(exist = false)
    private String label;

    public String getLabel() {
        return roomNum;
    }

    public void setLabel(String label) {
        this.roomNum = roomNum;
    }

    public Long getPid() {
        return floorId;
    }

    public void setPid(Long pid) {
        this.floorId = floorId;
    }
}
