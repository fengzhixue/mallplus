package com.zscat.mallplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sys.entity.SysPermissionCategory;
import com.zscat.mallplus.sys.service.ISysPermissionCategoryService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since ${date}
 */
@Slf4j
@RestController
@Api(tags = "SysPermissionCategoryController", description = "管理")
@RequestMapping("/sys/permissionCategory")
public class SysPermissionCategoryController {
    @Resource
    private ISysPermissionCategoryService ISysPermissionCategoryService;

    @SysLog(MODULE = "sys", REMARK = "查询sys_permission_category表")
    @ApiOperation("查询sys_permission_category表")
    @GetMapping(value = "/list")
    public Object getSysPermissionCategoryByPage(SysPermissionCategory entity,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            Object data = ISysPermissionCategoryService.page(new Page<SysPermissionCategory>(pageNum, pageSize), new QueryWrapper<>(entity));
            return new CommonResult().success(data);
        } catch (Exception e) {
            log.error("分页获取sys_permission_category列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "保存sys_permission_category表")
    @ApiOperation("保存sys_permission_category表")
    @PostMapping(value = "/create")
    public Object savePermissionCategory(@RequestBody SysPermissionCategory entity) {
        try {
            if (ISysPermissionCategoryService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存sys_permission_category表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "更新sys_permission_category")
    @ApiOperation("更新sys_permission_category")
    @PostMapping(value = "/update/{id}")
    public Object updatePermissionCategory(@RequestBody SysPermissionCategory entity) {
        try {
            if (ISysPermissionCategoryService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "删除sys_permission_category数据")
    @ApiOperation("删除权限类别表数据")
    @GetMapping(value = "/delete/{id}")
    public Object deleteRole(@ApiParam("权限类别表_id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("SysPermissionCategory_id");
            }
            if (ISysPermissionCategoryService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除权限类别表数据：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "根据ID查询sys_permission_category")
    @ApiOperation("根据ID查询sys_permission_category")
    @GetMapping(value = "/{id}")
    public Object getRoleById(@ApiParam("权限类别表_id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("SysPermissionCategory_id");
            }
            SysPermissionCategory sysPermissionCategory = ISysPermissionCategoryService.getById(id);
            return new CommonResult().success(sysPermissionCategory);
        } catch (Exception e) {
            log.error("sys_permission_category表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除SysPermissionCategory表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "sys", REMARK = "批量删除SysPermissionCategory表")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISysPermissionCategoryService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


}
