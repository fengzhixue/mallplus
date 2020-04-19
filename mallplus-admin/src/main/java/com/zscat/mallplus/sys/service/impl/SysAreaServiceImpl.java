package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sys.entity.SysArea;
import com.zscat.mallplus.sys.mapper.SysAreaMapper;
import com.zscat.mallplus.sys.service.ISysAreaService;
import com.zscat.mallplus.sys.vo.AreaWithChildrenItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements ISysAreaService {

    @Resource
    SysAreaMapper sysAreaMapper;

    @Override
    public List<AreaWithChildrenItem> listWithChildren() {
        return sysAreaMapper.listWithChildren();
    }
}


