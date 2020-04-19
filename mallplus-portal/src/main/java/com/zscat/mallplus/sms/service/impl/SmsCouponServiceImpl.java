package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.oms.entity.OmsCartItem;
import com.zscat.mallplus.sms.entity.*;
import com.zscat.mallplus.sms.mapper.*;
import com.zscat.mallplus.sms.service.ISmsCouponService;
import com.zscat.mallplus.sms.vo.SmsCouponHistoryDetail;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠卷表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements ISmsCouponService {

    @Resource
    private SmsBargainConfigMapper bargainConfigMapper;
    @Resource
    private SmsBargainRecordMapper bargainRecordMapper;


    @Resource
    private IUmsMemberService memberService;
    @Resource
    private SmsCouponMapper couponMapper;
    @Resource
    private SmsCouponHistoryMapper couponHistoryMapper;

    @Override
    public List<SmsCoupon> selectNotRecive(Integer pageSize) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        if (currentMember != null && currentMember.getId() != null) {
            return couponMapper.selectNotRecive(currentMember.getId(), pageSize);
        }
        return couponMapper.selectList(new QueryWrapper<SmsCoupon>().lt("start_time", new Date()).gt("end_time", new Date()).last("limit " + pageSize));
    }

    @Override
    public List<SmsCoupon> selectRecive(Integer pageSize) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        if (currentMember != null && currentMember.getId() != null) {
            return couponMapper.selectRecive(currentMember.getId(), pageSize);
        }
        return couponMapper.selectList(new QueryWrapper<SmsCoupon>().lt("start_time", new Date()).gt("end_time", new Date()));
    }

    @Override
    public List<SmsCoupon> selectNotRecive() {
        SmsCoupon coupon = new SmsCoupon();
        List<SmsCoupon> list = couponMapper.selectList(new QueryWrapper<>(coupon).lt("start_time", new Date()).gt("end_time", new Date()));
        return list;

    }


    public List<SmsCoupon> selectNotRecive1() {
        List<SmsCoupon> list = new ArrayList<>();
        SmsCoupon coupon = new SmsCoupon();
        try {
            UmsMember currentMember = memberService.getNewCurrentMember();
            coupon.setType(0);
            if (currentMember != null && currentMember.getId() != null) {
                List<SmsCouponHistory> histories = couponHistoryMapper.selectList(new QueryWrapper<SmsCouponHistory>().eq("member_id", currentMember.getId()));
                if (histories != null && histories.size() > 0) {
                    List<Long> ids = histories.stream()
                            .map(SmsCouponHistory::getCouponId)
                            .collect(Collectors.toList());
                    list = couponMapper.selectList(new QueryWrapper<>(coupon).lt("start_time", new Date()).gt("end_time", new Date()).notIn("id", ids));
                }

            }
        } catch (Exception e) {
            list = couponMapper.selectList(new QueryWrapper<>(coupon).lt("start_time", new Date()).gt("end_time", new Date()));

        }
        return list;

    }

    @Override
    public List<SmsCouponHistory> listMemberCoupon(Integer useStatus) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        if (currentMember == null) {
            return new ArrayList<>();
        }
        if (ValidatorUtils.empty(useStatus)) {
            return couponHistoryMapper.selectList(new QueryWrapper<SmsCouponHistory>().eq("member_id", currentMember.getId()));
        }
        return couponHistoryMapper.selectList(new QueryWrapper<SmsCouponHistory>().eq("member_id", currentMember.getId()).eq("use_status", useStatus));
    }

    @Transactional
    @Override
    public CommonResult addbatch(String couponIds) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        if (currentMember == null) {
            return new CommonResult().failed("优惠券不存在");
        }
        if (ValidatorUtils.empty(couponIds)) {
            return new CommonResult().failed("优惠券不存在");
        }
        String[] ids = couponIds.split(",");
        for (String couponId : ids) {
            //获取优惠券信息，判断数量
            SmsCoupon coupon = couponMapper.selectById(Long.valueOf(couponId));
            if (coupon == null) {
                return new CommonResult().failed("优惠券不存在");
            }
            if (coupon.getCount() <= 0) {
                return new CommonResult().failed("优惠券已经领完了");
            }
            Date now = new Date();
            if (now.after(coupon.getEndTime())) {
                return new CommonResult().failed("优惠券已过期");
            }
            //判断用户领取的优惠券数量是否超过限制
            SmsCouponHistory queryH = new SmsCouponHistory();
            queryH.setMemberId(currentMember.getId());
            queryH.setCouponId(Long.valueOf(couponId));

            int count = couponHistoryMapper.selectCount(new QueryWrapper<>(queryH));
            if (count >= coupon.getPerLimit()) {
                return new CommonResult().failed("您已经领取过该优惠券");
            }
            //生成领取优惠券历史
            SmsCouponHistory couponHistory = new SmsCouponHistory();
            couponHistory.setCouponId(Long.valueOf(couponId));
            couponHistory.setCouponCode(generateCouponCode(currentMember.getId()));
            couponHistory.setCreateTime(now);
            couponHistory.setMemberId(currentMember.getId());
            couponHistory.setMemberNickname(currentMember.getNickname());
            //主动领取
            couponHistory.setGetType(1);
            //未使用
            couponHistory.setUseStatus(0);
            couponHistory.setAmount(coupon.getAmount());
            couponHistory.setStartTime(coupon.getStartTime());
            couponHistory.setEndTime(coupon.getEndTime());
            couponHistory.setNote(coupon.getName() + ":满" + coupon.getMinPoint() + "减" + coupon.getAmount());
            couponHistoryMapper.insert(couponHistory);
            //修改优惠券表的数量、领取数量
            coupon.setCount(coupon.getCount() - 1);
            coupon.setReceiveCount(coupon.getReceiveCount() == null ? 1 : coupon.getReceiveCount() + 1);
            couponMapper.updateById(coupon);
        }
        return new CommonResult().success("领取成功", null);
    }

    @Transactional
    @Override
    public CommonResult add(Long couponId) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        if (currentMember == null) {
            return new CommonResult().failed("优惠券不存在");
        }
        //获取优惠券信息，判断数量
        SmsCoupon coupon = couponMapper.selectById(couponId);
        if (coupon == null) {
            return new CommonResult().failed("优惠券不存在");
        }
        if (coupon.getCount() <= 0) {
            return new CommonResult().failed("优惠券已经领完了");
        }
        Date now = new Date();
        if (now.after(coupon.getEndTime())) {
            return new CommonResult().failed("优惠券已过期");
        }
        //判断用户领取的优惠券数量是否超过限制
        SmsCouponHistory queryH = new SmsCouponHistory();
        queryH.setMemberId(currentMember.getId());
        queryH.setCouponId(couponId);

        int count = couponHistoryMapper.selectCount(new QueryWrapper<>(queryH));
        if (count >= coupon.getPerLimit()) {
            return new CommonResult().failed("您已经领取过该优惠券");
        }
        //生成领取优惠券历史
        SmsCouponHistory couponHistory = new SmsCouponHistory();
        couponHistory.setCouponId(couponId);
        couponHistory.setCouponCode(generateCouponCode(currentMember.getId()));
        couponHistory.setCreateTime(now);
        couponHistory.setMemberId(currentMember.getId());
        couponHistory.setMemberNickname(currentMember.getNickname());
        //主动领取
        couponHistory.setGetType(1);
        //未使用
        couponHistory.setUseStatus(0);
        couponHistory.setStartTime(coupon.getStartTime());
        couponHistory.setEndTime(coupon.getEndTime());
        couponHistory.setNote(coupon.getName() + ":满" + coupon.getMinPoint() + "减" + coupon.getAmount());
        couponHistory.setAmount(coupon.getAmount());
        couponHistory.setMinPoint(coupon.getMinPoint());
        couponHistoryMapper.insert(couponHistory);
        //修改优惠券表的数量、领取数量
        coupon.setCount(coupon.getCount() - 1);
        coupon.setReceiveCount(coupon.getReceiveCount() == null ? 1 : coupon.getReceiveCount() + 1);
        couponMapper.updateById(coupon);
        return new CommonResult().success("领取成功", "领取成功");
    }

    /**
     * 16位优惠码生成：时间戳后8位+4位随机数+用户id后4位
     */
    private String generateCouponCode(Long memberId) {
        StringBuilder sb = new StringBuilder();
        Long currentTimeMillis = System.currentTimeMillis();
        String timeMillisStr = currentTimeMillis.toString();
        sb.append(timeMillisStr.substring(timeMillisStr.length() - 8));
        for (int i = 0; i < 4; i++) {
            sb.append(new Random().nextInt(10));
        }
        String memberIdStr = memberId.toString();
        if (memberIdStr.length() <= 4) {
            sb.append(String.format("%04d", memberId));
        } else {
            sb.append(memberIdStr.substring(memberIdStr.length() - 4));
        }
        return sb.toString();
    }

    @Override
    public List<SmsCouponHistory> list(Integer useStatus) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        SmsCouponHistory couponHistory = new SmsCouponHistory();
        couponHistory.setMemberId(currentMember.getId());

        if (useStatus != null) {
            couponHistory.setUseStatus(useStatus);
        }
        return couponHistoryMapper.selectList(new QueryWrapper<>(couponHistory));
    }

    @Override
    public List<SmsCouponHistoryDetail> listCart(List<OmsCartItem> cartItemList, Integer type) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        Date now = new Date();
        //获取该用户所有优惠券
        List<SmsCouponHistoryDetail> allList = couponHistoryMapper.getDetailList(currentMember.getId());
        //根据优惠券使用类型来判断优惠券是否可用
        List<SmsCouponHistoryDetail> enableList = new ArrayList<>();
        List<SmsCouponHistoryDetail> disableList = new ArrayList<>();
        for (SmsCouponHistoryDetail couponHistoryDetail : allList) {
            Integer useType = couponHistoryDetail.getCoupon().getUseType();
            BigDecimal minPoint = couponHistoryDetail.getCoupon().getMinPoint();
            Date endTime = couponHistoryDetail.getCoupon().getEndTime();
            if (useType.equals(0)) {
                //0->全场通用
                //判断是否满足优惠起点
                //计算购物车商品的总价
                BigDecimal totalAmount = calcTotalAmount(cartItemList);
                if (now.before(endTime) && totalAmount.subtract(minPoint).intValue() >= 0) {
                    enableList.add(couponHistoryDetail);
                } else {
                    disableList.add(couponHistoryDetail);
                }
            } else if (useType.equals(1)) {
                //1->指定分类
                //计算指定分类商品的总价
                List<Long> productCategoryIds = new ArrayList<>();
                for (SmsCouponProductCategoryRelation categoryRelation : couponHistoryDetail.getCategoryRelationList()) {
                    productCategoryIds.add(categoryRelation.getProductCategoryId());
                }
                BigDecimal totalAmount = calcTotalAmountByproductCategoryId(cartItemList, productCategoryIds);
                if (now.before(endTime) && totalAmount.intValue() > 0 && totalAmount.subtract(minPoint).intValue() >= 0) {
                    enableList.add(couponHistoryDetail);
                } else {
                    disableList.add(couponHistoryDetail);
                }
            } else if (useType.equals(2)) {
                //2->指定商品
                //计算指定商品的总价
                List<Long> productIds = new ArrayList<>();
                for (SmsCouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
                    productIds.add(productRelation.getProductId());
                }
                BigDecimal totalAmount = calcTotalAmountByProductId(cartItemList, productIds);
                if (now.before(endTime) && totalAmount.intValue() > 0 && totalAmount.subtract(minPoint).intValue() >= 0) {
                    enableList.add(couponHistoryDetail);
                } else {
                    disableList.add(couponHistoryDetail);
                }
            }
        }
        if (type.equals(1)) {
            return enableList;
        } else {
            return disableList;
        }
    }

    private BigDecimal calcTotalAmount(List<OmsCartItem> cartItemList) {
        BigDecimal total = new BigDecimal("0");
        for (OmsCartItem item : cartItemList) {
            //  BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
            total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return total;
    }

    private BigDecimal calcTotalAmountByproductCategoryId(List<OmsCartItem> cartItemList, List<Long> productCategoryIds) {
        BigDecimal total = new BigDecimal("0");
        for (OmsCartItem item : cartItemList) {
            if (productCategoryIds.contains(item.getProductCategoryId())) {
                //   BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }

    private BigDecimal calcTotalAmountByProductId(List<OmsCartItem> cartItemList, List<Long> productIds) {
        BigDecimal total = new BigDecimal("0");
        for (OmsCartItem item : cartItemList) {
            if (productIds.contains(item.getProductId())) {
                //  BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }


}
