package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sys.entity.SysWebLog;
import com.zscat.mallplus.sys.mapper.SysWebLogMapper;
import com.zscat.mallplus.sys.service.ISysWebLogService;
import com.zscat.mallplus.vo.LogParam;
import com.zscat.mallplus.vo.LogStatisc;
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
public class SysWebLogServiceImpl extends ServiceImpl<SysWebLogMapper, SysWebLog> implements ISysWebLogService {

    @Resource
    private SysWebLogMapper logMapper;

    @Override
    public List<LogStatisc> selectPageExt(LogParam entity) {
        return logMapper.getLogStatisc(entity);
    }
}
