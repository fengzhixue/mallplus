package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildWuyePrice;
import com.zscat.mallplus.build.entity.BuildingRoom;
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
 * @since 2019-12-02
 */
@Slf4j
@RestController
@RequestMapping("/build/wuyePrice")
public class BuildWuyePriceController {

    @Resource
    private com.zscat.mallplus.build.service.IBuildWuyePriceService IBuildWuyePriceService;
    @Resource
    private com.zscat.mallplus.build.service.IBuildingRoomService IBuildingRoomService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有费用表列表")
    @ApiOperation("根据条件查询所有费用表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('build:wuyePrice:read')")
    public Object getBuildWuyePriceByPage(BuildWuyePrice entity,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildWuyePriceService.page(new Page<BuildWuyePrice>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有费用表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存费用表")
    @ApiOperation("保存费用表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('build:wuyePrice:create')")
    public Object saveBuildWuyePrice(@RequestBody BuildWuyePrice entity) {
        try {
            if (ValidatorUtils.empty(entity.getAmount()) || ValidatorUtils.empty(entity.getPrice())) {
                return new CommonResult().failed("请输入价格");
            }
            BuildingRoom room = IBuildingRoomService.getById(entity.getRoomId());
            if (room != null && room.getRoomDesc() != null) {
                entity.setRoomDesc(room.getRoomDesc());
            }
            entity.setCreateDate(new Date());
            entity.setMoneys(entity.getAmount().multiply(entity.getPrice()));
            if (IBuildWuyePriceService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存费用表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新费用表")
    @ApiOperation("更新费用表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('build:wuyePrice:update')")
    public Object updateBuildWuyePrice(@RequestBody BuildWuyePrice entity) {
        try {
            if (IBuildWuyePriceService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新费用表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除费用表")
    @ApiOperation("删除费用表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('build:wuyePrice:delete')")
    public Object deleteBuildWuyePrice(@ApiParam("费用表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("费用表id");
            }
            if (IBuildWuyePriceService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除费用表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给费用表分配费用表")
    @ApiOperation("查询费用表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('build:wuyePrice:read')")
    public Object getBuildWuyePriceById(@ApiParam("费用表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("费用表id");
            }
            BuildWuyePrice coupon = IBuildWuyePriceService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询费用表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除费用表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "build", REMARK = "批量删除费用表")
    @PreAuthorize("hasAuthority('build:wuyePrice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildWuyePriceService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildWuyePrice entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildWuyePrice> personList = IBuildWuyePriceService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", BuildWuyePrice.class, "导出社区数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildWuyePrice> personList = EasyPoiUtils.importExcel(file, BuildWuyePrice.class);
        IBuildWuyePriceService.saveBatch(personList);
    }
}

