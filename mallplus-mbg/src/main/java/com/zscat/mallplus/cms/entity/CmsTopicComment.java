package com.zscat.mallplus.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 专题评论表
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Data
@TableName("cms_topic_comment")
public class CmsTopicComment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属专题
     */
    @TableField("topic_id")
    private Long topicId;

    /**
     * 用户名
     */
    @TableField("member_nick_name")
    private String memberNickName;

    /**
     * 用户图标
     */
    @TableField("member_icon")
    private String memberIcon;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 状态
     */
    @TableField("show_status")
    private Integer showStatus;


}
