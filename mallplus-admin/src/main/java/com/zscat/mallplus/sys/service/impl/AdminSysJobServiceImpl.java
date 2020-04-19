package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sys.entity.AdminSysJob;
import com.zscat.mallplus.sys.mapper.AdminSysJobMapper;
import com.zscat.mallplus.sys.service.IAdminSysJobService;
import com.zscat.mallplus.sys.util.CronUtils;
import com.zscat.mallplus.sys.util.ScheduleConstants;
import com.zscat.mallplus.sys.util.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 定时任务调度表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-11-26
 */
@Service
public class AdminSysJobServiceImpl extends ServiceImpl<AdminSysJobMapper, AdminSysJob> implements IAdminSysJobService {

    @Resource
    AdminSysJobMapper jobMapper;
    @Resource
    private Scheduler scheduler;

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {

        for (Long jobId : ids) {
            AdminSysJob job = jobMapper.selectById(jobId);
            deleteJob(job);
        }
        return true;
    }

    @Override
    public void addJobLog(AdminSysJob jobLog) {
        jobMapper.insert(jobLog);
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int deleteJob(AdminSysJob job) {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        int rows = jobMapper.deleteById(jobId);
        if (rows > 0) {
            try {
                scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }

    /**
     * 项目启动时，初始化定时器
     * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException {
        List<AdminSysJob> jobList = jobMapper.selectList(new QueryWrapper<>());
        for (AdminSysJob job : jobList) {
            updateSchedulerJob(job, job.getJobGroup());
        }
    }

    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int pauseJob(AdminSysJob job) {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateById(job);
        if (rows > 0) {
            try {
                scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int resumeJob(AdminSysJob job) {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateById(job);
        if (rows > 0) {
            try {
                scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int changeStatus(AdminSysJob job) {
        int rows = 0;
        Integer status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public void run(AdminSysJob job) {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        AdminSysJob properties = jobMapper.selectById(job.getJobId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        try {
            scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional
    public int insertJob(AdminSysJob job) {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.insert(job);
        if (rows > 0) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int updateJob(AdminSysJob job) {
        AdminSysJob properties = jobMapper.selectById(job.getJobId());
        int rows = jobMapper.updateById(job);
        if (rows > 0) {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return rows;
    }

    /**
     * 更新任务
     *
     * @param job      调度信息
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(AdminSysJob job, String jobGroup) {
        try {
            Long jobId = job.getJobId();
            // 判断是否存在
            JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
            if (scheduler.checkExists(jobKey)) {
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(jobKey);
            }
            ScheduleUtils.createScheduleJob(scheduler, job);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }
}
