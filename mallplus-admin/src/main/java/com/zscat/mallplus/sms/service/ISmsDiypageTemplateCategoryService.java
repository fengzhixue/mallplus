package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sms.entity.SmsDiypageTemplateCategory;

/**
 * @author mallplus
 * @date 2019-12-04
 */

public interface ISmsDiypageTemplateCategoryService extends IService<SmsDiypageTemplateCategory> {

    Object selTemplateCategory();
}
