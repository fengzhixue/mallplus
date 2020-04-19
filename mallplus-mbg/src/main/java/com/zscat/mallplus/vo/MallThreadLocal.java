package com.zscat.mallplus.vo;

public class MallThreadLocal {
    private static final ThreadLocal<String> threadLocalUser = new ThreadLocal<>();
    private static final ThreadLocal<String> threadLocalB = new ThreadLocal<>();

    public static String getLocalUser() {
        return threadLocalUser.get();
    }

    /**
     * 在调用的线程的map中存入key为ThreadLocal本身，value为在该线程设置的值
     *
     * @param value
     */
    public static void setLocalUser(String value) {
        threadLocalUser.set(value);
    }

    public static void clearLocalUser() {
        threadLocalUser.remove();
    }

    public static String getValueB() {
        return threadLocalB.get();
    }

    public static void setValueB(String value) {
        threadLocalB.set(value);
    }

    public static void clearValueB() {
        threadLocalB.remove();
    }


}
