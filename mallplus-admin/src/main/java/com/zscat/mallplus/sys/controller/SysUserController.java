package com.zscat.mallplus.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildingCommunity;
import com.zscat.mallplus.build.entity.UserCommunityRelate;
import com.zscat.mallplus.build.mapper.BuildingCommunityMapper;
import com.zscat.mallplus.build.mapper.UserCommunityRelateMapper;
import com.zscat.mallplus.sys.entity.SysPermission;
import com.zscat.mallplus.sys.entity.SysRole;
import com.zscat.mallplus.sys.entity.SysUser;
import com.zscat.mallplus.sys.mapper.SysPermissionMapper;
import com.zscat.mallplus.sys.service.ISysPermissionService;
import com.zscat.mallplus.sys.service.ISysRoleService;
import com.zscat.mallplus.sys.service.ISysUserService;
import com.zscat.mallplus.ums.service.RedisService;
import com.zscat.mallplus.util.JsonUtil;
import com.zscat.mallplus.util.UserUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import com.zscat.mallplus.vo.Rediskey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Slf4j
@Api(value = "用户管理", description = "", tags = {"用户管理"})
@RestController
@RequestMapping("/sys/sysUser")
public class SysUserController extends ApiController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysPermissionService permissionService;
    @Resource
    private SysPermissionMapper permissionMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private UserCommunityRelateMapper userCommunityRelateMapper;
    @Resource
    private BuildingCommunityMapper buildingCommunityMapper;

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有用户列表")
    @ApiOperation("根据条件查询所有用户列表")
    @GetMapping(value = "/list")
    public Object getUserByPage(SysUser entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(sysUserService.page(new Page<SysUser>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有用户列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "保存用户")
    @ApiOperation("保存用户")
    @PostMapping(value = "/register")
    public Object saveUser(@RequestBody SysUser entity) {
        try {
           /* if (ValidatorUtils.empty(entity.getStoreId())){
                entity.setStoreId(UserUtils.getCurrentMember().getStoreId());
            }*/
            if (sysUserService.saves(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存用户：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "更新用户")
    @ApiOperation("更新用户")
    @PostMapping(value = "/update/{id}")
    public Object updateUser(@RequestBody SysUser entity) {
        try {
            if (sysUserService.updates(entity.getId(), entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新用户：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "删除用户")
    @ApiOperation("删除用户")
    @GetMapping(value = "/delete/{id}")
    public Object deleteUser(@ApiParam("用户id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("用户id");
            }
            SysUser user = sysUserService.getById(id);
            if (user.getSupplyId() != null && user.getSupplyId() == 1) {
                return new CommonResult().paramFailed("管理员账号不能删除");
            }
            if (sysUserService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除用户：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "给用户分配角色")
    @ApiOperation("查询用户明细")
    @GetMapping(value = "/{id}")
    public Object getUserById(@ApiParam("用户id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("用户id");
            }
            SysUser coupon = sysUserService.getById(id);
            coupon.setPassword(null);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询用户明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @SysLog(MODULE = "sys", REMARK = "刷新token")
    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    @ResponseBody
    public Object refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = sysUserService.refreshToken(token);
        if (refreshToken == null) {
            return new CommonResult().failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new CommonResult().success(tokenMap);
    }

    @SysLog(MODULE = "sys", REMARK = "登录以后返回token")
    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    @ResponseBody
    public Object login1( @RequestParam(value = "username", defaultValue = "1") String username,
                         @RequestParam(value = "password", defaultValue = "1") String password) {
        try {
            String token = sysUserService.login(username, password);
            if (token == null) {
                return new CommonResult().paramFailed("用户名或密码错误");
            }
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            tokenMap.put("userInfo", UserUtils.getCurrentMember());
            return new CommonResult().success(tokenMap);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
    }
    @SysLog(MODULE = "sys", REMARK = "登录以后返回token")
    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody SysUser umsAdminLoginParam, BindingResult result) {
        try {
            String token = sysUserService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
            if (token == null) {
                return new CommonResult().paramFailed("用户名或密码错误");
            }
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            // tokenMap.put("userId", UserUtils.getCurrentMember().getId());
            return new CommonResult().success(tokenMap);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
    }
    @SysLog(MODULE = "sys", REMARK = "获取当前登录用户信息")
    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public Object getAdminInfo(Principal principal) {
        String username = principal.getName();
        SysUser queryU = new SysUser();
        queryU.setUsername(username);
        SysUser umsAdmin = sysUserService.getOne(new QueryWrapper<>(queryU));
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("roles", new String[]{"TEST"});
        if (umsAdmin != null) {
            data.put("sysUser", umsAdmin);
            data.put("icon", umsAdmin.getIcon());
            data.put("userId", umsAdmin.getId());
            data.put("storeId", umsAdmin.getStoreId());

        }

        return new CommonResult().success(data);
    }

    @SysLog(MODULE = "sys", REMARK = "登出功能")
    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object logout() {

        return new CommonResult().success(null);
    }

    @SysLog(MODULE = "sys", REMARK = "给用户分配角色")
    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateRole(@RequestParam("adminId") Long adminId,
                             @RequestParam("roleIds") List<Long> roleIds) {
        int count = sysUserService.updateUserRole(adminId, roleIds);
        if (count >= 0) {
            //更新，删除时候，如果redis里有权限列表，重置
            if (!redisService.exists(String.format(Rediskey.menuList, adminId))) {
                List<SysPermission> list = permissionMapper.listUserPerms(adminId);
                String key = String.format(Rediskey.menuList, adminId);
                redisService.set(key, JsonUtil.objectToJson(list));
                return list;
            }
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "获取指定用户的角色")
    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getRoleList(@PathVariable Long adminId) {
        List<SysRole> roleList = sysUserService.getRoleListByUserId(adminId);
        return new CommonResult().success(roleList);
    }

    @SysLog(MODULE = "sys", REMARK = "获取指定用户的角色")
    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/userRoleCheck", method = RequestMethod.GET)
    @ResponseBody
    public Object userRoleCheck(@RequestParam("adminId") Long adminId) {
        List<SysRole> roleList = sysUserService.getRoleListByUserId(adminId);
        List<SysRole> allroleList = roleService.list(new QueryWrapper<>());
        if (roleList != null && roleList.size() > 0) {
            for (SysRole a : allroleList) {
                for (SysRole u : roleList) {
                    if (u != null && u.getId() != null) {
                        if (a.getId().equals(u.getId())) {
                            a.setChecked(true);
                        }
                    }
                }
            }
            return new CommonResult().success(allroleList);
        }
        return new CommonResult().success(allroleList);
    }

    @SysLog(MODULE = "sys", REMARK = "给用户分配+-权限")
    @ApiOperation("给用户分配+-权限")
    @RequestMapping(value = "/permission/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePermission(@RequestParam Long adminId,
                                   @RequestParam("permissionIds") List<Long> permissionIds) {
        int count = sysUserService.updatePermissionByUserId(adminId, permissionIds);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "获取用户所有权限（包括+-权限）")
    @ApiOperation("获取用户所有权限（包括+-权限）")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getPermissionList(@PathVariable Long adminId) {
        List<SysPermission> permissionList = sysUserService.getPermissionListByUserId(adminId);
        return new CommonResult().success(permissionList);
    }

    @ApiOperation("修改展示状态")
    @RequestMapping(value = "/update/updateShowStatus")
    @ResponseBody
    @SysLog(MODULE = "sys", REMARK = "修改展示状态")
    public Object updateShowStatus(@RequestParam("ids") Long ids,
                                   @RequestParam("showStatus") Integer showStatus) {
        SysUser role = new SysUser();
        role.setId(ids);
        role.setStatus(showStatus);
        sysUserService.updateById(role);

        return new CommonResult().success();

    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    @SysLog(MODULE = "sys", REMARK = "修改密码")
    public Object updatePassword(@RequestParam("password") String password,
                                 @RequestParam("renewPassword") String renewPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (ValidatorUtils.empty(password)) {
            return new CommonResult().failed("参数为空");
        }
        if (ValidatorUtils.empty(renewPassword)) {
            return new CommonResult().failed("参数为空");
        }
        if (ValidatorUtils.empty(newPassword)) {
            return new CommonResult().failed("参数为空");
        }
        if (!renewPassword.equals(newPassword)) {
            return new CommonResult().failed("新密码不一致!");
        }
        try {
            sysUserService.updatePassword(password, newPassword);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().success();

    }

    @SysLog(MODULE = "sys", REMARK = "获取用户的小区")
    @ApiOperation("获取相应角色权限")
    @RequestMapping(value = "/community/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Object communityList(@PathVariable Long userId) {
        List<UserCommunityRelate> permissionList = userCommunityRelateMapper.selectList(new QueryWrapper<UserCommunityRelate>().eq("user_id", userId));
        return new CommonResult().success(permissionList);
    }

    @SysLog(MODULE = "sys", REMARK = "获取用户的小区")
    @ApiOperation("获取相应角色权限")
    @RequestMapping(value = "/userCommunityRelate", method = RequestMethod.POST)
    @ResponseBody
    public Object userCommunityRelate(@RequestBody UserCommunityRelate entity) {
        return new CommonResult().success(sysUserService.userCommunityRelate(entity));
    }

    @SysLog(MODULE = "sys", REMARK = "获取用户的小区")
    @ApiOperation("获取相应角色权限")
    @RequestMapping(value = "/communityUser/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Object communityUser(@PathVariable Long userId) {
        List<UserCommunityRelate> permissionList = userCommunityRelateMapper.selectList(new QueryWrapper<UserCommunityRelate>().eq("user_id", userId));
        List<UserCommunityRelate> newList = new ArrayList<>();
        for (UserCommunityRelate relate : permissionList) {
            BuildingCommunity community = buildingCommunityMapper.selectById(relate.getCommunityId());
            if (community != null) {
                relate.setName(community.getName());
                newList.add(relate);
            }
        }
        return new CommonResult().success(newList);
    }
}

