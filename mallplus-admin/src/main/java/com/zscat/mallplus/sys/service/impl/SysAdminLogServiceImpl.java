package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sys.entity.SysAdminLog;
import com.zscat.mallplus.sys.mapper.SysAdminLogMapper;
import com.zscat.mallplus.sys.service.ISysAdminLogService;
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
public class SysAdminLogServiceImpl extends ServiceImpl<SysAdminLogMapper, SysAdminLog> implements ISysAdminLogService {

    @Resource
    private SysAdminLogMapper logMapper;

    @Override
    public List<LogStatisc> selectPageExt(LogParam entity) {
        return logMapper.getLogStatisc(entity);
    }
}
