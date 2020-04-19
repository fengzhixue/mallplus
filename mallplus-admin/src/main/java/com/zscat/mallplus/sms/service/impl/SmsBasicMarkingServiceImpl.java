package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsBasicMarking;
import com.zscat.mallplus.sms.mapper.SmsBasicMarkingMapper;
import com.zscat.mallplus.sms.service.ISmsBasicMarkingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-07-07
 */
@Service
public class SmsBasicMarkingServiceImpl extends ServiceImpl<SmsBasicMarkingMapper, SmsBasicMarking> implements ISmsBasicMarkingService {

    @Resource
    private SmsBasicMarkingMapper markingMapper;

    /**
     * * 1 有效2 无效
     *
     * @param id
     * @param status
     * @return
     */
    @Transactional
    @Override
    public int updateStatus(Long id, Integer status, Integer bigType) {
        SmsBasicMarking marking = new SmsBasicMarking();
        if (status == 1) {
            marking.setId(id);
            marking.setStatus(1);
            markingMapper.updateById(marking);
        } else {
            marking.setStatus(1);
            markingMapper.update(marking, new QueryWrapper<SmsBasicMarking>().eq("big_type", bigType));
            marking.setId(id);
            marking.setStatus(0);
            markingMapper.updateById(marking);
        }
        return 0;
    }
}
