package com.zscat.mallplus.build.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.build.entity.BuildingCommunity;
import com.zscat.mallplus.build.mapper.BuildingCommunityMapper;
import com.zscat.mallplus.build.service.IBuildingCommunityService;
import com.zscat.mallplus.exception.ApiMallPlusException;
import com.zscat.mallplus.sys.entity.SysUser;
import com.zscat.mallplus.sys.entity.SysUserRole;
import com.zscat.mallplus.sys.mapper.SysUserMapper;
import com.zscat.mallplus.sys.mapper.SysUserRoleMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 小区 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
@Service
public class BuildingCommunityServiceImpl extends ServiceImpl<BuildingCommunityMapper, BuildingCommunity> implements IBuildingCommunityService {

    @Resource
    private BuildingCommunityMapper communityMapper;
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public boolean saveCommunity(BuildingCommunity entity) {
        //1 创建小区
        entity.setCreateTime(new Date());
        communityMapper.insert(entity);
        // 2 创建物业公司账号
        SysUser user = new SysUser();
        user.setUsername(entity.getName());
        SysUser umsAdminList = userMapper.selectByUserName(entity.getName());
        if (umsAdminList != null) {
            throw new ApiMallPlusException("此小区已存在");
        }
        user.setStatus(1);
        //  user.setStoreId(entity.getId());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setCreateTime(new Date());
        user.setSupplyId(0L);
        user.setNote("小区账户：小区ID=" + entity.getName() + "," + entity.getId());
        user.setNickName(entity.getName());
        userMapper.insert(user);

        // 3 分配物业公司角色
        SysUserRole userRole = new SysUserRole();
        userRole.setAdminId(user.getId());
        userRole.setRoleId(3L);
        userRoleMapper.insert(userRole);
        return true;
    }
}
