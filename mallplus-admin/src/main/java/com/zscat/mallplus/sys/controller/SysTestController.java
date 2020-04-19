package com.zscat.mallplus.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sys.entity.SysTest;
import com.zscat.mallplus.sys.service.ISysTestService;
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
 * @author mallplus
 * @date 2019-12-02
 * 测试
 */
@Slf4j
@RestController
@RequestMapping("/sys/sysTest")
public class SysTestController {

    @Resource
    private ISysTestService ISysTestService;

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有测试列表")
    @ApiOperation("根据条件查询所有测试列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sys:sysTest:read')")
    public Object getSysTestByPage(SysTest entity,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISysTestService.page(new Page<SysTest>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有测试列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "保存测试")
    @ApiOperation("保存测试")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sys:sysTest:create')")
    public Object saveSysTest(@RequestBody SysTest entity) {
        try {

            if (ISysTestService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存测试：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "更新测试")
    @ApiOperation("更新测试")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sys:sysTest:update')")
    public Object updateSysTest(@RequestBody SysTest entity) {
        try {
            if (ISysTestService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新测试：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "删除测试")
    @ApiOperation("删除测试")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sys:sysTest:delete')")
    public Object deleteSysTest(@ApiParam("测试id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("测试id");
            }
            if (ISysTestService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除测试：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "给测试分配测试")
    @ApiOperation("查询测试明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sys:sysTest:read')")
    public Object getSysTestById(@ApiParam("测试id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("测试id");
            }
            SysTest coupon = ISysTestService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询测试明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除测试")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "sys", REMARK = "批量删除测试")
    @PreAuthorize("hasAuthority('sys:sysTest:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISysTestService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "sys", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, SysTest entity) {
        // 模拟从数据库获取需要导出的数据
        List<SysTest> personList = ISysTestService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", SysTest.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "sys", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<SysTest> personList = EasyPoiUtils.importExcel(file, SysTest.class);
        ISysTestService.saveBatch(personList);
    }
}


