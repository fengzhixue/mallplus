package com.zscat.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sys.entity.SysPermissionCategory;


/**
 * 权限类别表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-04-27 18:52:51
 */

public interface SysPermissionCategoryMapper extends BaseMapper<SysPermissionCategory> {

    SysPermissionCategory get(Long id);
//
//	List<SysPermissionCategory> list(SysPermissionCategory permissionCategory);
//
//    int count(SysPermissionCategory permissionCategory);
//
//	int save(SysPermissionCategory permissionCategory);
//
//	int update(SysPermissionCategory permissionCategory);
//
//	int remove(Long id);
//
//	int batchRemove(Long[] ids);
}
