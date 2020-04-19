package com.zscat.mallplus.oms.vo;


import com.zscat.mallplus.cms.entity.CmsSubject;
import com.zscat.mallplus.pms.entity.PmsBrand;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductAttributeCategory;
import com.zscat.mallplus.pms.entity.PmsSmallNaviconCategory;
import com.zscat.mallplus.sms.entity.SmsCoupon;
import com.zscat.mallplus.sms.entity.SmsHomeAdvertise;
import com.zscat.mallplus.sms.vo.HomeFlashPromotion;
import com.zscat.mallplus.sys.entity.SysStore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页内容返回信息封装
 * https://github.com/shenzhuan/mallplus on 2019/1/28.
 */
@Getter
@Setter
public class HomeContentResult {
    List<PmsSmallNaviconCategory> navList;
    List<ActivityVo> activityList;
    //轮播广告
    private List<SmsHomeAdvertise> advertiseList;
    //推荐品牌
    private List<PmsBrand> brandList;
    //推荐品牌
    private List<SysStore> storeList;
    //当前秒杀场次
    //当前秒杀场次
    private HomeFlashPromotion homeFlashPromotion;
    //新品推荐
    private List<PmsProduct> newProductList;
    //人气推荐
    private List<PmsProduct> hotProductList;
    private List<PmsProduct> saleProductList;
    //推荐专题
    private List<CmsSubject> subjectList;
    private List<PmsProductAttributeCategory> cat_list;
    private List<SmsCoupon> couponList;

}
