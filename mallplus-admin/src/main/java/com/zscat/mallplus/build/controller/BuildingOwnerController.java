package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildingCommunity;
import com.zscat.mallplus.build.entity.BuildingOwner;
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
 * 业主成员表 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
@Slf4j
@RestController
@RequestMapping("/building/owner")
public class BuildingOwnerController {

    @Resource
    private com.zscat.mallplus.build.service.IBuildingOwnerService IBuildingOwnerService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有业主表列表")
    @ApiOperation("根据条件查询所有业主表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('building:owner:read')")
    public Object getBuildingOwnerByPage(BuildingOwner entity,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildingOwnerService.page(new Page<BuildingOwner>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有业主表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存业主表")
    @ApiOperation("保存业主表")
    @PostMapping(value = "/create")

    public Object saveBuildingOwner(@RequestBody BuildingOwner entity) {
        try {
            if (ValidatorUtils.empty(entity.getRoomId())) {
                return new CommonResult().failed("请选择房屋");
            }
            entity.setCreateTime(new Date());
            if (entity.getType() == 2 || entity.getType() == 3) {
                List<BuildingOwner> list = IBuildingOwnerService.list(new QueryWrapper<BuildingOwner>().eq("room_id", entity.getRoomId()).eq("type", 1).orderByAsc("id"));
                if (list != null && list.size() > 0) {
                    entity.setOwnerId(list.get(0).getId());
                } else {
                    return new CommonResult().failed("请先添加一个业主");
                }
            }
            if (IBuildingOwnerService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存业主表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新业主表")
    @ApiOperation("更新业主表")
    @PostMapping(value = "/update/{id}")

    public Object updateBuildingOwner(@RequestBody BuildingOwner entity) {
        try {
            if (IBuildingOwnerService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新业主表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除业主表")
    @ApiOperation("删除业主表")
    @GetMapping(value = "/delete/{id}")

    public Object deleteBuildingOwner(@ApiParam("业主表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("业主表id");
            }
            if (IBuildingOwnerService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除业主表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给业主表分配业主表")
    @ApiOperation("查询业主表明细")
    @GetMapping(value = "/{id}")

    public Object getBuildingOwnerById(@ApiParam("业主表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("业主表id");
            }
            BuildingOwner coupon = IBuildingOwnerService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询业主表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除业主表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "build", REMARK = "批量删除业主表")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildingOwnerService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildingOwner entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildingOwner> personList = IBuildingOwnerService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出业主数据", "业主数据", BuildingCommunity.class, "导出业主数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildingOwner> personList = EasyPoiUtils.importExcel(file, BuildingOwner.class);
        IBuildingOwnerService.saveBatch(personList);
    }
}



