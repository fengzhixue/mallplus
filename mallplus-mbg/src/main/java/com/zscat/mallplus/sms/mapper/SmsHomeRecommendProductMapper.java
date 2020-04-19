package com.zscat.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sms.entity.SmsHomeRecommendProduct;
import com.zscat.mallplus.sms.vo.HomeProductAttr;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsHomeRecommendProductMapper extends BaseMapper<SmsHomeRecommendProduct> {
    List<HomeProductAttr> queryList();
}
