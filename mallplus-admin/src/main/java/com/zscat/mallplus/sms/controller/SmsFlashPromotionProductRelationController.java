package com.zscat.mallplus.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.bo.SmsFlashPromotionProducts;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.service.IPmsProductService;
import com.zscat.mallplus.sms.entity.SmsFlashPromotionProductRelation;
import com.zscat.mallplus.sms.service.ISmsFlashPromotionProductRelationService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品限时购与商品关系表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "SmsFlashPromotionProductRelationController", description = "商品限时购与商品关系表管理")
@RequestMapping("/sms/SmsFlashPromotionProductRelation")
public class SmsFlashPromotionProductRelationController {
    @Resource
    private ISmsFlashPromotionProductRelationService ISmsFlashPromotionProductRelationService;
    @Resource
    private IPmsProductService pmsProductService;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有商品限时购与商品关系表列表")
    @ApiOperation("根据条件查询所有商品限时购与商品关系表列表")
    @GetMapping(value = "/list")
    public Object getSmsFlashPromotionProductRelationByPage(SmsFlashPromotionProductRelation entity,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            //分页查询
            IPage<SmsFlashPromotionProductRelation> data = ISmsFlashPromotionProductRelationService.page(new Page<SmsFlashPromotionProductRelation>(pageNum, pageSize), new QueryWrapper<>(entity));
            //根据条件查询当前秒杀活动，当前点档的所有商品信息
            List<SmsFlashPromotionProductRelation> list = ((IPage) data).getRecords();
            List<SmsFlashPromotionProducts> smsFlashPromotionProductsList = new ArrayList<>();
            Map<String, Object> map = new HashedMap();
            map.put("total", data.getTotal());
            map.put("size", data.getSize());
            map.put("current", data.getCurrent());
            map.put("ascs", data.ascs());
            map.put("descs", data.descs());


            for (SmsFlashPromotionProductRelation item : list) {
                PmsProduct product = pmsProductService.getById(item.getProductId());
                SmsFlashPromotionProducts smsFlashPromotionProduct = new SmsFlashPromotionProducts();
                smsFlashPromotionProduct.setId(item.getId());
                smsFlashPromotionProduct.setFlashPromotionCount(item.getFlashPromotionCount());
                smsFlashPromotionProduct.setFlashPromotionLimit(item.getFlashPromotionLimit());
                smsFlashPromotionProduct.setFlashPromotionPrice(item.getFlashPromotionPrice());
                smsFlashPromotionProduct.setProduct(product);
                smsFlashPromotionProductsList.add(smsFlashPromotionProduct);
            }
            map.put("data", smsFlashPromotionProductsList);
            return new CommonResult().success(map);
        } catch (Exception e) {
            log.error("根据条件查询所有商品限时购与商品关系表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "批量选择商品添加关联")
    @ApiOperation("批量选择商品添加关联")
    @RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody List<SmsFlashPromotionProductRelation> relationList) {
        boolean count = ISmsFlashPromotionProductRelationService.saveBatch(relationList);
        if (count) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "保存商品限时购与商品关系表")
    @ApiOperation("保存商品限时购与商品关系表")
    @PostMapping(value = "/create")
    public Object saveSmsFlashPromotionProductRelation(@RequestBody SmsFlashPromotionProductRelation entity) {
        try {
            if (ISmsFlashPromotionProductRelationService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存商品限时购与商品关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新商品限时购与商品关系表")
    @ApiOperation("更新商品限时购与商品关系表")
    @PostMapping(value = "/update/{id}")
    public Object updateSmsFlashPromotionProductRelation(@RequestBody SmsFlashPromotionProductRelation entity) {
        try {
            if (ISmsFlashPromotionProductRelationService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新商品限时购与商品关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除商品限时购与商品关系表")
    @ApiOperation("删除商品限时购与商品关系表")
    @GetMapping(value = "/delete/{id}")
    public Object deleteSmsFlashPromotionProductRelation(@ApiParam("商品限时购与商品关系表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("商品限时购与商品关系表id");
            }
            if (ISmsFlashPromotionProductRelationService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除商品限时购与商品关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给商品限时购与商品关系表分配商品限时购与商品关系表")
    @ApiOperation("查询商品限时购与商品关系表明细")
    @GetMapping(value = "/{id}")
    public Object getSmsFlashPromotionProductRelationById(@ApiParam("商品限时购与商品关系表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("商品限时购与商品关系表id");
            }
            SmsFlashPromotionProductRelation coupon = ISmsFlashPromotionProductRelationService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询商品限时购与商品关系表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除商品限时购与商品关系表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除商品限时购与商品关系表")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsFlashPromotionProductRelationService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


}
