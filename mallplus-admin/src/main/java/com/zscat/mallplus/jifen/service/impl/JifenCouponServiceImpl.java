package com.zscat.mallplus.jifen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.jifen.entity.JifenCoupon;
import com.zscat.mallplus.jifen.mapper.JifenCouponMapper;
import com.zscat.mallplus.jifen.service.IJifenCouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mallplus
 * @date 2019-12-17
 */
@Service
public class JifenCouponServiceImpl extends ServiceImpl
        <JifenCouponMapper, JifenCoupon> implements IJifenCouponService {

    @Resource
    private JifenCouponMapper jifenCouponMapper;


}
