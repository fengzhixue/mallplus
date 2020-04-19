package ${package}.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import ${package}.entity.${className};
import ${package}.service.I${className}Service;
import com.zscat.mallplus.util.EasyPoiUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Date;

/**
* @author ${author}
* @date ${date}
*  ${prefix}
*/
@Slf4j
@RestController
@RequestMapping("/${moduleName}/${changeClassName}")
public class ${className}Controller {

@Resource
private I${className}Service I${className}Service;

@SysLog(MODULE = "${moduleName}", REMARK = "根据条件查询所有${prefix}列表")
@ApiOperation("根据条件查询所有${prefix}列表")
@GetMapping(value = "/list")
@PreAuthorize("hasAuthority('${moduleName}:${changeClassName}:read')")
public Object get${className}ByPage(${className} entity,
@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
) {
try {
return new CommonResult().success(I${className}Service.page(new Page<${className}>(pageNum, pageSize), new QueryWrapper<>(entity)));
} catch (Exception e) {
log.error("根据条件查询所有${prefix}列表：%s", e.getMessage(), e);
}
return new CommonResult().failed();
}

@SysLog(MODULE = "${moduleName}", REMARK = "保存${prefix}")
@ApiOperation("保存${prefix}")
@PostMapping(value = "/create")
@PreAuthorize("hasAuthority('${moduleName}:${changeClassName}:create')")
public Object save${className}(@RequestBody ${className} entity) {
try {
entity.setCreateTime(new Date());
if (I${className}Service.save(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("保存${prefix}：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "${moduleName}", REMARK = "更新${prefix}")
@ApiOperation("更新${prefix}")
@PostMapping(value = "/update/{id}")
@PreAuthorize("hasAuthority('${moduleName}:${changeClassName}:update')")
public Object update${className}(@RequestBody ${className} entity) {
try {
if (I${className}Service.updateById(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("更新${prefix}：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "${moduleName}", REMARK = "删除${prefix}")
@ApiOperation("删除${prefix}")
@GetMapping(value = "/delete/{id}")
@PreAuthorize("hasAuthority('${moduleName}:${changeClassName}:delete')")
public Object delete${className}(@ApiParam("${prefix}id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("${prefix}id");
}
if (I${className}Service.removeById(id)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("删除${prefix}：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "${moduleName}", REMARK = "给${prefix}分配${prefix}")
@ApiOperation("查询${prefix}明细")
@GetMapping(value = "/{id}")
@PreAuthorize("hasAuthority('${moduleName}:${changeClassName}:read')")
public Object get${className}ById(@ApiParam("${prefix}id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("${prefix}id");
}
${className} coupon = I${className}Service.getById(id);
return new CommonResult().success(coupon);
} catch (Exception e) {
log.error("查询${prefix}明细：%s", e.getMessage(), e);
return new CommonResult().failed();
}

}

@ApiOperation(value = "批量删除${prefix}")
@RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
@SysLog(MODULE = "${moduleName}", REMARK = "批量删除${prefix}")
@PreAuthorize("hasAuthority('${moduleName}:${changeClassName}:delete')")
public Object deleteBatch(@RequestParam("ids") List
<Long> ids) {
    boolean count = I${className}Service.removeByIds(ids);
    if (count) {
    return new CommonResult().success(count);
    } else {
    return new CommonResult().failed();
    }
    }


    @SysLog(MODULE = "${moduleName}", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, ${className} entity) {
    // 模拟从数据库获取需要导出的数据
    List<${className}> personList = I${className}Service.list(new QueryWrapper<>(entity));
    // 导出操作
    EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", ${className}.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "${moduleName}", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
    List<${className}> personList = EasyPoiUtils.importExcel(file, ${className}.class);
    I${className}Service.saveBatch(personList);
    }
    }


