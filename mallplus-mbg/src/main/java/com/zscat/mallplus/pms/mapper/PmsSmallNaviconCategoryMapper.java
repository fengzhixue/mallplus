package com.zscat.mallplus.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.pms.entity.PmsSmallNaviconCategory;


/**
 * 小程序首页nav管理
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-05-08 00:09:37
 */

public interface PmsSmallNaviconCategoryMapper extends BaseMapper<PmsSmallNaviconCategory> {

    PmsSmallNaviconCategory get(Long id);

//	List<PmsSmallNaviconCategory> list(PmsSmallNaviconCategory smallNaviconCategory);
//
//    int count(PmsSmallNaviconCategory smallNaviconCategory);
//
//	int save(PmsSmallNaviconCategory smallNaviconCategory);
//
//	int update(PmsSmallNaviconCategory smallNaviconCategory);
//
//	int remove(Long id);
//
//	int batchRemove(Integer[] ids);
}
