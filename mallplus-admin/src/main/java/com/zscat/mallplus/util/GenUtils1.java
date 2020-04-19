/*
package com.zscat.mallplus.util;


import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.template.*;
import com.zscat.mallplus.bo.ColumnDO;
import com.zscat.mallplus.bo.TableDO;
import com.zscat.mallplus.config.Constant;
import com.zscat.mallplus.exception.ApiMallPlusException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

*/
/**
 * 代码生成器   工具类
 * <p>
 * 生成代码
 * <p>
 * 列名转换成Java属性名
 * <p>
 * 表名转换成Java类名
 * <p>
 * 获取配置信息
 * <p>
 * 获取文件名
 * <p>
 * 生成代码
 * <p>
 * 列名转换成Java属性名
 * <p>
 * 表名转换成Java类名
 * <p>
 * 获取配置信息
 * <p>
 * 获取文件名
 *//*

public class GenUtils1 {


    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("common/generator/domain.java.ftl");
        templates.add("common/generator/Dao.java.ftl");
        //templates.add("common/generator/Mapper.java.vm");
        //  templates.add("common/generator/Mapper.xml.vm");
        templates.add("common/generator/Service.java.ftl");
        templates.add("common/generator/ServiceImpl.java.ftl");
        templates.add("common/generator/Controller.java.ftl");
        templates.add("common/generator/add.vue.ftl");
        templates.add("common/generator/index.vue.ftl");
        templates.add("common/generator/api.js.ftl");
        templates.add("common/generator/path.js.ftl");
        templates.add("common/generator/update.vue.ftl");

        templates.add("common/generator/BrandDetail.vue.ftl");
        templates.add("common/generator/menu.sql.ftl");
        return templates;
    }

    */
/**
 * 生成代码
 *//*



    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        //表信息
        TableDO tableDO = new TableDO();
        tableDO.setTableName(table.get("tableName"));
        tableDO.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableDO.getTableName(), config.getString("tablePrefix"), config.getString("autoRemovePre"));
        tableDO.setClassName(className);
        tableDO.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnDO> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("columnName"));
            columnDO.setDataType(column.get("dataType"));
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnDO.getDataType(), "unknowType");
            columnDO.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }

            columsList.add(columnDO);
        }
        tableDO.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        String packages = "com.zscat.mallplus";
        String Module = tableDO.getTableName().split("_")[0].substring(0, 1).toUpperCase() + tableDO.getTableName().split("_")[0].substring(1).toLowerCase();
        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("module", tableDO.getTableName().split("_")[0]);
        map.put("Module", Module);
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        map.put("pathName", packages.substring(packages.lastIndexOf(".") + 1));
        map.put("columns", tableDO.getColumns());
        map.put("package", packages);
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("date", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));

        System.out.println(map.toString());
        //获取模板列表
        List<String> templates = getTemplates();
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));

        for (String templateName : templates) {
            //渲染模板

            Template template = engine.getTemplate(templateName);

            try {
                String filePath = getFileName(
                        tableDO.getTableName().split("_")[0], templateName, tableDO.getClassname(), tableDO.getClassName(), packages.substring(packages.lastIndexOf(".")+ 1), Module);
                //添加到zip
                assert filePath != null;
                File file = new File(filePath);


                // 生成代码
                genFile(file, template, map);
            } catch (IOException e) {
                throw new ApiMallPlusException("渲染模板失败，表名：" + tableDO.getTableName(), e);
            }
        }
    }
    private static void genFile(File file, Template template, Map<String, Object> map) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            FileUtil.touch(file);
            writer = new FileWriter(file);
            template.render(map, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert writer != null;
            writer.close();
        }
    }

    */
/**
 * 列名转换成Java属性名
 *//*

    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    */
/**
 * 表名转换成Java类名
 *//*

    public static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {
        if (Constant.AUTO_REOMVE_PRE.equals(autoRemovePre)) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    */
/**
 * 获取配置信息
 *//*

    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (org.apache.commons.configuration.ConfigurationException e) {
            throw new ApiMallPlusException("获取配置文件失败，", e);
        }
    }

    */
/**
 * 获取文件名
 *//*

    public static String getFileName(String module, String template, String classname, String className, String packageName, String Module) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        //String modulesname=config.getString("packageName");
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("domain.java.ftl")) {
            return Module + className + ".java";
        }

        if (template.contains("Dao.java.ftl")) {
            return Module + className + "Mapper.java";
        }

//		if(template.contains("Mapper.java.vm")){
//			return packagePath + "dao" + File.separator + className + "Mapper.java";
//		}
        // templates.add("common/generator/menu.sql.ftl");
        if (template.contains("menu.sql.ftl")) {
            return Module + className + "menu.sql";
        }
        if (template.contains("Service.java.ftl")) {
            return "I" + Module + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.ftl")) {
            return Module + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.ftl")) {
            return Module + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return Module + className + "Mapper.xml";
        }
        if (template.contains("api.js.ftl")) {
            return classname + ".js";
        }
        if (template.contains("path.js.ftl")) {
            return classname + "path" + ".js";
        }
        if (template.contains("add.vue.ftl")) {
            return classname + File.separator + "add.vue";
        }

        if (template.contains("index.vue.ftl")) {
            return classname + File.separator + "index.vue";
        }
        if (template.contains("update.vue.ftl")) {
            return classname + File.separator + "update.vue";
        }
        if (template.contains("BrandDetail.vue.ftl")) {
            return classname + File.separator + "components" + File.separator + className + "Detail.vue";
        }

        return null;
    }
}
*/
