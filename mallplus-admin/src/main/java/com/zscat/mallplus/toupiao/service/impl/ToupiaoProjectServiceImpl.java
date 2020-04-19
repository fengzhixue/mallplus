package com.zscat.mallplus.toupiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.toupiao.entity.ToupiaoProject;
import com.zscat.mallplus.toupiao.mapper.ToupiaoProjectMapper;
import com.zscat.mallplus.toupiao.service.IToupiaoProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mallplus
 * @date 2019-12-17
 */
@Service
public class ToupiaoProjectServiceImpl extends ServiceImpl
        <ToupiaoProjectMapper, ToupiaoProject> implements IToupiaoProjectService {

    @Resource
    private ToupiaoProjectMapper toupiaoProjectMapper;


}
