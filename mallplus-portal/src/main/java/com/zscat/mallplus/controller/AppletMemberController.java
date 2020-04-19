package com.zscat.mallplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zscat.mallplus.annotation.IgnoreAuth;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.cms.entity.CmsSubject;
import com.zscat.mallplus.cms.service.ICmsSubjectService;
import com.zscat.mallplus.enums.ConstansValue;
import com.zscat.mallplus.enums.OrderStatus;
import com.zscat.mallplus.oms.entity.OmsOrder;
import com.zscat.mallplus.oms.service.IOmsOrderService;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductAttributeCategory;
import com.zscat.mallplus.pms.service.IPmsProductAttributeCategoryService;
import com.zscat.mallplus.pms.service.IPmsProductCategoryService;
import com.zscat.mallplus.pms.service.IPmsProductService;
import com.zscat.mallplus.pms.service.IPmsSmallNaviconCategoryService;

import com.zscat.mallplus.sms.entity.SmsCoupon;
import com.zscat.mallplus.sms.entity.SmsHomeAdvertise;
import com.zscat.mallplus.sms.mapper.SmsFlashPromotionSessionMapper;
import com.zscat.mallplus.sms.mapper.SmsHomeNewProductMapper;
import com.zscat.mallplus.sms.mapper.SmsHomeRecommendProductMapper;
import com.zscat.mallplus.sms.service.*;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.ums.service.RedisService;
import com.zscat.mallplus.util.JsonUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员登录注册管理Controller
 * https://github.com/shenzhuan/mallplus on 2018/8/3.
 */
@Data
@RestController
@Api(tags = "AppletMemberController", description = "小程序登录首页")
@RequestMapping("/api/applet")
public class AppletMemberController  {
    @Autowired
    ISmsFlashPromotionSessionService smsFlashPromotionSessionService;
    @Autowired
    private IUmsMemberService memberService;
    @Autowired
    private ISmsHomeAdvertiseService advertiseService;
    @Autowired
    private ISmsCouponService couponService;
    @Autowired
    private IPmsProductAttributeCategoryService productAttributeCategoryService;
    @Autowired
    private IPmsSmallNaviconCategoryService smallNaviconCategoryService;
    @Autowired
    private ISmsFlashPromotionService smsFlashPromotionService;
    @Autowired
    private ISmsFlashPromotionProductRelationService smsFlashPromotionProductRelationService;
    @Autowired
    private IPmsProductService pmsProductService;
    @Autowired
    private ISmsHomeNewProductService smsHomeNewProductService;
    @Autowired
    private ISmsHomeRecommendProductService smsHomeRecommendProductService;
    @Autowired
    private IPmsProductCategoryService pmsProductCategoryService;

    @Autowired
    private ICmsSubjectService subjectService;
    @Autowired
    private IOmsOrderService orderService;

    @Autowired
    private RedisService redisService;
    @Resource
    private SmsFlashPromotionSessionMapper smsFlashPromotionSessionMapper;
    @Resource
    private SmsHomeRecommendProductMapper smsHomeRecommendProductMapper;
    @Resource
    private SmsHomeNewProductMapper smsHomeNewProductMapper;


    @Resource
    private ISmsRedPacketService redPacketService;

    @Resource
    private ISmsUserRedPacketService userRedPacketService;


