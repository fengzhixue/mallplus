package com.zscat.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.oms.mapper.OmsOrderMapper;
import com.zscat.mallplus.oms.vo.OrderStstic;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.entity.UmsMemberLevel;
import com.zscat.mallplus.ums.mapper.UmsMemberMapper;
import com.zscat.mallplus.ums.service.IUmsMemberLevelService;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements IUmsMemberService {

    @Resource
    private IUmsMemberLevelService memberLevelService;
    @Resource
    private OmsOrderMapper omsOrderMapper;
    @Resource
    private UmsMemberMapper memberMapper;

    @Override
    public void updataMemberOrderInfo() {
        List<OrderStstic> orders = omsOrderMapper.listOrderGroupByMemberId();
        List<UmsMemberLevel> levelList = memberLevelService.list(new QueryWrapper<UmsMemberLevel>().orderByDesc("price"));
        for (OrderStstic o : orders) {
            UmsMember member = new UmsMember();
            member.setId(o.getMemberId());
            member.setBuyMoney(o.getTotalPayAmount());
            for (UmsMemberLevel level : levelList) {
                if (member.getBuyMoney().compareTo(level.getPrice()) >= 0) {
                    member.setMemberLevelId(level.getId());
                    member.setMemberLevelName(level.getName());
                    break;
                }
            }
            member.setBuyCount(o.getTotalCount());
            memberMapper.updateById(member);
        }
    }
}
