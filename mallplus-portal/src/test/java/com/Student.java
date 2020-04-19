package com;

import lombok.Data;

@Data
public class Student {

    private String name;
    private int age;
    private Long hight;
    private int sex;

    // 设置 年龄和性别的拼接，得出相应分组

    public Long getIwantStudent() {
        return Long.valueOf(this.sex + this.age);
    }
}