package com.zscat.mallplus.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author zscat
 * @Description:小程序首页nav管理
 * @email 951449465@qq.com
 * @date 2019-05-08 00:09:37
 */
@Data
@TableName("pms_small_navicon_category")
public class PmsSmallNaviconCategory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "小程序首页分类ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分类名称")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "分类图标")
    @TableField("icon")
    private String icon;

    // 1 h5 url 其他 /pages/classify/classify   2 商品详情 3文章详情  4文章列表 5 智能表单
    @ApiModelProperty(value = "跳转页面 h5")
    @TableField("summary")
    private String summary;

    @ApiModelProperty(value = "跳转类型applet")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "跳转类型pc")
    @TableField("pc_url")
    private String pcUrl;
}
