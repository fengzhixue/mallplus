package com.zscat.mallplus.sys.vo;


import com.zscat.mallplus.sys.entity.SysArea;

import java.util.List;

/**
 * https://github.com/shenzhuan/mallplus on 2018/5/25.
 */
public class AreaWithChildrenItem extends SysArea {
    private List<SysArea> children;

    public List<SysArea> getChildren() {
        return children;
    }

    public void setChildren(List<SysArea> children) {
        this.children = children;
    }
}
