package com.zscat.mallplus.sys.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.zscat.mallplus.bo.ColumnInfo;
import com.zscat.mallplus.bo.TableInfo;
import com.zscat.mallplus.sys.entity.GenConfig;
import com.zscat.mallplus.sys.mapper.GeneratorMapper;
import com.zscat.mallplus.sys.service.GeneratorService;
import com.zscat.mallplus.util.GenUtil;
import com.zscat.mallplus.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class GeneratorServiceImpl implements GeneratorService {
    @Resource
    GeneratorMapper generatorMapper;


    @Override
    @SuppressWarnings("all")
    public Object getTables(String name, int[] startEnd) {

        List<Map<String, Object>> list = generatorMapper.list(name, startEnd[0], startEnd[1]);
        List<TableInfo> tableInfos = new ArrayList<>();
        for (Map<String, Object> obj : list) {

            tableInfos.add(new TableInfo(obj.get("tableName"), obj.get("createTime"), obj.get("engine"), obj.get("coding"), ObjectUtil.isNotEmpty(obj.get("table_comment")) ? obj.get("table_comment").toString() : "-"));
        }
        Object totalElements = generatorMapper.count(null);
        return PageUtil.toPage(tableInfos, totalElements);
    }

    @Override
    @SuppressWarnings("all")
    public Object getColumns(String name) {

        List<Map<String, String>> result = generatorMapper.listColumns(name);
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Map<String, String> obj : result) {

            columnInfos.add(new ColumnInfo(obj.get("column_name"), obj.get("is_nullable"), obj.get("data_type"), obj.get("column_comment"), obj.get("columnkey"), obj.get("extra"), null, "true"));
        }
        return PageUtil.toPage(columnInfos, columnInfos.size());
    }

    @Override
    public void generator(List<ColumnInfo> columnInfos, GenConfig genConfig, String tableName) {

        try {
            GenUtil.generatorCode(columnInfos, genConfig, tableName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
