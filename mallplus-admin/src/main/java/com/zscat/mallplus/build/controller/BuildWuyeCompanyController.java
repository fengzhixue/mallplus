package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildWuyeCompany;
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
@RequestMapping("/build/wuyeCompany")
public class BuildWuyeCompanyController {

    @Resource
    private com.zscat.mallplus.build.service.IBuildWuyeCompanyService IBuildWuyeCompanyService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有物业公司表列表")
    @ApiOperation("根据条件查询所有物业公司表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('build:wuyeCompany:read')")
    public Object getBuildWuyeCompanyByPage(BuildWuyeCompany entity,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildWuyeCompanyService.page(new Page<BuildWuyeCompany>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有物业公司表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存物业公司表")
    @ApiOperation("保存物业公司表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('build:wuyeCompany:create')")
    public Object saveBuildWuyeCompany(@RequestBody BuildWuyeCompany entity) {
        try {
            entity.setStatus("3");
            if (IBuildWuyeCompanyService.saveCompany(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存物业公司表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新物业公司表")
    @ApiOperation("更新物业公司表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('build:wuyeCompany:update')")
    public Object updateBuildWuyeCompany(@RequestBody BuildWuyeCompany entity) {
        try {
            if (IBuildWuyeCompanyService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新物业公司表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除物业公司表")
    @ApiOperation("删除物业公司表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('build:wuyeCompany:delete')")
    public Object deleteBuildWuyeCompany(@ApiParam("物业公司表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("物业公司表id");
            }
            if (IBuildWuyeCompanyService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除物业公司表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给物业公司表分配物业公司表")
    @ApiOperation("查询物业公司表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('build:wuyeCompany:read')")
    public Object getBuildWuyeCompanyById(@ApiParam("物业公司表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("物业公司表id");
            }
            BuildWuyeCompany coupon = IBuildWuyeCompanyService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询物业公司表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除物业公司表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "build", REMARK = "批量删除物业公司表")
    @PreAuthorize("hasAuthority('build:wuyeCompany:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildWuyeCompanyService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildWuyeCompany entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildWuyeCompany> personList = IBuildWuyeCompanyService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", BuildWuyeCompany.class, "导出社区数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildWuyeCompany> personList = EasyPoiUtils.importExcel(file, BuildWuyeCompany.class);
        IBuildWuyeCompanyService.saveBatch(personList);
    }
}


