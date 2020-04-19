package com.zscat.mallplus.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.pms.entity.PmsAlbum;
import com.zscat.mallplus.pms.service.IPmsAlbumService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Api(tags = "PmsAlbumController", description = "管理")
@RequestMapping("/pms/PmsAlbum")
public class PmsAlbumController {
    @Resource
    private IPmsAlbumService IPmsAlbumService;

    @SysLog(MODULE = "pms", REMARK = "查询pms_album表")
    @ApiOperation("查询pms_album表")
    @GetMapping(value = "/list")

    public Object getPmsAlbumByPage(PmsAlbum entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsAlbumService.page(new Page<PmsAlbum>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("分页获取pms_album列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存pms_album表")
    @ApiOperation("保存pms_album表")
    @PostMapping(value = "/create")

    public Object saveAlbum(@RequestBody PmsAlbum entity) {
        try {
            if (IPmsAlbumService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存pms_album表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新pms_album")
    @ApiOperation("更新pms_album")
    @PostMapping(value = "/update/{id}")

    public Object updateAlbum(@RequestBody PmsAlbum entity) {
        try {
            if (IPmsAlbumService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除pms_album数据")
    @ApiOperation("删除相册表数据")
    @GetMapping(value = "/delete/{id}")

    public Object deleteRole(@ApiParam("相册表_id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("PmsAlbum_id");
            }
            if (IPmsAlbumService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除相册表数据：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "根据ID查询pms_album")
    @ApiOperation("根据ID查询pms_album")
    @GetMapping(value = "/{id}")

    public Object getRoleById(@ApiParam("相册表_id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("PmsAlbum_id");
            }
            PmsAlbum pmsAlbum = IPmsAlbumService.getById(id);
            return new CommonResult().success(pmsAlbum);
        } catch (Exception e) {
            log.error("pms_album表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除PmsAlbum表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除PmsAlbum表")

    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsAlbumService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


}
