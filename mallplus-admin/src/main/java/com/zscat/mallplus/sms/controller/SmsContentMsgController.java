package com.zscat.mallplus.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sms.entity.SmsContentMsg;
import com.zscat.mallplus.sms.service.ISmsContentMsgService;
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
 * @date 2019-12-17
 * 短信记录
 */
@Slf4j
@RestController
@RequestMapping("/sms/smsContentMsg")
public class SmsContentMsgController {

    @Resource
    private ISmsContentMsgService ISmsContentMsgService;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有短信记录列表")
    @ApiOperation("根据条件查询所有短信记录列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sms:smsContentMsg:read')")
    public Object getSmsContentMsgByPage(SmsContentMsg entity,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsContentMsgService.page(new Page<SmsContentMsg>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有短信记录列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "保存短信记录")
    @ApiOperation("保存短信记录")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sms:smsContentMsg:create')")
    public Object saveSmsContentMsg(@RequestBody SmsContentMsg entity) {
        try {

            if (ISmsContentMsgService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存短信记录：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新短信记录")
    @ApiOperation("更新短信记录")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sms:smsContentMsg:update')")
    public Object updateSmsContentMsg(@RequestBody SmsContentMsg entity) {
        try {
            if (ISmsContentMsgService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新短信记录：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除短信记录")
    @ApiOperation("删除短信记录")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sms:smsContentMsg:delete')")
    public Object deleteSmsContentMsg(@ApiParam("短信记录id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("短信记录id");
            }
            if (ISmsContentMsgService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除短信记录：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给短信记录分配短信记录")
    @ApiOperation("查询短信记录明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sms:smsContentMsg:read')")
    public Object getSmsContentMsgById(@ApiParam("短信记录id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("短信记录id");
            }
            SmsContentMsg coupon = ISmsContentMsgService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询短信记录明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除短信记录")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "sms", REMARK = "批量删除短信记录")
    @PreAuthorize("hasAuthority('sms:smsContentMsg:delete')")
    public Object deleteBatch(@RequestParam("ids") List
            <Long> ids) {
        boolean count = ISmsContentMsgService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "sms", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, SmsContentMsg entity) {
        // 模拟从数据库获取需要导出的数据
        List<SmsContentMsg> personList = ISmsContentMsgService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", SmsContentMsg.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "sms", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<SmsContentMsg> personList = EasyPoiUtils.importExcel(file, SmsContentMsg.class);
        ISmsContentMsgService.saveBatch(personList);
    }
}


