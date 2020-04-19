package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildAdv;
import com.zscat.mallplus.build.service.IBuildAdvService;
import com.zscat.mallplus.util.EasyPoiUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 首页轮播广告表 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-12-02
 */
@Slf4j
@RestController
@RequestMapping("/build/adv")
public class BuildAdvController {

    @Resource
    private IBuildAdvService IBuildAdvService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有轮播广告列表")
    @ApiOperation("根据条件查询所有轮播广告列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('build:adv:read')")
    public Object getBuildAdvByPage(BuildAdv entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildAdvService.page(new Page<BuildAdv>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有轮播广告列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存轮播广告")
    @ApiOperation("保存轮播广告")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('build:adv:create')")
    public Object saveBuildAdv(@RequestBody BuildAdv entity) {
        try {
            entity.setStatus(1);
            if (IBuildAdvService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存轮播广告：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新轮播广告")
    @ApiOperation("更新轮播广告")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('build:adv:update')")
    public Object updateBuildAdv(@RequestBody BuildAdv entity) {
        try {
            if (IBuildAdvService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新轮播广告：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除轮播广告")
    @ApiOperation("删除轮播广告")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('build:adv:delete')")
    public Object deleteBuildAdv(@ApiParam("轮播广告id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("轮播广告id");
            }
            if (IBuildAdvService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除轮播广告：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给轮播广告分配轮播广告")
    @ApiOperation("查询轮播广告明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('build:adv:read')")
    public Object getBuildAdvById(@ApiParam("轮播广告id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("轮播广告id");
            }
            BuildAdv coupon = IBuildAdvService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询轮播广告明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除轮播广告")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "build", REMARK = "批量删除轮播广告")
    @PreAuthorize("hasAuthority('build:adv:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildAdvService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "build", REMARK = "修改上下线状态")
    @ApiOperation("修改上下线状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateStatus(@PathVariable Long id, Integer status) {
        BuildAdv record = new BuildAdv();
        record.setId(id);
        record.setStatus(status);
        return IBuildAdvService.updateById(record);

    }

    @SysLog(MODULE = "build", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildAdv entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildAdv> personList = IBuildAdvService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", BuildAdv.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "build", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildAdv> personList = EasyPoiUtils.importExcel(file, BuildAdv.class);
        IBuildAdvService.saveBatch(personList);
    }
}


