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
 * 公告表
 * </p>
 *
 * @author zscat
 * @since 2019-12-02
 */
@Setter
@Getter
@TableName("build_notice")
public class BuildNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告类型
     */
    private Boolean type;

    @TableField("create_time")
    private Date createTime;

    /**
     * 排序 从小到大
     */
    private Integer sort;

    /**
     * 软删除位  有时间代表已删除
     */
    private Long isdel;

    /**
     * 所属店铺
     */
    @TableField("community_id")
    private Integer communityId;


}
