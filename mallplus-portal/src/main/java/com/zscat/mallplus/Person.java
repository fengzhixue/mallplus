package com.zscat.mallplus;

import com.zscat.mallplus.sms.vo.BeanKv;

/**
 * Created by qcl on 2018/3/26.
 */
public class Person {

    private int id;
    private String name;
    private int age;

    public Person() {
    }

    public Person(int id, String name, int age) {
        this.id = id;

        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        BeanKv kv = new BeanKv();
        if (kv == null) {
            System.out.println("1");
        } else {
            System.out.println(2);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
