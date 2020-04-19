package com.zscat.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sms.entity.SmsHomeNewProduct;
import com.zscat.mallplus.sms.vo.HomeProductAttr;

import java.util.List;

/**
 * <p>
 * 新鲜好物表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsHomeNewProductMapper extends BaseMapper<SmsHomeNewProduct> {
    List<HomeProductAttr> queryList();
}
