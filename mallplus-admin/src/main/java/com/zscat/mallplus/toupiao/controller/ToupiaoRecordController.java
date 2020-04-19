package com.zscat.mallplus.toupiao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.toupiao.entity.ToupiaoRecord;
import com.zscat.mallplus.toupiao.service.IToupiaoRecordService;
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
 * @date 2019-12-17
 * 投票记录
 */
@Slf4j
@RestController
@RequestMapping("/toupiao/toupiaoRecord")
public class ToupiaoRecordController {

    @Resource
    private IToupiaoRecordService IToupiaoRecordService;

    @SysLog(MODULE = "toupiao", REMARK = "根据条件查询所有投票记录列表")
    @ApiOperation("根据条件查询所有投票记录列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('toupiao:toupiaoRecord:read')")
    public Object getToupiaoRecordByPage(ToupiaoRecord entity,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IToupiaoRecordService.page(new Page<ToupiaoRecord>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有投票记录列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "toupiao", REMARK = "保存投票记录")
    @ApiOperation("保存投票记录")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('toupiao:toupiaoRecord:create')")
    public Object saveToupiaoRecord(@RequestBody ToupiaoRecord entity) {
        try {
            entity.setCreateTime(new Date());
            if (IToupiaoRecordService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存投票记录：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "toupiao", REMARK = "更新投票记录")
    @ApiOperation("更新投票记录")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('toupiao:toupiaoRecord:update')")
    public Object updateToupiaoRecord(@RequestBody ToupiaoRecord entity) {
        try {
            if (IToupiaoRecordService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新投票记录：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "toupiao", REMARK = "删除投票记录")
    @ApiOperation("删除投票记录")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('toupiao:toupiaoRecord:delete')")
    public Object deleteToupiaoRecord(@ApiParam("投票记录id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("投票记录id");
            }
            if (IToupiaoRecordService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除投票记录：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "toupiao", REMARK = "给投票记录分配投票记录")
    @ApiOperation("查询投票记录明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('toupiao:toupiaoRecord:read')")
    public Object getToupiaoRecordById(@ApiParam("投票记录id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("投票记录id");
            }
            ToupiaoRecord coupon = IToupiaoRecordService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询投票记录明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除投票记录")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "toupiao", REMARK = "批量删除投票记录")
    @PreAuthorize("hasAuthority('toupiao:toupiaoRecord:delete')")
    public Object deleteBatch(@RequestParam("ids") List
            <Long> ids) {
        boolean count = IToupiaoRecordService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "toupiao", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, ToupiaoRecord entity) {
        // 模拟从数据库获取需要导出的数据
        List<ToupiaoRecord> personList = IToupiaoRecordService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", ToupiaoRecord.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "toupiao", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<ToupiaoRecord> personList = EasyPoiUtils.importExcel(file, ToupiaoRecord.class);
        IToupiaoRecordService.saveBatch(personList);
    }
}


