package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsContent;
import com.zscat.mallplus.sms.mapper.SmsContentMapper;
import com.zscat.mallplus.sms.service.ISmsContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mallplus
 * @date 2019-12-07
 */
@Service
public class SmsContentServiceImpl extends ServiceImpl<SmsContentMapper, SmsContent> implements ISmsContentService {

    @Resource
    private SmsContentMapper smsContentMapper;


}
