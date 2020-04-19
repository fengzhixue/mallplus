package com;

import java.util.Random;

public class Test02 {
    public static void main(String[] args) {
        String s = "101=fddw233dw3435d3d21d,102=tttt,103=eeee,104=qqqq,105=冯";
        String p = "101=fddw233dw3435d3d21d,04,105=冯";
        String[] str = s.split(",");

        for (int i = 0; i < str.length; i++) {
            Random r = new Random();
            int a = r.nextInt(10);
            System.out.println(a);
            if (p.indexOf(str[i]) > -1) { //删除多个时用 ，删除一个时用 if(p.equals(str[i]))
                str[i] = "";
            }
        }
        s = "";
        for (int i = 0; i < str.length; i++) {
            if (str[i] != "") {
                s += str[i] + ",";
            }
        }
        //  System.out.println(s.substring(0, s.length() - 1));
    }
}
