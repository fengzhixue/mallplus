package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildingCommunity;
import com.zscat.mallplus.build.entity.BuildingFloor;
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
 * 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-11-27
 */
@Slf4j
@RestController
@RequestMapping("/building/floor")
public class BuildingFloorController {

    @Resource
    private com.zscat.mallplus.build.service.IBuildingFloorService IBuildingFloorService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有楼房表列表")
    @ApiOperation("根据条件查询所有楼房表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('building:floor:read')")
    public Object getBuildingFloorByPage(BuildingFloor entity,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildingFloorService.page(new Page<BuildingFloor>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有楼房表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存楼房表")
    @ApiOperation("保存楼房表")
    @PostMapping(value = "/create")

    public Object saveBuildingFloor(@RequestBody BuildingFloor entity) {
        try {
            if (ValidatorUtils.empty(entity.getCommunityId())) {
                return new CommonResult().failed("请选择小区");
            }
            entity.setCreateTime(new Date());
            if (IBuildingFloorService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存楼房表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新楼房表")
    @ApiOperation("更新楼房表")
    @PostMapping(value = "/update/{id}")

    public Object updateBuildingFloor(@RequestBody BuildingFloor entity) {
        try {
            if (IBuildingFloorService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新楼房表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除楼房表")
    @ApiOperation("删除楼房表")
    @GetMapping(value = "/delete/{id}")

    public Object deleteBuildingFloor(@ApiParam("楼房表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("楼房表id");
            }
            if (IBuildingFloorService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除楼房表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给楼房表分配楼房表")
    @ApiOperation("查询楼房表明细")
    @GetMapping(value = "/{id}")

    public Object getBuildingFloorById(@ApiParam("楼房表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("楼房表id");
            }
            BuildingFloor coupon = IBuildingFloorService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询楼房表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除楼房表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "build", REMARK = "批量删除楼房表")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildingFloorService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildingFloor entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildingFloor> personList = IBuildingFloorService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出楼房数据", "楼房数据", BuildingCommunity.class, "导出楼房数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildingFloor> personList = EasyPoiUtils.importExcel(file, BuildingFloor.class);
        IBuildingFloorService.saveBatch(personList);
    }
}



