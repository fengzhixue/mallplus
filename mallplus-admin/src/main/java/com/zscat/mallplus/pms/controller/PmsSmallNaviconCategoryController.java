package com.zscat.mallplus.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.pms.entity.PmsSmallNaviconCategory;
import com.zscat.mallplus.pms.service.IPmsSmallNaviconCategoryService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = "PmsSmallNaviconCategoryController", description = "管理")
@RequestMapping("/pms/smallNaviconCategory")
public class PmsSmallNaviconCategoryController {
    @Resource
    private IPmsSmallNaviconCategoryService IPmsSmallNaviconCategoryService;

    @SysLog(MODULE = "pms", REMARK = "查询pms_small_navicon_category表")
    @ApiOperation("查询pms_small_navicon_category表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsSmallNaviconCategory:read')")
    public Object getPmsSmallNaviconCategoryByPage(PmsSmallNaviconCategory entity,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsSmallNaviconCategoryService.page(new Page<PmsSmallNaviconCategory>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("分页获取pms_small_navicon_category列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存pms_small_navicon_category表")
    @ApiOperation("保存pms_small_navicon_category表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsSmallNaviconCategory:create')")
    public Object saveSmallNaviconCategory(@RequestBody PmsSmallNaviconCategory entity) {
        try {
            if (IPmsSmallNaviconCategoryService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存pms_small_navicon_category表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新pms_small_navicon_category")
    @ApiOperation("更新pms_small_navicon_category")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsSmallNaviconCategory:update')")
    public Object updateSmallNaviconCategory(@RequestBody PmsSmallNaviconCategory entity) {
        try {
            if (IPmsSmallNaviconCategoryService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除pms_small_navicon_category数据")
    @ApiOperation("删除小程序首页nav管理数据")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsSmallNaviconCategory:delete')")
    public Object deleteRole(@ApiParam("小程序首页nav管理_id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("PmsSmallNaviconCategory_id");
            }
            if (IPmsSmallNaviconCategoryService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除小程序首页nav管理数据：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "根据ID查询pms_small_navicon_category")
    @ApiOperation("根据ID查询pms_small_navicon_category")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsSmallNaviconCategory:read')")
    public Object getRoleById(@ApiParam("小程序首页nav管理_id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("PmsSmallNaviconCategory_id");
            }
            PmsSmallNaviconCategory pmsSmallNaviconCategory = IPmsSmallNaviconCategoryService.getById(id);
            return new CommonResult().success(pmsSmallNaviconCategory);
        } catch (Exception e) {
            log.error("pms_small_navicon_category表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除PmsSmallNaviconCategory表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除PmsSmallNaviconCategory表")
    @PreAuthorize("hasAuthority('pms:PmsSmallNaviconCategory:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsSmallNaviconCategoryService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


}
