package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsDiyPage;
import com.zscat.mallplus.sms.mapper.SmsDiyPageMapper;
import com.zscat.mallplus.sms.service.ISmsDiyPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-11-19
 */
@Service
public class SmsDiyPageServiceImpl extends ServiceImpl<SmsDiyPageMapper, SmsDiyPage> implements ISmsDiyPageService {

    @Resource
    private SmsDiyPageMapper diyPageMapper;

    @Override
    public Integer selDiyPageTypeId(Integer typeId, Long id) {
        return diyPageMapper.selectCount(new QueryWrapper<SmsDiyPage>().eq("status", 1).eq("type", typeId).ne("id", id));
    }

    @Override
    public Object selDiyPageDetail(SmsDiyPage entity) {
        return null;
    }

    @Override
    public Integer selectCounts(Long id, String name) {
        return diyPageMapper.selectCount(new QueryWrapper<SmsDiyPage>().eq("name", name).ne("id", id));
    }
}
