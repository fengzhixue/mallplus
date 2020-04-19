package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildGroupMember;
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
@RequestMapping("/build/groupMember")
public class BuildGroupMemberController {


    @Resource
    private com.zscat.mallplus.build.service.IBuildGroupMemberService IBuildGroupMemberService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有拼团列表")
    @ApiOperation("根据条件查询所有拼团列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('build:groupMember:read')")
    public Object getBuildGroupMemberByPage(BuildGroupMember entity,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildGroupMemberService.page(new Page<BuildGroupMember>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有拼团列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存拼团")
    @ApiOperation("保存拼团")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('build:groupMember:create')")
    public Object saveBuildGroupMember(@RequestBody BuildGroupMember entity) {
        try {
            entity.setStatus(1);
            if (IBuildGroupMemberService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存拼团：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新拼团")
    @ApiOperation("更新拼团")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('build:groupMember:update')")
    public Object updateBuildGroupMember(@RequestBody BuildGroupMember entity) {
        try {
            if (IBuildGroupMemberService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新拼团：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除拼团")
    @ApiOperation("删除拼团")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('build:groupMember:delete')")
    public Object deleteBuildGroupMember(@ApiParam("拼团id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("拼团id");
            }
            if (IBuildGroupMemberService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除拼团：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给拼团分配拼团")
    @ApiOperation("查询拼团明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('build:groupMember:read')")
    public Object getBuildGroupMemberById(@ApiParam("拼团id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("拼团id");
            }
            BuildGroupMember coupon = IBuildGroupMemberService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询拼团明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除拼团")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "build", REMARK = "批量删除拼团")
    @PreAuthorize("hasAuthority('build:groupMember:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildGroupMemberService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildGroupMember entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildGroupMember> personList = IBuildGroupMemberService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", BuildGroupMember.class, "导出社区数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildGroupMember> personList = EasyPoiUtils.importExcel(file, BuildGroupMember.class);
        IBuildGroupMemberService.saveBatch(personList);
    }
}

