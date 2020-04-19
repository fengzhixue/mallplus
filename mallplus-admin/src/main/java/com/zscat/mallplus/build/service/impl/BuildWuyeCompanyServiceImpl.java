package com.zscat.mallplus.build.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.build.entity.BuildWuyeCompany;
import com.zscat.mallplus.build.mapper.BuildWuyeCompanyMapper;
import com.zscat.mallplus.build.service.IBuildWuyeCompanyService;
import com.zscat.mallplus.exception.ApiMallPlusException;
import com.zscat.mallplus.sys.entity.SysUser;
import com.zscat.mallplus.sys.entity.SysUserRole;
import com.zscat.mallplus.sys.mapper.SysUserMapper;
import com.zscat.mallplus.sys.mapper.SysUserRoleMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-12-02
 */
@Service
public class BuildWuyeCompanyServiceImpl extends ServiceImpl<BuildWuyeCompanyMapper, BuildWuyeCompany> implements IBuildWuyeCompanyService {

    @Resource
    private BuildWuyeCompanyMapper wuyeCompanyMapper;
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Transactional
    @Override
    public boolean saveCompany(BuildWuyeCompany entity) {
        //1 创建物业公司
        entity.setCreateTime(new Date());
        wuyeCompanyMapper.insert(entity);
        // 2 创建物业公司账号
        SysUser user = new SysUser();
        user.setUsername(entity.getName());
        SysUser umsAdminList = userMapper.selectByUserName(entity.getName());
        if (umsAdminList != null) {
            throw new ApiMallPlusException("此物业公司已存在");
        }
        user.setStatus(1);
        // user.setStoreId(entity.getId());
        user.setSupplyId(0L);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setCreateTime(new Date());
        user.setSupplyId(0L);
        user.setNote("物业公司账户：物业公司ID=" + entity.getName() + "," + entity.getId());
        user.setIcon(entity.getPic());
        user.setNickName(entity.getName());
        userMapper.insert(user);

        // 3 分配物业公司角色
        SysUserRole userRole = new SysUserRole();
        userRole.setAdminId(user.getId());
        userRole.setRoleId(2L);
        userRoleMapper.insert(userRole);
        return true;
    }
}
