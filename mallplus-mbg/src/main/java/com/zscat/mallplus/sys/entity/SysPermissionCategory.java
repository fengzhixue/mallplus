package com.zscat.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zscat.mallplus.utils.BaseEntity;

import java.io.Serializable;


/**
 * 权限类别表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-04-27 18:52:51
 */
public class SysPermissionCategory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //类型名称
    private String name;
    //类型图标
    private String icon;
    //类型数量
    private Integer typeCount;
    //状态
    private Integer showStatus;
    //排序
    private Integer sort;

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：类型名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：类型名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：类型图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置：类型图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取：类型数量
     */
    public Integer getTypeCount() {
        return typeCount;
    }

    /**
     * 设置：类型数量
     */
    public void setTypeCount(Integer typeCount) {
        this.typeCount = typeCount;
    }

    /**
     * 获取：状态
     */
    public Integer getShowStatus() {
        return showStatus;
    }

    /**
     * 设置：状态
     */
    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    /**
     * 获取：排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置：排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
