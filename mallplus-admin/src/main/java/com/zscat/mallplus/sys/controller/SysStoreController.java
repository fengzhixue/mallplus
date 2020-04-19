package com.zscat.mallplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.ApiContext;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.bill.entity.BakCategory;
import com.zscat.mallplus.bill.mapper.BakCategoryMapper;
import com.zscat.mallplus.enums.ConstansValue;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductAttributeCategory;
import com.zscat.mallplus.pms.mapper.PmsProductAttributeCategoryMapper;
import com.zscat.mallplus.pms.service.IPmsProductService;
import com.zscat.mallplus.sys.entity.SysStore;
import com.zscat.mallplus.sys.service.ISysStoreService;
import com.zscat.mallplus.sys.service.impl.RedisUtil;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import com.zscat.mallplus.vo.Rediskey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-05-18
 */
@Slf4j
@RestController
@Api(tags = "SysStoreController", description = "管理")
@RequestMapping("/sys/SysStore")
public class SysStoreController {
    @Resource
    private ISysStoreService ISysStoreService;
    @Resource
    private IPmsProductService pmsProductService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
    @Autowired
    private ApiContext apiContext;
    @Resource
    private BakCategoryMapper bakCategoryMapper;

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有列表")
    @ApiOperation("根据条件查询所有列表")
    @GetMapping(value = "/setStoreId/{id}")
    public Object setStoreId(@ApiParam("id") @PathVariable Integer id) {
        try {
            //   apiContext.setCurrentProviderId(id);
            return new CommonResult().success();
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有列表")
    @ApiOperation("根据条件查询所有列表")
    @GetMapping(value = "/list")
    public Object getSysStoreByPage(SysStore entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISysStoreService.page(new Page<SysStore>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "保存")
    @ApiOperation("保存")
    @PostMapping(value = "/create")
    public Object saveSysStore(@RequestBody SysStore entity) {
        try {
            if (ISysStoreService.saveStore(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed("此用户已存在");
    }

    @SysLog(MODULE = "sys", REMARK = "更新")
    @ApiOperation("更新")
    @PostMapping(value = "/update/{id}")
    public Object updateSysStore(@RequestBody SysStore entity) {
        try {
            if (ISysStoreService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "删除")
    @ApiOperation("删除")
    @GetMapping(value = "/delete/{id}")
    public Object deleteSysStore(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            if (ISysStoreService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "给分配")
    @ApiOperation("查询明细")
    @GetMapping(value = "/{id}")
    public Object getSysStoreById(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            SysStore coupon = ISysStoreService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISysStoreService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有列表")
    @ApiOperation("根据条件查询所有列表")
    @GetMapping(value = "/listBakCate")
    public Object listCate(BakCategory entity,
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize
    ) {
        try {
            return new CommonResult().success(bakCategoryMapper.selectList(new QueryWrapper<BakCategory>().eq("pid", 0)));
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("获取商铺详情")
    @RequestMapping(value = "/storeDetail", method = RequestMethod.GET)
    @ResponseBody
    public Object storeDetail() {
        SysStore store = ISysStoreService.getById(1);
        List<PmsProductAttributeCategory> list = productAttributeCategoryMapper.selectList(new QueryWrapper<PmsProductAttributeCategory>().eq("store_id", store.getId()));
        for (PmsProductAttributeCategory gt : list) {
            PmsProduct productQueryParam = new PmsProduct();
            productQueryParam.setProductAttributeCategoryId(gt.getId());
            productQueryParam.setPublishStatus(1);
            productQueryParam.setVerifyStatus(1);
            IPage<PmsProduct> goodsList = pmsProductService.page(new Page<PmsProduct>(0, 8), new QueryWrapper<>(productQueryParam).select(ConstansValue.sampleGoodsList));
            if (goodsList != null && goodsList.getRecords() != null && goodsList.getRecords().size() > 0) {
                gt.setGoodsList(goodsList.getRecords());
            } else {
                gt.setGoodsList(new ArrayList<>());
            }
        }
        store.setList(list);
        store.setGoodsCount(pmsProductService.count(new QueryWrapper<PmsProduct>().eq("store_id", store.getId())));
        //记录浏览量到redis,然后定时更新到数据库
        String key = Rediskey.STORE_VIEWCOUNT_CODE + store.getId();
        //找到redis中该篇文章的点赞数，如果不存在则向redis中添加一条
        Map<Object, Object> viewCountItem = redisUtil.hGetAll(Rediskey.STORE_VIEWCOUNT_KEY);
        Integer viewCount = 0;
        if (!viewCountItem.isEmpty()) {
            if (viewCountItem.containsKey(key)) {
                viewCount = Integer.parseInt(viewCountItem.get(key).toString()) + 1;
                redisUtil.hPut(Rediskey.STORE_VIEWCOUNT_KEY, key, viewCount + "");
            } else {
                viewCount = 1;
                redisUtil.hPut(Rediskey.STORE_VIEWCOUNT_KEY, key, 1 + "");
            }
        } else {
            redisUtil.hPut(Rediskey.STORE_VIEWCOUNT_KEY, key, 1 + "");
        }
        Map<String, Object> map = new HashMap<>();
        store.setHit(viewCount);
        map.put("store", store);
        return new CommonResult().success(map);
    }
}
