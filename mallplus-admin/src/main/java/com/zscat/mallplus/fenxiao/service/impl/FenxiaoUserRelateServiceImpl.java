package com.zscat.mallplus.fenxiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.fenxiao.entity.FenxiaoUserRelate;
import com.zscat.mallplus.fenxiao.mapper.FenxiaoUserRelateMapper;
import com.zscat.mallplus.fenxiao.service.IFenxiaoUserRelateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mallplus
 * @date 2019-12-17
 */
@Service
public class FenxiaoUserRelateServiceImpl extends ServiceImpl
        <FenxiaoUserRelateMapper, FenxiaoUserRelate> implements IFenxiaoUserRelateService {

    @Resource
    private FenxiaoUserRelateMapper fenxiaoUserRelateMapper;


}
