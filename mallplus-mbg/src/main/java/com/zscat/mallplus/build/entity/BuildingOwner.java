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
 * 业主成员表
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
@Setter
@Getter
@TableName("building_owner")
public class BuildingOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业主成员ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业主ID
     */
    @Excel(name = "业主ID", orderNum = "1", width = 30)
    @TableField("owner_id")
    private Long ownerId;
    @Excel(name = "房屋编号", orderNum = "2", width = 30)
    @TableField("room_id")
    private Long roomId;
    /**
     * 业主名称
     */
    @Excel(name = "名称", orderNum = "3", width = 30)
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别", orderNum = "4", width = 30)
    private String sex;

    /**
     * 年龄
     */
    @Excel(name = "年龄", orderNum = "5", width = 30)
    private Integer age;

    /**
     * 联系人手机号
     */
    @Excel(name = "联系人手机号", orderNum = "6", width = 30)
    private String phone;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "7", width = 30)
    private String remark;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd", orderNum = "8", width = 30)
    @TableField("create_time")
    private Date createTime;

    /**
     * 数据状态，详细参考c_status表，0, 在用 1失效
     */
    private String status;

    /**
     * 1 业主本人 2 家庭成员 3 租户
     */
    @Excel(name = "名称", orderNum = "3", width = 30)
    private Integer type;

    @TableField("status_desc")
    private String statusDesc;

    private String roomDesc;

    private String username;

    private String password;

    private String openid;
}
