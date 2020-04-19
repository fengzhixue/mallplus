package com.zscat.mallplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zscat.mallplus.annotation.IgnoreAuth;
import com.zscat.mallplus.pms.entity.PmsSmallNaviconCategory;
import com.zscat.mallplus.pms.service.IPmsSmallNaviconCategoryService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "SmallNaviconCategoryController", description = "小程序首页nav管理")
@RequestMapping("/api/smallnav")
public class PmsSmallNaviconCategoryController {
    @Resource
    IPmsSmallNaviconCategoryService smallNaviconCategoryService;

    @IgnoreAuth
    @PostMapping(value = "/nav/querySmallNavList")
    @ApiOperation(value = "查询首页nav列表")
    public Object queryProductList(@RequestBody PmsSmallNaviconCategory productQueryParam) {

        return new CommonResult().success(smallNaviconCategoryService.list(new QueryWrapper<>(productQueryParam)));
    }
}
