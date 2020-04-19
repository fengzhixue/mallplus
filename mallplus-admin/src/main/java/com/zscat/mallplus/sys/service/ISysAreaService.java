package com.zscat.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sys.entity.SysArea;
import com.zscat.mallplus.sys.vo.AreaWithChildrenItem;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface ISysAreaService extends IService<SysArea> {

    List<AreaWithChildrenItem> listWithChildren();
}
