package com.zscat.mallplus.sys.controller;


import cn.hutool.core.util.PageUtil;
import com.zscat.mallplus.bo.ColumnInfo;
import com.zscat.mallplus.sys.entity.GenConfig;
import com.zscat.mallplus.sys.mapper.GeneratorConfigMapper;
import com.zscat.mallplus.sys.service.GeneratorService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/gen")
@RestController
public class GeneratorController {

    @Resource
    private GeneratorService generatorService;


    @Resource
    private GeneratorConfigMapper generatorConfigMapper;


    @ApiOperation("查询数据库元数据")
    @GetMapping(value = "/tables")
    public Object getTables(@RequestParam(defaultValue = "") String name,
                            @RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(defaultValue = "10") Integer size) {
        int[] startEnd = PageUtil.transToStartEnd(page + 1, size);
        return new CommonResult().success(generatorService.getTables(name, startEnd));
    }

    @ApiOperation("查询表内元数据")
    @GetMapping(value = "/columns")
    public Object getTables(@RequestParam String tableName) {
        return new CommonResult().success(generatorService.getColumns(tableName));
    }

    @ApiOperation("生成代码")
    @PostMapping
    public Object generator(@RequestBody List<ColumnInfo> columnInfos, @RequestParam String tableName) {

        generatorService.generator(columnInfos, generatorConfigMapper.selectById(1), tableName);
        return new CommonResult().success();
    }

    @ApiOperation("查询")
    @GetMapping(value = "/get")
    public Object get() {
        return new CommonResult().success(generatorConfigMapper.selectById(1));
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Object emailConfig(@Validated @RequestBody GenConfig genConfig) {
        genConfig.setId(1L);
        return new CommonResult().success(generatorConfigMapper.updateById(genConfig));
    }
}
