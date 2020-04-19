package com.zscat.mallplus.oms.vo;


import com.zscat.mallplus.oms.entity.OmsCartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 购物车中选择规格的商品信息
 * https://github.com/shenzhuan/mallplus on 2018/8/2.
 */
@Setter
@Getter
public class StoreCart {
    private List<OmsCartItem> list;
    private String storeName;
}
