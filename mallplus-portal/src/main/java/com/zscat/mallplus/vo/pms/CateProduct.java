package com.zscat.mallplus.vo.pms;

import com.zscat.mallplus.sms.vo.HomeProductAttr;
import lombok.Data;

import java.util.List;

@Data
public class CateProduct {
    private Long categoryId;//分类Id
    private String categoryName;//分类名字
    private String categoryImage;//分类图片路径
    private List<HomeProductAttr> pmsProductList;//分类商品列表
}
