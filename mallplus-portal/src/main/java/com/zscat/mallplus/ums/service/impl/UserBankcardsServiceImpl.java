package com.zscat.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.entity.UserBankcards;
import com.zscat.mallplus.ums.mapper.UserBankcardsMapper;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.ums.service.IUserBankcardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@Service
public class UserBankcardsServiceImpl extends ServiceImpl<UserBankcardsMapper, UserBankcards> implements IUserBankcardsService {

    @Resource
    UserBankcardsMapper bankcardsMapper;
    @Autowired
    private IUmsMemberService memberService;

    @Transactional
    @Override
    public int setDefault(Long id) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        UserBankcards query = new UserBankcards();
        query.setIsDefault(2);
        bankcardsMapper.update(query, new QueryWrapper<UserBankcards>().eq("user_id", currentMember.getId()));

        UserBankcards def = new UserBankcards();
        def.setId(id);
        def.setIsDefault(1);
        this.updateById(def);
        return 1;
    }
}
