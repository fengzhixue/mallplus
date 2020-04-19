package com.zscat.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 代码生成配置
 *
 * @author mallplus
 * @date 2019-01-03
 */
@Data
@TableName("gen_config")
public class GenConfig {


    private Long id;

    // 包路径
    private String pack;

    // 模块名

    private String moduleName;

    // 前端文件路径
    private String path;

    // 前端文件路径

    private String apiPath;

    // 作者
    private String author;

    // 表前缀
    private String prefix;

    // 是否覆盖
    private Boolean cover;
}
