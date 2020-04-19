package com.zscat.mallplus.build.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.build.entity.BuildWuyeCompany;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-12-02
 */
public interface IBuildWuyeCompanyService extends IService<BuildWuyeCompany> {

    boolean saveCompany(BuildWuyeCompany entity);
}
