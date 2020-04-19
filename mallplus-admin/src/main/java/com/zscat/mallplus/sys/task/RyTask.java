package com.zscat.mallplus.sys.task;

import com.zscat.mallplus.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Slf4j
@Component("ryTask")
public class RyTask {


    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        params.put("endTime", calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        params.put("startTime", calendar.getTime());

        System.out.println(params);
    }

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        // System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    /**
     * 商户数据日统计
     */
    public void storeDayStatics() throws InterruptedException {


        log.info("商户数据日统计：{}，共{}个商户需要需要同步", DateUtils.getLastDayOfWeek());

        log.info("商户数据日统计end====：{}", DateUtils.getLastDayOfWeek());
    }

    /**
     * 商户统计
     */
    public void storeStatics() throws InterruptedException {


        log.info("商户统计end====：{}", DateUtils.getLastDayOfWeek());
    }

}
