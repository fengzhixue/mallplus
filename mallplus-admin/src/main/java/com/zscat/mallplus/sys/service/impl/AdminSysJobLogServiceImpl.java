package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sys.entity.AdminSysJobLog;
import com.zscat.mallplus.sys.mapper.AdminSysJobLogMapper;
import com.zscat.mallplus.sys.service.IAdminSysJobLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务调度日志表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-11-26
 */
@Service
public class AdminSysJobLogServiceImpl extends ServiceImpl<AdminSysJobLogMapper, AdminSysJobLog> implements IAdminSysJobLogService {

    @Override
    public void addJobLog(AdminSysJobLog jobLog) {
        this.baseMapper.insert(jobLog);
    }
}
