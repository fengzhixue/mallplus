package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildRepair;
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
 * 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-12-02
 */
@Slf4j
@RestController
@RequestMapping("/build/repair")
public class BuildRepairController {

    @Resource
    private com.zscat.mallplus.build.service.IBuildRepairService IBuildRepairService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有报修表列表")
    @ApiOperation("根据条件查询所有报修表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('build:repair:read')")
    public Object getBuildRepairByPage(BuildRepair entity,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildRepairService.page(new Page<BuildRepair>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有报修表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存报修表")
    @ApiOperation("保存报修表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('build:repair:create')")
    public Object saveBuildRepair(@RequestBody BuildRepair entity) {
        try {
            if (IBuildRepairService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存报修表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新报修表")
    @ApiOperation("更新报修表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('build:repair:update')")
    public Object updateBuildRepair(@RequestBody BuildRepair entity) {
        try {
            if (IBuildRepairService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新报修表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除报修表")
    @ApiOperation("删除报修表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('build:repair:delete')")
    public Object deleteBuildRepair(@ApiParam("报修表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("报修表id");
            }
            if (IBuildRepairService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除报修表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给报修表分配报修表")
    @ApiOperation("查询报修表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('build:repair:read')")
    public Object getBuildRepairById(@ApiParam("报修表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("报修表id");
            }
            BuildRepair coupon = IBuildRepairService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询报修表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除报修表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "build", REMARK = "批量删除报修表")
    @PreAuthorize("hasAuthority('build:repair:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildRepairService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildRepair entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildRepair> personList = IBuildRepairService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", BuildRepair.class, "导出社区数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildRepair> personList = EasyPoiUtils.importExcel(file, BuildRepair.class);
        IBuildRepairService.saveBatch(personList);
    }
}


