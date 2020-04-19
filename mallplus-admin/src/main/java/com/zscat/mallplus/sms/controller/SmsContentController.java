package com.zscat.mallplus.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sms.entity.SmsContent;
import com.zscat.mallplus.sms.service.ISmsContentService;
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
 * @author mallplus
 * @date 2019-12-07
 * 短信模版
 */
@Slf4j
@RestController
@RequestMapping("/sms/smsContent")
public class SmsContentController {

    @Resource
    private ISmsContentService ISmsContentService;

    @SysLog(MODULE = "ms", REMARK = "根据条件查询所有短信模版列表")
    @ApiOperation("根据条件查询所有短信模版列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sms:smsContent:read')")
    public Object getSmsContentByPage(SmsContent entity,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsContentService.page(new Page<SmsContent>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有短信模版列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ms", REMARK = "保存短信模版")
    @ApiOperation("保存短信模版")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sms:smsContent:create')")
    public Object saveSmsContent(@RequestBody SmsContent entity) {
        try {
            entity.setCreateTime(new Date());
            if (ISmsContentService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存短信模版：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ms", REMARK = "更新短信模版")
    @ApiOperation("更新短信模版")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sms:smsContent:update')")
    public Object updateSmsContent(@RequestBody SmsContent entity) {
        try {
            if (ISmsContentService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新短信模版：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ms", REMARK = "删除短信模版")
    @ApiOperation("删除短信模版")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sms:smsContent:delete')")
    public Object deleteSmsContent(@ApiParam("短信模版id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("短信模版id");
            }
            if (ISmsContentService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除短信模版：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ms", REMARK = "给短信模版分配短信模版")
    @ApiOperation("查询短信模版明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sms:smsContent:read')")
    public Object getSmsContentById(@ApiParam("短信模版id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("短信模版id");
            }
            SmsContent coupon = ISmsContentService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询短信模版明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除短信模版")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "ms", REMARK = "批量删除短信模版")
    @PreAuthorize("hasAuthority('sms:smsContent:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsContentService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "ms", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, SmsContent entity) {
        // 模拟从数据库获取需要导出的数据
        List<SmsContent> personList = ISmsContentService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", SmsContent.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "ms", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<SmsContent> personList = EasyPoiUtils.importExcel(file, SmsContent.class);
        ISmsContentService.saveBatch(personList);
    }
}


