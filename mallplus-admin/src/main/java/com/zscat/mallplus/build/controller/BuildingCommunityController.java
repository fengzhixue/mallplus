package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildingCommunity;
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
 * 小区 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
@Slf4j
@RestController
@RequestMapping("/building/community")
public class BuildingCommunityController {

    @Resource
    private com.zscat.mallplus.build.service.IBuildingCommunityService IBuildingCommunityService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有小区表列表")
    @ApiOperation("根据条件查询所有小区表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('building:community:read')")
    public Object getBuildingCommunityByPage(BuildingCommunity entity,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildingCommunityService.page(new Page<BuildingCommunity>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有小区表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存小区表")
    @ApiOperation("保存小区表")
    @PostMapping(value = "/create")

    public Object saveBuildingCommunity(@RequestBody BuildingCommunity entity) {
        try {
            entity.setCreateTime(new Date());
            entity.setStatus(3);
            if (ValidatorUtils.empty(entity.getCompanyId())) {
                //     entity.setCompanyId(UserUtils.getCurrentMember().getStoreId());
            }
            if (ValidatorUtils.empty(entity.getCompanyId())) {
                return new CommonResult().failed("请选择物业攻啊");
            }
            if (IBuildingCommunityService.saveCommunity(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存小区表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新小区表")
    @ApiOperation("更新小区表")
    @PostMapping(value = "/update/{id}")

    public Object updateBuildingCommunity(@RequestBody BuildingCommunity entity) {
        try {
            if (IBuildingCommunityService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新小区表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除小区表")
    @ApiOperation("删除小区表")
    @GetMapping(value = "/delete/{id}")

    public Object deleteBuildingCommunity(@ApiParam("小区表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("小区表id");
            }
            if (IBuildingCommunityService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除小区表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给小区表分配小区表")
    @ApiOperation("查询小区表明细")
    @GetMapping(value = "/{id}")

    public Object getBuildingCommunityById(@ApiParam("小区表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("小区表id");
            }
            BuildingCommunity coupon = IBuildingCommunityService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询小区表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除小区表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "build", REMARK = "批量删除小区表")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildingCommunityService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildingCommunity entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildingCommunity> personList = IBuildingCommunityService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", BuildingCommunity.class, "导出社区数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildingCommunity> personList = EasyPoiUtils.importExcel(file, BuildingCommunity.class);
        IBuildingCommunityService.saveBatch(personList);
    }
}


