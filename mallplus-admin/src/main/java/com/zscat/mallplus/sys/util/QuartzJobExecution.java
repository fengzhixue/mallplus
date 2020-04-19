package com.zscat.mallplus.sys.util;

import com.zscat.mallplus.sys.entity.AdminSysJob;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author ruoyi
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, AdminSysJob job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
