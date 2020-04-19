package com.zscat.mallplus;


import com.zscat.mallplus.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/3.
 */
public class T {
    public static void main(String[] args) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        Date da = new Date();
        String format = sdf2.format(da);
        long nowT = DateUtil.strToDate(format, "HH:mm:ss").getTime();
        System.out.println(nowT);


    }
}
