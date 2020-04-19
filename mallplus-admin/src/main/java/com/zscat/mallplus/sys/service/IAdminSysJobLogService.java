package com.zscat.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sys.entity.AdminSysJobLog;

/**
 * <p>
 * 定时任务调度日志表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-11-26
 */
public interface IAdminSysJobLogService extends IService<AdminSysJobLog> {

    void addJobLog(AdminSysJobLog jobLog);
}
