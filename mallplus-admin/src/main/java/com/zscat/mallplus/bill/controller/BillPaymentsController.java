package com.zscat.mallplus.bill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.bill.entity.BillPayments;
import com.zscat.mallplus.bill.service.IBillPaymentsService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 支付单表 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@Slf4j
@RestController
@RequestMapping("/bill/billPayments")
public class BillPaymentsController {

    @Resource
    private IBillPaymentsService billAftersalesService;

    @SysLog(MODULE = "bill", REMARK = "根据条件查询所有支付单表")
    @ApiOperation("根据条件查询所有支付单表")
    @GetMapping(value = "/list")
    public Object getCmsHelpByPage(BillPayments entity,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(billAftersalesService.page(new Page<BillPayments>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有帮助表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }
}

