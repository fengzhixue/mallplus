package com.zscat.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品基本信息表
 * </p>
 *
 * @author zscat
 * @since 2019-09-17
 */
@TableName("bak_goods")
public class BakGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品编号
     */
    @TableField("goods_sn")
    private String goodsSn;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品所属类目ID
     */
    @TableField("category_id")
    private Integer categoryId;

    @TableField("brand_id")
    private Integer brandId;

    /**
     * 商品宣传图片列表，采用JSON数组格式
     */
    private String gallery;

    /**
     * 商品关键字，采用逗号间隔
     */
    private String keywords;

    /**
     * 商品简介
     */
    private String brief;

    /**
     * 是否上架
     */
    @TableField("is_on_sale")
    private Boolean isOnSale;

    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 商品页面商品图片
     */
    @TableField("pic_url")
    private String picUrl;

    /**
     * 商品分享朋友圈图片
     */
    @TableField("share_url")
    private String shareUrl;

    /**
     * 是否新品首发，如果设置则可以在新品首发页面展示
     */
    @TableField("is_new")
    private Boolean isNew;

    /**
     * 是否人气推荐，如果设置则可以在人气推荐页面展示
     */
    @TableField("is_hot")
    private Boolean isHot;

    /**
     * 商品单位，例如件、盒
     */
    private String unit;

    /**
     * 专柜价格
     */
    @TableField("counter_price")
    private BigDecimal counterPrice;

    /**
     * 零售价格
     */
    @TableField("retail_price")
    private BigDecimal retailPrice;

    /**
     * 商品详细介绍，是富文本格式
     */
    private String detail;

    /**
     * 创建时间
     */
    @TableField("add_time")
    private LocalDateTime addTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Boolean deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Boolean getOnSale() {
        return isOnSale;
    }

    public void setOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(BigDecimal counterPrice) {
        this.counterPrice = counterPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "BakGoods{" +
                ", id=" + id +
                ", goodsSn=" + goodsSn +
                ", name=" + name +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", gallery=" + gallery +
                ", keywords=" + keywords +
                ", brief=" + brief +
                ", isOnSale=" + isOnSale +
                ", sortOrder=" + sortOrder +
                ", picUrl=" + picUrl +
                ", shareUrl=" + shareUrl +
                ", isNew=" + isNew +
                ", isHot=" + isHot +
                ", unit=" + unit +
                ", counterPrice=" + counterPrice +
                ", retailPrice=" + retailPrice +
                ", detail=" + detail +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                "}";
    }
}
