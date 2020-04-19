package com.zscat.mallplus.sys.util;

import com.zscat.mallplus.sys.entity.AdminSysJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author ruoyi
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, AdminSysJob job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
