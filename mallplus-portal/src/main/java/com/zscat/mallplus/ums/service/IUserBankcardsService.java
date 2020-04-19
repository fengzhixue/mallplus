package com.zscat.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.ums.entity.UserBankcards;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
public interface IUserBankcardsService extends IService<UserBankcards> {

    int setDefault(Long id);
}
