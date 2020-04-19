package com.zscat.mallplus.oms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.oms.entity.OmsCompanyAddress;
import com.zscat.mallplus.oms.service.IOmsCompanyAddressService;
import com.zscat.mallplus.util.EasyPoiUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
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
 * @author mallplus
 * @date 2019-12-07
 * 发货地址
 */
@Slf4j
@Api(tags = "oms", description = "发货地址列表")
@RestController
@RequestMapping("/oms/omsCompanyAddress")
public class OmsCompanyAddressController {

    @Resource
    private IOmsCompanyAddressService IOmsCompanyAddressService;

    @SysLog(MODULE = "oms", REMARK = "根据条件查询所有发货地址列表")
    @ApiOperation("根据条件查询所有发货地址列表")
    @GetMapping(value = "/list")
    public Object getOmsCompanyAddressByPage(OmsCompanyAddress entity,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            if (ValidatorUtils.notEmpty(entity.getName())) {
                return new CommonResult().success(IOmsCompanyAddressService.page(new Page<OmsCompanyAddress>(pageNum, pageSize), new QueryWrapper<OmsCompanyAddress>(new OmsCompanyAddress()).like("name", entity.getName())));
            }
            return new CommonResult().success(IOmsCompanyAddressService.page(new Page<OmsCompanyAddress>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有发货地址列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "保存发货地址")
    @ApiOperation("保存发货地址")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('oms:omsCompanyAddress:create')")
    public Object saveOmsCompanyAddress(@RequestBody OmsCompanyAddress entity) {
        try {

            if (IOmsCompanyAddressService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存发货地址：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "更新发货地址")
    @ApiOperation("更新发货地址")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('oms:omsCompanyAddress:update')")
    public Object updateOmsCompanyAddress(@RequestBody OmsCompanyAddress entity) {
        try {
            if (IOmsCompanyAddressService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新发货地址：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "删除发货地址")
    @ApiOperation("删除发货地址")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('oms:omsCompanyAddress:delete')")
    public Object deleteOmsCompanyAddress(@ApiParam("发货地址id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("发货地址id");
            }
            if (IOmsCompanyAddressService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除发货地址：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "给发货地址分配发货地址")
    @ApiOperation("查询发货地址明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('oms:omsCompanyAddress:read')")
    public Object getOmsCompanyAddressById(@ApiParam("发货地址id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("发货地址id");
            }
            OmsCompanyAddress coupon = IOmsCompanyAddressService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询发货地址明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除发货地址")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "oms", REMARK = "批量删除发货地址")
    @PreAuthorize("hasAuthority('oms:omsCompanyAddress:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IOmsCompanyAddressService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "oms", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, OmsCompanyAddress entity) {
        // 模拟从数据库获取需要导出的数据
        List<OmsCompanyAddress> personList = IOmsCompanyAddressService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", OmsCompanyAddress.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "oms", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<OmsCompanyAddress> personList = EasyPoiUtils.importExcel(file, OmsCompanyAddress.class);
        IOmsCompanyAddressService.saveBatch(personList);
    }
}


