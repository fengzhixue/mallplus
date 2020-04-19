package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tools {
    public static void main(String[] args) throws Exception {
        String host = "https://goexpress.market.alicloudapi.com";       //【1】请求地址  支持http 和 https 及 WEBSOCKET
        String path = "/goexpress";                                     //【2】后缀
        String appcode = "436e99b5b81044698cbaf100d164aa63";                             //【3】AppCode  你自己的AppCode 在买家中心查看
        String no = "780098068058";                                     //【4】参数，具体参照api接口参数
        String type = "zto";                                            //【5】参数，具体参照api接口参数
        String urlSend = host + path + "?no=" + no + "&type=" + type;   //【6】拼接请求链接
        /*【1】 ~ 【6】 需要修改为对应的 可以参考产品详情 */
        URL url = new URL(urlSend);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Authorization", "APPCODE " + appcode);//格式Authorization:APPCODE (中间是英文空格)
        int httpCode = httpURLConnection.getResponseCode();
        String json = read(httpURLConnection.getInputStream());
        System.out.println("/* 获取服务器响应状态码 200 正常；400 权限错误 ； 403 次数用完； */ ");
        System.out.println(httpCode);
        System.out.println("/* 获取返回的json   */ ");
        System.out.print(json);
    }

    /*
        读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
