package com.zscat.mallplus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    private static final DateFormat FORMATER_DATE_YMD = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 无分隔符日期格式 "yyyyMMddHHmmssSSS"
     */
    public static String DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS = "yyyyMMddHHmmssSSS";
    // 日期转换格式数组
    public static String[][] regularExp = new String[][]{

            // 默认格式
            {"\\d{4}-((([0][1,3-9]|[1][0-2]|[1-9])-([0-2]\\d|[3][0,1]|[1-9]))|((02|2)-(([1-9])|[0-2]\\d)))\\s+([0,1]\\d|[2][0-3]|\\d):([0-5]\\d|\\d):([0-5]\\d|\\d)",
                    DATE_TIME_PATTERN},
            // 仅日期格式 年月日
            {"\\d{4}-((([0][1,3-9]|[1][0-2]|[1-9])-([0-2]\\d|[3][0,1]|[1-9]))|((02|2)-(([1-9])|[0-2]\\d)))",
                    DATE_PATTERN},
            //  带毫秒格式
            {"\\d{4}((([0][1,3-9]|[1][0-2]|[1-9])([0-2]\\d|[3][0,1]|[1-9]))|((02|2)(([1-9])|[0-2]\\d)))([0,1]\\d|[2][0-3])([0-5]\\d|\\d)([0-5]\\d|\\d)\\d{1,3}",
                    DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS}
    };
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    private static Calendar calendar = Calendar.getInstance();

    public static Date toDate(String d) throws Exception {
        return FORMATER_DATE_YMD.parse(d);
    }

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    public static String addDay(Date s, int n) {

        SimpleDateFormat FORMATER_DATE_YMD = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cd = Calendar.getInstance();
        cd.setTime(s);
        cd.add(Calendar.DATE, n);//增加一天
        //cd.add(Calendar.MONTH, n);//增加一个月
        return FORMATER_DATE_YMD.format(cd.getTime());

    }

    /**
     * 转换为时间类型格式
     *
     * @param strDate 日期
     * @return
     */
    public static Date strToDate(String strDate) {
        try {
            String strType = getDateFormat(strDate);
            SimpleDateFormat sf = new SimpleDateFormat(strType);
            return new Date((sf.parse(strDate).getTime()));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据传入的日期格式字符串，获取日期的格式
     *
     * @return 秒
     */
    public static String getDateFormat(String date_str) {
        String style = null;
        if (StringUtils.isEmpty(date_str)) {
            return null;
        }
        boolean b = false;
        for (int i = 0; i < regularExp.length; i++) {
            b = date_str.matches(regularExp[i][0]);
            if (b) {
                style = regularExp[i][1];
            }
        }
        if (StringUtils.isEmpty(style)) {
            logger.info("date_str:" + date_str);
            logger.info("日期格式获取出错，未识别的日期格式");
        }
        return style;
    }

    /**
     * 返回当前时间的"yyyy-MM-dd"格式字符串
     */
    public static String currentDay() {
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(new Date());
    }

    public static int calculateDaysNew(Date first, Date second) {
        int days = 0;

        if (second.before(first)) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(second);
            calendar1.set(Calendar.HOUR_OF_DAY, 0);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            calendar1.set(Calendar.MILLISECOND, 0);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(first);
            calendar2.set(Calendar.HOUR_OF_DAY, 0);
            calendar2.set(Calendar.MINUTE, 0);
            calendar2.set(Calendar.SECOND, 0);
            calendar2.set(Calendar.MILLISECOND, 0);
            while (calendar1.compareTo(calendar2) != 0) {
                calendar1.add(Calendar.DAY_OF_YEAR, 1);
                days++;
            }
            days = -days;
        } else {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(first);
            calendar1.set(Calendar.HOUR_OF_DAY, 0);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            calendar1.set(Calendar.MILLISECOND, 0);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(second);
            calendar2.set(Calendar.HOUR_OF_DAY, 0);
            calendar2.set(Calendar.MINUTE, 0);
            calendar2.set(Calendar.SECOND, 0);
            calendar2.set(Calendar.MILLISECOND, 0);
            while (calendar1.compareTo(calendar2) != 0) {
                calendar1.add(Calendar.DAY_OF_YEAR, 1);
                days++;
            }
        }

        return days;
    }

    /**
     * 获取当月的第一天
     *
     * @return
     */
    public static String geFirstDayByMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return FORMATER_DATE_YMD.format(c.getTime());
    }

    /**
     * 获取当月的第一天
     *
     * @return
     */
    public static Date geFirstDayDateByMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    /**
     * 获取当月的最后一天
     *
     * @return
     */
    public static String geLastDayByMonth() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return FORMATER_DATE_YMD.format(ca.getTime());
    }

    /**
     * 获取当前周的第一天：
     *
     * @return
     */
    public static Date getFirstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new Date());
            cal.set(Calendar.DAY_OF_WEEK, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cal.getTime();
    }

    /**
     * 获取当前周最后一天
     *
     * @return
     */
    public static Date getLastDayOfWeek() {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(new Date());
            cal.set(Calendar.DAY_OF_WEEK, 1);
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 6);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cal.getTime();
    }

    /*
    输入日期字符串比如201703，返回当月第一天的Date
    */
    public static Date getMinDateMonth(String month) {
        try {
            Date nowDate = sdf.parse(month);
            calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    输入日期字符串，返回当月最后一天的Date
    */
    public static Date getMaxDateMonth(String month) {
        try {
            Date nowDate = sdf.parse(month);
            calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String month = "201705";
        System.out.println(getMinDateMonth(month));
        System.out.println(getMaxDateMonth(month));
        System.out.println(DateUtils.geLastDayByMonth());
        System.out.println(DateUtils.addDay(new Date(), -7));
        System.out.println(DateUtils.calculateDaysNew(DateUtils.toDate(DateUtils.addDay(new Date(), -7)), new Date()));
    }
}
