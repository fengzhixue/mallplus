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
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
@Setter
@Getter
@TableName("building_floor")
public class BuildingFloor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 楼ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Excel(name = "社区编号", orderNum = "1", width = 30)
    @TableField("community_id")
    private Long communityId;
    /**
     * 楼编号
     */
    @Excel(name = "楼编号", orderNum = "2", width = 30)
    @TableField("floor_num")
    private String floorNum;

    /**
     * 小区楼名称
     */
    @Excel(name = "名称", orderNum = "3", width = 30)
    private String name;

    @Excel(name = "楼层", orderNum = "4", width = 30)
    @TableField("layer_count")
    private Integer layerCount;

    /**
     * 1 有电梯
     */
    @Excel(name = "电梯", orderNum = "5", width = 30)
    private Integer lift;
    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "6", width = 30)
    private String remark;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd", orderNum = "7", width = 30)
    @TableField("create_time")
    private Date createTime;
    private String pic;

    @TableField(exist = false)
    private List<BuildingRoom> children;

    @TableField(exist = false)
    private String label;

    public String getLabel() {
        return name;
    }

    public void setLabel(String label) {
        this.name = name;
    }
}
