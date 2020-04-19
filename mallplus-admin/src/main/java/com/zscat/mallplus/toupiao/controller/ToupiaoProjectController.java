package com.zscat.mallplus.toupiao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.toupiao.entity.ToupiaoProject;
import com.zscat.mallplus.toupiao.service.IToupiaoProjectService;
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
 * 投票项目
 */
@Slf4j
@RestController
@RequestMapping("/toupiao/toupiaoProject")
public class ToupiaoProjectController {

    @Resource
    private IToupiaoProjectService IToupiaoProjectService;

    @SysLog(MODULE = "toupiao", REMARK = "根据条件查询所有投票项目列表")
    @ApiOperation("根据条件查询所有投票项目列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('toupiao:toupiaoProject:read')")
    public Object getToupiaoProjectByPage(ToupiaoProject entity,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IToupiaoProjectService.page(new Page<ToupiaoProject>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有投票项目列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "toupiao", REMARK = "保存投票项目")
    @ApiOperation("保存投票项目")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('toupiao:toupiaoProject:create')")
    public Object saveToupiaoProject(@RequestBody ToupiaoProject entity) {
        try {
            entity.setCreateTime(new Date());
            if (IToupiaoProjectService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存投票项目：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "toupiao", REMARK = "更新投票项目")
    @ApiOperation("更新投票项目")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('toupiao:toupiaoProject:update')")
    public Object updateToupiaoProject(@RequestBody ToupiaoProject entity) {
        try {
            if (IToupiaoProjectService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新投票项目：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "toupiao", REMARK = "删除投票项目")
    @ApiOperation("删除投票项目")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('toupiao:toupiaoProject:delete')")
    public Object deleteToupiaoProject(@ApiParam("投票项目id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("投票项目id");
            }
            if (IToupiaoProjectService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除投票项目：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "toupiao", REMARK = "给投票项目分配投票项目")
    @ApiOperation("查询投票项目明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('toupiao:toupiaoProject:read')")
    public Object getToupiaoProjectById(@ApiParam("投票项目id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("投票项目id");
            }
            ToupiaoProject coupon = IToupiaoProjectService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询投票项目明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除投票项目")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "toupiao", REMARK = "批量删除投票项目")
    @PreAuthorize("hasAuthority('toupiao:toupiaoProject:delete')")
    public Object deleteBatch(@RequestParam("ids") List
            <Long> ids) {
        boolean count = IToupiaoProjectService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "toupiao", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, ToupiaoProject entity) {
        // 模拟从数据库获取需要导出的数据
        List<ToupiaoProject> personList = IToupiaoProjectService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", ToupiaoProject.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "toupiao", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<ToupiaoProject> personList = EasyPoiUtils.importExcel(file, ToupiaoProject.class);
        IToupiaoProjectService.saveBatch(personList);
    }
}


