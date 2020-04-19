package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sms.entity.SmsDiyPage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-11-19
 */
public interface ISmsDiyPageService extends IService<SmsDiyPage> {

    Integer selDiyPageTypeId(Integer typeId, Long id);

    Object selDiyPageDetail(SmsDiyPage entity);

    Integer selectCounts(Long id, String name);
}
