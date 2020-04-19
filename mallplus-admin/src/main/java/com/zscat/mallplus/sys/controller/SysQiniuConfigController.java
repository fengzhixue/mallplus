package com.zscat.mallplus.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.sys.entity.SysQiniuConfig;
import com.zscat.mallplus.sys.entity.SysQiniuContent;
import com.zscat.mallplus.sys.service.ISysQiniuConfigService;
import com.zscat.mallplus.sys.service.ISysQiniuContentService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-11-30
 */
@Slf4j
@RestController
@RequestMapping("/sys/sysQiniuConfig")
public class SysQiniuConfigController {
    @Resource
    private ISysQiniuConfigService qiNiuService;
    @Resource
    private ISysQiniuContentService qiniuContentService;

    @GetMapping(value = "/config")
    public Object get() {
        return new CommonResult().success(qiNiuService.getOne(new QueryWrapper<>()));
    }


    @ApiOperation("配置七牛云存储")
    @PutMapping(value = "/config")
    public Object emailConfig(@Validated @RequestBody SysQiniuConfig qiniuConfig) {
        qiNiuService.updateById(qiniuConfig);
        // qiNiuService.update(qiniuConfig.getType());
        return new ResponseEntity(HttpStatus.OK);
    }


    @ApiOperation("导出数据")
    @GetMapping(value = "/download/list")
    public void download(HttpServletResponse response, SysQiniuContent entity) throws IOException {
        qiNiuService.downloadList(qiniuContentService.list(new QueryWrapper<>(entity)), response);
    }


    @ApiOperation("查询文件")
    @GetMapping
    public Object getRoles(SysQiniuConfig entity,
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            return new CommonResult().success(qiNiuService.page(new Page<SysQiniuConfig>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有系统配置信息表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }


    @ApiOperation("上传文件")
    @PostMapping
    public Object upload(@RequestParam MultipartFile file) {
        SysQiniuContent qiniuContent = qiNiuService.upload(file, qiNiuService.getOne(new QueryWrapper<>()));
        Map<String, Object> map = new HashMap<>(3);
        map.put("id", qiniuContent.getId());
        map.put("errno", 0);
        map.put("data", new String[]{qiniuContent.getUrl()});
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @ApiOperation("同步七牛云数据")
    @PostMapping(value = "/synchronize")
    public Object synchronize() {
        qiNiuService.synchronize(qiNiuService.getOne(new QueryWrapper<>()));
        return new ResponseEntity(HttpStatus.OK);
    }


    @ApiOperation("下载文件")
    @GetMapping(value = "/download/{id}")
    public Object download(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("url", qiNiuService.download(qiNiuService.findByContentId(id), qiNiuService.getOne(new QueryWrapper<>())));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @ApiOperation("删除文件")
    @DeleteMapping(value = "/{id}")
    public Object delete(@PathVariable Long id) {
        qiNiuService.delete(qiNiuService.findByContentId(id), qiNiuService.getOne(new QueryWrapper<>()));
        return new ResponseEntity(HttpStatus.OK);
    }


    @ApiOperation("删除多张图片")
    @DeleteMapping
    public Object deleteAll(@RequestBody Long[] ids) {
        qiNiuService.deleteAll(ids, qiNiuService.getOne(new QueryWrapper<>()));
        return new ResponseEntity(HttpStatus.OK);
    }
}

