package com.zscat.mallplus.build.entity;

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
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-12-02
 */
@Setter
@Getter
@TableName("build_wuye_company")
public class BuildWuyeCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 1申请 2 审核失败 3 审核成功
     */
    private String status;

    /**
     * 照片
     */
    private String pic;

    /**
     * 位置
     */
    private String address;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 审核内容
     */
    @TableField("status_desc")
    private String statusDesc;

    private String phone;


}
