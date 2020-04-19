package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sms.entity.SmsBasicGifts;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-07-07
 */
public interface ISmsBasicGiftsService extends IService<SmsBasicGifts> {

    int updateStatus(Long id, Integer status);
}
