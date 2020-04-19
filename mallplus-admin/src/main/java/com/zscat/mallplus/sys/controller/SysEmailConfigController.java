package com.zscat.mallplus.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zscat.mallplus.sys.entity.SysEmailConfig;
import com.zscat.mallplus.sys.service.ISysEmailConfigService;
import com.zscat.mallplus.sys.vo.EmailVo;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-11-30
 */
@RestController
@RequestMapping("/sys/sysEmailConfig")
public class SysEmailConfigController {

    @Resource
    private ISysEmailConfigService emailService;


    @GetMapping(value = "/{id}")
    public Object get() {
        return new CommonResult().success(emailService.getOne(new QueryWrapper<>()));
    }


    @PostMapping(value = "/update")
    @ApiOperation("配置邮件")
    public Object emailConfig(@Validated @RequestBody SysEmailConfig emailConfig) {
        emailService.updateById(emailConfig);
        return new CommonResult().success();
    }


    @PostMapping(value = "/send")
    @ApiOperation("发送邮件")
    public Object send(@Validated @RequestBody EmailVo emailVo) throws Exception {
        emailService.send(emailVo, emailService.getOne(new QueryWrapper<>()));
        return new CommonResult().success();
    }
}

