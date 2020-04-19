package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.cms.entity.CmsSubject;
import com.zscat.mallplus.oms.vo.HomeContentResult;
import com.zscat.mallplus.pms.entity.PmsBrand;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductAttributeCategory;
import com.zscat.mallplus.pms.entity.PmsSmallNaviconCategory;
import com.zscat.mallplus.sms.entity.SmsGroup;
import com.zscat.mallplus.sms.entity.SmsHomeAdvertise;
import com.zscat.mallplus.sms.vo.HomeFlashPromotion;
import com.zscat.mallplus.sys.entity.SysStore;
import com.zscat.mallplus.vo.home.Pages;

import java.util.List;

/**
 * <p>
 * 首页轮播广告表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface ISmsHomeAdvertiseService extends IService<SmsHomeAdvertise> {

    HomeContentResult singelContent();

    HomeContentResult singelContent1();

    List<PmsBrand> getRecommendBrandList(int pageNum, int pageSize);

    HomeFlashPromotion getHomeFlashPromotion();

    List<PmsProduct> getNewProductList(int pageNum, int pageSize);

    List<PmsProduct> getSaleProductList(int pageNum, int pageSize);

    List<PmsProduct> getHotProductList(int pageNum, int pageSize);

    List<CmsSubject> getRecommendSubjectList(int pageNum, int pageSize);

    List<SmsHomeAdvertise> getHomeAdvertiseList(int type);

    List<SmsHomeAdvertise> getHomeAdvertiseList();

    List<PmsProductAttributeCategory> getPmsProductAttributeCategories();

    List<SysStore> getStoreList(int pageNum, int pageSize);

    List<HomeFlashPromotion> homeFlashPromotionList();

    List<PmsSmallNaviconCategory> getNav();

    List<SmsGroup> lastGroupGoods(Integer pageNum);

    Pages contentNew();

    HomeContentResult contentNew1();

    HomeContentResult contentPc();

    HomeContentResult singelmobileContent();
}
