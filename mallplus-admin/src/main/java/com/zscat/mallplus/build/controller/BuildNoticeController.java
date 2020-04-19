package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildNotice;
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
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-12-02
 */
@Slf4j
@RestController
@RequestMapping("/build/notice")
public class BuildNoticeController {


    @Resource
    private com.zscat.mallplus.build.service.IBuildNoticeService IBuildNoticeService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有公告表列表")
    @ApiOperation("根据条件查询所有公告表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('build:notice:read')")
    public Object getBuildNoticeByPage(BuildNotice entity,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildNoticeService.page(new Page<BuildNotice>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有公告表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存公告表")
    @ApiOperation("保存公告表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('build:notice:create')")
    public Object saveBuildNotice(@RequestBody BuildNotice entity) {
        try {
            entity.setCreateTime(new Date());
            if (IBuildNoticeService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存公告表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新公告表")
    @ApiOperation("更新公告表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('build:notice:update')")
    public Object updateBuildNotice(@RequestBody BuildNotice entity) {
        try {
            if (IBuildNoticeService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新公告表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除公告表")
    @ApiOperation("删除公告表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('build:notice:delete')")
    public Object deleteBuildNotice(@ApiParam("公告表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("公告表id");
            }
            if (IBuildNoticeService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除公告表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给公告表分配公告表")
    @ApiOperation("查询公告表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('build:notice:read')")
    public Object getBuildNoticeById(@ApiParam("公告表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("公告表id");
            }
            BuildNotice coupon = IBuildNoticeService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询公告表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除公告表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "build", REMARK = "批量删除公告表")
    @PreAuthorize("hasAuthority('build:notice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildNoticeService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildNotice entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildNotice> personList = IBuildNoticeService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", BuildNotice.class, "导出社区数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildNotice> personList = EasyPoiUtils.importExcel(file, BuildNotice.class);
        IBuildNoticeService.saveBatch(personList);
    }
}

