package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsDiypageTemplateCategory;
import com.zscat.mallplus.sms.mapper.SmsDiypageTemplateCategoryMapper;
import com.zscat.mallplus.sms.service.ISmsDiypageTemplateCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mallplus
 * @date 2019-12-04
 */
@Service
public class SmsDiypageTemplateCategoryServiceImpl extends ServiceImpl<SmsDiypageTemplateCategoryMapper, SmsDiypageTemplateCategory> implements ISmsDiypageTemplateCategoryService {

    @Resource
    private SmsDiypageTemplateCategoryMapper smsDiypageTemplateCategoryMapper;


    @Override
    public Object selTemplateCategory() {
        return smsDiypageTemplateCategoryMapper.selectList(new QueryWrapper<>());
    }
}
