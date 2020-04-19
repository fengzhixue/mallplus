package com.zscat.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.build.entity.UserCommunityRelate;
import com.zscat.mallplus.sys.entity.SysPermission;
import com.zscat.mallplus.sys.entity.SysRole;
import com.zscat.mallplus.sys.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface ISysUserService extends IService<SysUser> {

    String refreshToken(String token);

    String login(String username, String password);

    int updateUserRole(Long adminId, List<Long> roleIds);

    List<SysRole> getRoleListByUserId(Long adminId);

    int updatePermissionByUserId(Long adminId, List<Long> permissionIds);

    List<SysPermission> getPermissionListByUserId(Long adminId);

    List<SysPermission> listMenuByUserId(Long adminId);

    boolean saves(SysUser entity);

    boolean updates(Long id, SysUser admin);

    List<SysPermission> listUserPerms(Long id);

    void removePermissRedis(Long id);

    List<SysPermission> listPerms();

    Object reg(SysUser entity);

//    SmsCode generateCode(String phone);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    Object userCommunityRelate(UserCommunityRelate entity);

    void updatePassword(String password, String newPassword);
}
