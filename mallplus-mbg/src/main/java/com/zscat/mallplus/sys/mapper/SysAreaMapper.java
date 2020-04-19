package com.zscat.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sys.entity.SysArea;
import com.zscat.mallplus.sys.vo.AreaWithChildrenItem;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface SysAreaMapper extends BaseMapper<SysArea> {

    List<AreaWithChildrenItem> listWithChildren();
}
