package com.zscat.mallplus.fenxiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.fenxiao.entity.FenxiaoChecks;
import com.zscat.mallplus.fenxiao.mapper.FenxiaoChecksMapper;
import com.zscat.mallplus.fenxiao.service.IFenxiaoChecksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mallplus
 * @date 2019-12-17
 */
@Service
public class FenxiaoChecksServiceImpl extends ServiceImpl
        <FenxiaoChecksMapper, FenxiaoChecks> implements IFenxiaoChecksService {

    @Resource
    private FenxiaoChecksMapper fenxiaoChecksMapper;


}
