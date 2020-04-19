package com.zscat.mallplus.fenxiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.fenxiao.entity.FenxiaoConfig;
import com.zscat.mallplus.fenxiao.mapper.FenxiaoConfigMapper;
import com.zscat.mallplus.fenxiao.service.IFenxiaoConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mallplus
 * @date 2019-12-17
 */
@Service
public class FenxiaoConfigServiceImpl extends ServiceImpl
        <FenxiaoConfigMapper, FenxiaoConfig> implements IFenxiaoConfigService {

    @Resource
    private FenxiaoConfigMapper fenxiaoConfigMapper;


}
