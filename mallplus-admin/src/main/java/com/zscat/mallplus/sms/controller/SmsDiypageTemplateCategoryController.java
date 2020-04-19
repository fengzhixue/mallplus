package com.zscat.mallplus.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sms.entity.SmsDiypageTemplateCategory;
import com.zscat.mallplus.sms.service.ISmsDiypageTemplateCategoryService;
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
 * @date 2019-12-04
 * 页面模版
 */
@Slf4j
@RestController
@RequestMapping("/sms/smsDiypageTemplateCategory")
public class SmsDiypageTemplateCategoryController {

    @Resource
    private ISmsDiypageTemplateCategoryService ISmsDiypageTemplateCategoryService;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有页面模版列表")
    @ApiOperation("根据条件查询所有页面模版列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sms:smsDiypageTemplateCategory:read')")
    public Object getSmsDiypageTemplateCategoryByPage(SmsDiypageTemplateCategory entity,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsDiypageTemplateCategoryService.page(new Page<SmsDiypageTemplateCategory>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有页面模版列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "保存页面模版")
    @ApiOperation("保存页面模版")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sms:smsDiypageTemplateCategory:create')")
    public Object saveSmsDiypageTemplateCategory(@RequestBody SmsDiypageTemplateCategory entity) {
        try {

            if (ISmsDiypageTemplateCategoryService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存页面模版：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新页面模版")
    @ApiOperation("更新页面模版")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sms:smsDiypageTemplateCategory:update')")
    public Object updateSmsDiypageTemplateCategory(@RequestBody SmsDiypageTemplateCategory entity) {
        try {
            if (ISmsDiypageTemplateCategoryService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新页面模版：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除页面模版")
    @ApiOperation("删除页面模版")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sms:smsDiypageTemplateCategory:delete')")
    public Object deleteSmsDiypageTemplateCategory(@ApiParam("页面模版id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("页面模版id");
            }
            if (ISmsDiypageTemplateCategoryService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除页面模版：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给页面模版分配页面模版")
    @ApiOperation("查询页面模版明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sms:smsDiypageTemplateCategory:read')")
    public Object getSmsDiypageTemplateCategoryById(@ApiParam("页面模版id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("页面模版id");
            }
            SmsDiypageTemplateCategory coupon = ISmsDiypageTemplateCategoryService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询页面模版明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除页面模版")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "sms", REMARK = "批量删除页面模版")
    @PreAuthorize("hasAuthority('sms:smsDiypageTemplateCategory:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsDiypageTemplateCategoryService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "sms", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, SmsDiypageTemplateCategory entity) {
        // 模拟从数据库获取需要导出的数据
        List<SmsDiypageTemplateCategory> personList = ISmsDiypageTemplateCategoryService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", SmsDiypageTemplateCategory.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "sms", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<SmsDiypageTemplateCategory> personList = EasyPoiUtils.importExcel(file, SmsDiypageTemplateCategory.class);
        ISmsDiypageTemplateCategoryService.saveBatch(personList);
    }
}


