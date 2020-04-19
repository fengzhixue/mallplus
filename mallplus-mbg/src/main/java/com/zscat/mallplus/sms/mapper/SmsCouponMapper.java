package com.zscat.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sms.entity.SmsCoupon;
import com.zscat.mallplus.sms.vo.SmsCouponParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠卷表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsCouponMapper extends BaseMapper<SmsCoupon> {

    List<SmsCoupon> selectNotRecive(@Param("memberId") Long memberId, @Param("limit") Integer limit);

    List<SmsCoupon> selectRecive(@Param("memberId") Long memberId, @Param("limit") Integer limit);

    SmsCouponParam getItem(@Param("id") Long id);
}
