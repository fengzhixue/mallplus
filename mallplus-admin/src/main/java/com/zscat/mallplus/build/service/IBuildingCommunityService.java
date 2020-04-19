package com.zscat.mallplus.build.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.build.entity.BuildingCommunity;

/**
 * <p>
 * 小区 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
public interface IBuildingCommunityService extends IService<BuildingCommunity> {

    boolean saveCommunity(BuildingCommunity entity);
}
