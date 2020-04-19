package com.zscat.mallplus.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sms.entity.SmsFlashPromotion;
import com.zscat.mallplus.sms.service.ISmsFlashPromotionService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since ${date}
 */
@Slf4j
@RestController
@Api(tags = "SmsFlashPromotionController", description = "管理")
@RequestMapping("/sms/flashPromotion")
public class SmsFlashPromotionController {
    @Resource
    private ISmsFlashPromotionService ISmsFlashPromotionService;

    @SysLog(MODULE = "sms", REMARK = "查询sms_flash_promotion表")
    @ApiOperation("查询sms_flash_promotion表")
    @GetMapping(value = "/list")
    public Object getSmsFlashPromotionByPage(SmsFlashPromotion entity,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsFlashPromotionService.page(new Page<SmsFlashPromotion>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("分页获取sms_flash_promotion列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "保存sms_flash_promotion表")
    @ApiOperation("保存sms_flash_promotion表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sms:SmsFlashPromotion:create')")
    public Object saveFlashPromotion(@RequestBody SmsFlashPromotion entity) {
        try {
            if (ISmsFlashPromotionService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存sms_flash_promotion表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新sms_flash_promotion")
    @ApiOperation("更新sms_flash_promotion")
    @PostMapping(value = "/update/{id}")
    public Object updateFlashPromotion(@RequestBody SmsFlashPromotion entity) {
        try {
            if (ISmsFlashPromotionService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新sms_flash_promotion")
    @ApiOperation("更新sms_flash_promotion")
    @PostMapping(value = "/update/status")
    public Object updateFlashStatus(@RequestParam("ids") Long ids, @RequestParam("status") Integer status) {
        try {
            SmsFlashPromotion entity = new SmsFlashPromotion();
            entity.setId(ids);
            entity.setStatus(status);
            if (ISmsFlashPromotionService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新sms_flash_promotion")
    @ApiOperation("更新sms_flash_promotion")
    @PostMapping(value = "/update/isIndex")
    public Object updateFlashIsIndex(@RequestParam("ids") Long ids, @RequestParam("isIndex") Integer isIndex) {
        try {
            SmsFlashPromotion entity = new SmsFlashPromotion();
            entity.setId(ids);
            entity.setIsIndex(isIndex);
            if (ISmsFlashPromotionService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除sms_flash_promotion数据")
    @ApiOperation("删除限时购表数据")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sms:SmsFlashPromotion:delete')")
    public Object deleteRole(@ApiParam("限时购表_id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("SmsFlashPromotion_id");
            }
            if (ISmsFlashPromotionService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除限时购表数据：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "根据ID查询sms_flash_promotion")
    @ApiOperation("根据ID查询sms_flash_promotion")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sms:SmsFlashPromotion:read')")
    public Object getRoleById(@ApiParam("限时购表_id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("SmsFlashPromotion_id");
            }
            SmsFlashPromotion smsFlashPromotion = ISmsFlashPromotionService.getById(id);
            return new CommonResult().success(smsFlashPromotion);
        } catch (Exception e) {
            log.error("sms_flash_promotion表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除SmsFlashPromotion表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "sms", REMARK = "批量删除SmsFlashPromotion表")
    @PreAuthorize("hasAuthority('sms:SmsFlashPromotion:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsFlashPromotionService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


}
