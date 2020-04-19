package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.oms.entity.OmsCartItem;

import com.zscat.mallplus.sms.entity.SmsBargainRecord;
import com.zscat.mallplus.sms.entity.SmsCoupon;
import com.zscat.mallplus.sms.entity.SmsCouponHistory;
import com.zscat.mallplus.sms.vo.SmsCouponHistoryDetail;
import com.zscat.mallplus.utils.CommonResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 优惠卷表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface ISmsCouponService extends IService<SmsCoupon> {

    /**
     * 会员添加优惠券
     */
    @Transactional
    CommonResult add(Long couponId);

    /**
     * 获取优惠券列表
     *
     * @param useStatus 优惠券的使用状态
     */
    List<SmsCouponHistory> list(Integer useStatus);

    /**
     * 根据购物车信息获取可用优惠券
     */
    List<SmsCouponHistoryDetail> listCart(List<OmsCartItem> cartItemList, Integer type);


    List<SmsCoupon> selectNotRecive(Integer pageSize);

    List<SmsCoupon> selectRecive(Integer pageSize);

    List<SmsCoupon> selectNotRecive();


    List<SmsCouponHistory> listMemberCoupon(Integer useStatus);

    CommonResult addbatch(String couponIds);


}
