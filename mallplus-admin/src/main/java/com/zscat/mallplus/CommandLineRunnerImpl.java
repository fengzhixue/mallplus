package com.zscat.mallplus;

import com.zscat.mallplus.sys.mapper.GeneratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author lnj
 *         createTime 2018-11-07 22:25
 **/
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    GeneratorMapper generatorMapper;

    public static void main(String[] args) {
        System.out.println("http://localhost:8080/cms/CmsSubjectCategory/list".replace("//", "a").indexOf("/") + 1);
    }

    @Override
    public void run(String... args) throws Exception {

        /*List<Map<String, Object>> tables = generatorMapper.list();
        for (Map<String, Object> map : tables) {
            List<Map<String, String>> colus = generatorMapper.listColumns(map.get("tableName").toString());
            for (Map<String, String> mapc : colus) {
                if (mapc.get("columnName").equals("store_id")) {
                    Constant.Tables.put(map.get("tableName").toString(), "1");

                }
            }
        }*/

    }
}