    @IgnoreAuth
    @ApiOperation("注册")
    @SysLog(MODULE = "applet", REMARK = "小程序注册")
    @PostMapping("login_by_weixin1")
    public Object loginByWeixinNew(@RequestBody AppletLoginParam param) {
        try {
            return memberService.loginByWeixin1(param);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
    }

    @IgnoreAuth
    @ApiOperation("注册")
    @SysLog(MODULE = "applet", REMARK = "小程序注册")
    @PostMapping("login_by_weixin2")
    public Object loginByWeixinNew2(AppletLoginParam param) {
        try {
            return memberService.loginByWeixin1(param);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
    }

    @IgnoreAuth
    @ApiOperation("注册")
    @SysLog(MODULE = "applet", REMARK = "小程序注册")
    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody AppletLoginParam param) {
        try {
            return memberService.loginByWeixin(param);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }

    }

    @IgnoreAuth
    @ApiOperation("获取小程序openid")
    @SysLog(MODULE = "applet", REMARK = "获取小程序openid")
    @PostMapping("getAppletOpenId")
    public Object getAppletOpenId(@RequestBody AppletLoginParam param) {
        try {
            return memberService.getAppletOpenId(param);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
    }

    @PutMapping("/setUserInfo")
    @ApiOperation(value="设置用户信息", notes="设置用户信息")
    public Object setUserInfo(@RequestBody AppletLoginParam userInfoParam) {
        Long userId = memberService.getNewCurrentMember().getId();
        UmsMember user = new UmsMember();
        user.setId(userId);
        user.setIcon(userInfoParam.getPhone());
        user.setNickname(userInfoParam.getOpenid());
        memberService.updateById(user);

        return new CommonResult().success();
    }

    @IgnoreAuth
    @ApiOperation("小程序绑定手机号解密")
    @SysLog(MODULE = "applet", REMARK = "小程序绑定手机号解密")
    @PostMapping("getWxPhone")
    public Object getWxPhone(@RequestParam String openid,
                             @RequestParam String keyStr,
                             @RequestParam String ivStr,
                             @RequestParam String encDataStr) {
        try {
            String phone = memberService.getWxPhone(openid, keyStr, ivStr, encDataStr);
            if (phone != null) {
                return new CommonResult().success(phone);
            } else {
                return new CommonResult().failed("获取失败");
            }
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
    }

    /**
     * 小程序主页
     *
     * @param
     * @return
     */
    //首页获取，轮播图，分类，团购商品，分类商品，秒杀商品
    @IgnoreAuth
    @SysLog(MODULE = "applet", REMARK = "小程序首页")
    @ApiOperation("小程序首页")
    @GetMapping("/index")
    public Object index() {

        List<TArticleDO> model_list = new ArrayList<>();
        IndexData data = new IndexData();
        try {
            TArticleDO a = new TArticleDO("banner");
            TArticleDO a1 = new TArticleDO("search");
            TArticleDO a2 = new TArticleDO("nav");
            TArticleDO a3 = new TArticleDO("cat");
            TArticleDO a4 = new TArticleDO("coupon");
            TArticleDO a5 = new TArticleDO("topic");
            TArticleDO a6 = new TArticleDO("redPacket");
            TArticleDO b2 = new TArticleDO("block", "3");
            TArticleDO b1 = new TArticleDO("block", "4");
            TArticleDO b3 = new TArticleDO("block", "5");
            model_list.add(a);
            model_list.add(a1);
            model_list.add(a2);
            model_list.add(a3);
            model_list.add(a4);
            model_list.add(a5);
            model_list.add(a6);
            model_list.add(b1);
            model_list.add(b2);
            model_list.add(b3);
            //获取轮播图
            List<SmsHomeAdvertise> bannerList = null;
            SmsHomeAdvertise queryT = new SmsHomeAdvertise();
            String bannerJson = redisService.get(Rediskey.appletBannerKey + "2");
            if (bannerJson != null) {
                bannerList = JsonUtils.jsonToList(bannerJson, SmsHomeAdvertise.class);
            }
            if (bannerJson == null || bannerList.size() <= 0) {

                bannerList = advertiseService.list(new QueryWrapper<>(queryT));
                redisService.set(Rediskey.appletBannerKey + "2", JsonUtils.objectToJson(bannerList));
                redisService.expire(Rediskey.appletBannerKey + "2", 60);
            }
            //获取轮播结束
            //获取分类
            List<TArticleDO> nav_icon_list = new ArrayList<>();
            TArticleDO c1 = new TArticleDO("我的公告", "/pages/topic-list/topic-list", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/86/863a7db352a936743faf8edd5162bb5c.png");
            TArticleDO c2 = new TArticleDO("商品分类", "/pages/cat/cat", "switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/35/3570994c06e61b1f0cf719bdb52a0053.png");
            TArticleDO c3 = new TArticleDO("购物车", "/pages/cart/cart", "switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/c2/c2b01cf78f79cbfba192d5896eeaecbe.png");
            TArticleDO c4 = new TArticleDO("我的订单", "/pages/order/order?status=9", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/7c/7c80acbbd479b099566cc6c3d34fbcb8.png");
            TArticleDO c5 = new TArticleDO("用户中心", "/pages/user/user", "switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/46/46eabbff1e7dc5e416567fc45d4d5df3.png");
            TArticleDO c6 = new TArticleDO("优惠劵", "/pages/coupon/coupon?status=0", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/13/13312a6d56c202330f8c282d8cf84ada.png");
            TArticleDO c7 = new TArticleDO("我的收藏", "/pages/favorite/favorite", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/ca/cab6d8d4785e43bd46dcbb52ddf66f61.png");
            TArticleDO c8 = new TArticleDO("售后订单", "/pages/order/order?status=4", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/cf/cfb32a65d845b4e9a9778020ed2ccac6.png");
            nav_icon_list.add(c1);
            nav_icon_list.add(c2);
            nav_icon_list.add(c3);
            nav_icon_list.add(c4);
            nav_icon_list.add(c5);
            nav_icon_list.add(c6);
            nav_icon_list.add(c7);
            nav_icon_list.add(c8);
            //获取分类结束
            //获取秒杀活动商品


            data.setCat_goods_cols(2);
            data.setNav_icon_list(nav_icon_list);
            data.setBanner_list(bannerList);
            data.setModule_list(model_list);

            return new CommonResult().success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed(e.getMessage());
        }

    }

    @IgnoreAuth
    @SysLog(MODULE = "applet", REMARK = "小程序首页")
    @ApiOperation("小程序首页")
    @GetMapping("/index1")
    public Object index1() {

        IndexData data = new IndexData();
        //获取首页分类商品列表
       /* List<CateProduct> cateProductList = null;
        List<CateProduct> temp = new ArrayList<>();
        String cateProductJson = redisService.get(Rediskey.appletCateProductsKey);
        if (cateProductJson != null) {
            cateProductList = JsonUtils.jsonToList(cateProductJson, CateProduct.class);
        }
        if (cateProductJson == null || cateProductList.size() <= 0) {
            PmsProductCategory queryP = new PmsProductCategory();
            queryP.setIndexStatus(1);
            List<PmsProductCategory> pmsProductCategoryList = pmsProductCategoryService.list(new QueryWrapper<>(queryP));
            if (pmsProductCategoryList.size() > 0) {
                for (PmsProductCategory item : pmsProductCategoryList) {
                    PmsProduct queryProduct = new PmsProduct();
                    queryProduct.setProductCategoryId(item.getId());
                    List<PmsProduct> pmsProductList = pmsProductService.list(new QueryWrapper<>(queryProduct));//商品列表
                    List<HomeProductAttr> temphomeProductattr = new ArrayList<>();

                    for (PmsProduct pmsProduct : pmsProductList
                    ) {
                        HomeProductAttr productAttr = new HomeProductAttr();
                        productAttr.setId(pmsProduct.getId());
                        productAttr.setProductName(pmsProduct.getName());
                        productAttr.setProductImg(pmsProduct.getPic());
                        productAttr.setProductPrice(pmsProduct.getPrice() != null ? pmsProduct.getPrice() : BigDecimal.ZERO);
                        temphomeProductattr.add(productAttr);
                    }

                    CateProduct cateProduct = new CateProduct();
                    cateProduct.setCategoryId(item.getId());
                    cateProduct.setCategoryName(item.getName());
                    cateProduct.setCategoryImage(item.getIcon());
                    cateProduct.setPmsProductList(temphomeProductattr);
                    //存入分类+商品对象vo
                    temp.add(cateProduct);
                }
                cateProductList = temp;
                redisService.set(Rediskey.appletCateProductsKey, JsonUtils.objectToJson(cateProductList));
                redisService.expire(Rediskey.appletCateProductsKey, 60 );
            }
        }
*/
        //获取优惠券
        List<SmsCoupon> couponList = new ArrayList<>();
        couponList = couponService.selectNotRecive();
        //获取优惠券结束
        List<PmsProductAttributeCategory> productAttributeCategoryList = null;
        String catJson = redisService.get(Rediskey.appletCategoryKey);
        if (catJson != null) {
            productAttributeCategoryList = JsonUtils.jsonToList(catJson, PmsProductAttributeCategory.class);
        } else {
            productAttributeCategoryList = productAttributeCategoryService.list(new QueryWrapper<>());
            for (PmsProductAttributeCategory gt : productAttributeCategoryList) {
                PmsProduct productQueryParam = new PmsProduct();
                productQueryParam.setProductAttributeCategoryId(gt.getId());
                productQueryParam.setPublishStatus(1);
                productQueryParam.setVerifyStatus(1);
                gt.setGoodsList(pmsProductService.list(new QueryWrapper<>(productQueryParam)));
            }
            redisService.set(Rediskey.appletCategoryKey, JsonUtils.objectToJson(productAttributeCategoryList));
            redisService.expire(Rediskey.appletCategoryKey, 60);
        }
        List<CmsSubject> subjectList = subjectService.list(new QueryWrapper<CmsSubject>().select(ConstansValue.sampleSubjectList).last("limit 5"));
        //获取轮播图
        List<SmsHomeAdvertise> bannerList = null;
        SmsHomeAdvertise queryT = new SmsHomeAdvertise();
        String bannerJson = redisService.get(Rediskey.appletBannerKey + "2");
        if (bannerJson != null) {
            bannerList = JsonUtils.jsonToList(bannerJson, SmsHomeAdvertise.class);
        }
        if (bannerJson == null || bannerList.size() <= 0) {

            bannerList = advertiseService.list(new QueryWrapper<>(queryT));
            redisService.set(Rediskey.appletBannerKey + "2", JsonUtils.objectToJson(bannerList));
            redisService.expire(Rediskey.appletBannerKey + "2", 60);
        }
        //获取轮播结束
        //获取分类
        List<TArticleDO> nav_icon_list = new ArrayList<>();
        TArticleDO c1 = new TArticleDO("我的公告", "/pages/topic-list/topic-list", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/86/863a7db352a936743faf8edd5162bb5c.png");
        TArticleDO c2 = new TArticleDO("商品分类", "/pages/cat/cat", "switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/35/3570994c06e61b1f0cf719bdb52a0053.png");
        TArticleDO c3 = new TArticleDO("购物车", "/pages/cart/cart", "switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/c2/c2b01cf78f79cbfba192d5896eeaecbe.png");
        TArticleDO c4 = new TArticleDO("我的订单", "/pages/order/order?status=9", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/7c/7c80acbbd479b099566cc6c3d34fbcb8.png");
        TArticleDO c5 = new TArticleDO("用户中心", "/pages/user/user", "switchTab", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/46/46eabbff1e7dc5e416567fc45d4d5df3.png");
        TArticleDO c6 = new TArticleDO("优惠劵", "/pages/coupon/coupon?status=0", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/13/13312a6d56c202330f8c282d8cf84ada.png");
        TArticleDO c7 = new TArticleDO("我的收藏", "/pages/favorite/favorite", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/ca/cab6d8d4785e43bd46dcbb52ddf66f61.png");
        TArticleDO c8 = new TArticleDO("售后订单", "/pages/order/order?status=4", "navigate", "http://www.91weiyi.xyz/addons/zjhj_mall/core/web/uploads/image/cf/cfb32a65d845b4e9a9778020ed2ccac6.png");
        nav_icon_list.add(c1);
        nav_icon_list.add(c2);
        nav_icon_list.add(c3);
        nav_icon_list.add(c4);
        nav_icon_list.add(c5);
        nav_icon_list.add(c6);
        nav_icon_list.add(c7);
        nav_icon_list.add(c8);
        //获取分类结束
        //获取秒杀活动商品


        data.setCat_goods_cols(2);
        data.setNav_icon_list(nav_icon_list);
        data.setBanner_list(bannerList);
        data.setSubjectList(subjectList);
        data.setCat_list(productAttributeCategoryList);
        // data.setCate_products(cateProductList);
        data.setCoupon_list(couponList);
        return new CommonResult().success(data);
    }

    @IgnoreAuth
    @ApiOperation("小程序用户详情")
    @SysLog(MODULE = "applet", REMARK = "小程序用户详情")
    @GetMapping("/user")
    public Object user() {
        UmsMember umsMember = memberService.getNewCurrentMember();
        if (umsMember != null && umsMember.getId() != null) {
            OmsOrder param = new OmsOrder();
            param.setMemberId(umsMember.getId());
            List<OmsOrder> list = orderService.list(new QueryWrapper<>(param));
            int status0 = 0;
            int status1 = 0;
            int status2 = 0;
            int status3 = 0;
            int status4 = 0;
            int status5 = 0;
            OrderStatusCount count = new OrderStatusCount();
            for (OmsOrder consult : list) {
                if (consult.getStatus() == OrderStatus.INIT.getValue()) {
                    status0++;
                }
                if (consult.getStatus() == OrderStatus.TO_DELIVER.getValue()) {
                    status1++;
                }
                if (consult.getStatus() == OrderStatus.DELIVERED.getValue()) {
                    status2++;
                }
                if (consult.getStatus() == OrderStatus.TO_COMMENT.getValue()) {
                    status2++;
                }
                if (consult.getStatus() == OrderStatus.TRADE_SUCCESS.getValue()) {
                    status4++;
                }
                if (consult.getStatus() == OrderStatus.CLOSED.getValue()) {
                    status5++;
                }
            }
            count.setStatus0(status0);
            count.setStatus1(status1);
            count.setStatus2(status2);
            count.setStatus3(status3);
            count.setStatus4(status4);
            count.setStatus5(status5);
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("user", umsMember);
            objectMap.put("count", count);
            return new CommonResult().success(objectMap);
        }
        return new CommonResult().failed();

    }
}
