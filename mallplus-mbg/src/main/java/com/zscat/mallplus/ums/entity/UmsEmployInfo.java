package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-07-02
 */
@TableName("ums_employ_info")
public class UmsEmployInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer age;

    private String gender;

    private String hobby;

    private String peoplenote;

    private String phone;

    private String address;

    private String guide1;

    private String guide;

    private String suggestion;

    private String guide2;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("store_id")
    private Long storeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getPeoplenote() {
        return peoplenote;
    }

    public void setPeoplenote(String peoplenote) {
        this.peoplenote = peoplenote;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGuide1() {
        return guide1;
    }

    public void setGuide1(String guide1) {
        this.guide1 = guide1;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getGuide2() {
        return guide2;
    }

    public void setGuide2(String guide2) {
        this.guide2 = guide2;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "UmsEmployInfo{" +
                ", id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", gender=" + gender +
                ", hobby=" + hobby +
                ", peoplenote=" + peoplenote +
                ", phone=" + phone +
                ", address=" + address +
                ", guide1=" + guide1 +
                ", guide=" + guide +
                ", suggestion=" + suggestion +
                ", guide2=" + guide2 +
                ", createTime=" + createTime +
                ", storeId=" + storeId +
                "}";
    }
}
