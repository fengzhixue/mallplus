package com.zscat.mallplus.ums.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.ums.entity.UmsIntegrationChangeHistory;
import com.zscat.mallplus.ums.service.IUmsIntegrationChangeHistoryService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 积分变化历史记录表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "UmsIntegrationChangeHistoryController", description = "积分变化历史记录表管理")
@RequestMapping("/ums/UmsIntegrationChangeHistory")
public class UmsIntegrationChangeHistoryController {
    @Resource
    private IUmsIntegrationChangeHistoryService IUmsIntegrationChangeHistoryService;

    @SysLog(MODULE = "ums", REMARK = "根据条件查询所有积分变化历史记录表列表")
    @ApiOperation("根据条件查询所有积分变化历史记录表列表")
    @GetMapping(value = "/list")
    public Object getUmsIntegrationChangeHistoryByPage(UmsIntegrationChangeHistory entity,
                                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IUmsIntegrationChangeHistoryService.page(new Page<UmsIntegrationChangeHistory>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有积分变化历史记录表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "ums", REMARK = "给积分变化历史记录表分配积分变化历史记录表")
    @ApiOperation("查询积分变化历史记录表明细")
    @GetMapping(value = "/{id}")
    public Object getUmsIntegrationChangeHistoryById(@ApiParam("积分变化历史记录表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("积分变化历史记录表id");
            }
            UmsIntegrationChangeHistory coupon = IUmsIntegrationChangeHistoryService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询积分变化历史记录表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }


}
