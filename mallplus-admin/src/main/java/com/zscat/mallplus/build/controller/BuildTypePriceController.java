package com.zscat.mallplus.build.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.build.entity.BuildTypePrice;
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
@RequestMapping("/build/typePrice")
public class BuildTypePriceController {

    @Resource
    private com.zscat.mallplus.build.service.IBuildTypePriceService IBuildTypePriceService;

    @SysLog(MODULE = "build", REMARK = "根据条件查询所有价格类型表列表")
    @ApiOperation("根据条件查询所有价格类型表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('build:typePrice:read')")
    public Object getBuildTypePriceByPage(BuildTypePrice entity,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IBuildTypePriceService.page(new Page<BuildTypePrice>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有价格类型表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "保存价格类型表")
    @ApiOperation("保存价格类型表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('build:typePrice:create')")
    public Object saveBuildTypePrice(@RequestBody BuildTypePrice entity) {
        try {
            entity.setCreateTime(new Date());
            if (IBuildTypePriceService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存价格类型表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "更新价格类型表")
    @ApiOperation("更新价格类型表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('build:typePrice:update')")
    public Object updateBuildTypePrice(@RequestBody BuildTypePrice entity) {
        try {
            if (IBuildTypePriceService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新价格类型表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "删除价格类型表")
    @ApiOperation("删除价格类型表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('build:typePrice:delete')")
    public Object deleteBuildTypePrice(@ApiParam("价格类型表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("价格类型表id");
            }
            if (IBuildTypePriceService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除价格类型表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "build", REMARK = "给价格类型表分配价格类型表")
    @ApiOperation("查询价格类型表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('build:typePrice:read')")
    public Object getBuildTypePriceById(@ApiParam("价格类型表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("价格类型表id");
            }
            BuildTypePrice coupon = IBuildTypePriceService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询价格类型表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除价格类型表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "build", REMARK = "批量删除价格类型表")
    @PreAuthorize("hasAuthority('build:typePrice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IBuildTypePriceService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, BuildTypePrice entity) {
        // 模拟从数据库获取需要导出的数据
        List<BuildTypePrice> personList = IBuildTypePriceService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", BuildTypePrice.class, "导出社区数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<BuildTypePrice> personList = EasyPoiUtils.importExcel(file, BuildTypePrice.class);
        IBuildTypePriceService.saveBatch(personList);
    }
}



