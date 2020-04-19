package com.zscat.mallplus.build.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("building_user_community")
public class UserCommunityRelate {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user_id")
    private Long userId;


    @TableField("community_id")
    private Long communityId;

    @TableField(exist = false)
    private String communityIds;

    @TableField(exist = false)
    private String name;
}
