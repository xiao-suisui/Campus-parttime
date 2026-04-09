package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JobApplicationMapper;
import com.ruoyi.system.domain.JobApplication;
import com.ruoyi.system.service.IJobApplicationService;

/**
 * 投递记录Service业务层处理
 * 
 * @author wly
 * @date 2026-04-08
 */
@Service
public class JobApplicationServiceImpl implements IJobApplicationService 
{
    private static final String STATUS_APPLIED = "0";
    private static final String STATUS_HIRED = "1";
    private static final String STATUS_COMPLETED = "2";

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    /**
     * 查询投递记录
     * 
     * @param applicationId 投递记录主键
     * @return 投递记录
     */
    @Override
    public JobApplication selectJobApplicationByApplicationId(Long applicationId)
    {
        return jobApplicationMapper.selectJobApplicationByApplicationId(applicationId);
    }

    /**
     * 查询投递记录列表
     * 
     * @param jobApplication 投递记录
     * @return 投递记录
     */
    @Override
    public List<JobApplication> selectJobApplicationList(JobApplication jobApplication)
    {
        return jobApplicationMapper.selectJobApplicationList(jobApplication);
    }

    /**
     * 新增投递记录
     * 
     * @param jobApplication 投递记录
     * @return 结果
     */
    @Override
    public int insertJobApplication(JobApplication jobApplication)
    {
        jobApplication.setApplicationStatus(STATUS_APPLIED);
        if (jobApplication.getApplyTime() == null)
        {
            jobApplication.setApplyTime(new Date());
        }
        jobApplication.setCreateTime(DateUtils.getNowDate());
        return jobApplicationMapper.insertJobApplication(jobApplication);
    }

    /**
     * 修改投递记录
     * 
     * @param jobApplication 投递记录
     * @return 结果
     */
    @Override
    public int updateJobApplication(JobApplication jobApplication)
    {
        JobApplication current = getRequiredApplication(jobApplication.getApplicationId());
        if (jobApplication.getApplicationStatus() != null
                && !jobApplication.getApplicationStatus().equals(current.getApplicationStatus()))
        {
            throw new ServiceException("状态变更请使用录用/标记完成动作接口");
        }
        // 防止通过通用编辑覆盖流程关键时间字段
        jobApplication.setApplicationStatus(current.getApplicationStatus());
        jobApplication.setProcessTime(current.getProcessTime());
        jobApplication.setCompleteTime(current.getCompleteTime());
        jobApplication.setUpdateTime(DateUtils.getNowDate());
        return jobApplicationMapper.updateJobApplication(jobApplication);
    }

    /**
     * 批量删除投递记录
     * 
     * @param applicationIds 需要删除的投递记录主键
     * @return 结果
     */
    @Override
    public int deleteJobApplicationByApplicationIds(Long[] applicationIds)
    {
        return jobApplicationMapper.deleteJobApplicationByApplicationIds(applicationIds);
    }

    /**
     * 删除投递记录信息
     * 
     * @param applicationId 投递记录主键
     * @return 结果
     */
    @Override
    public int deleteJobApplicationByApplicationId(Long applicationId)
    {
        return jobApplicationMapper.deleteJobApplicationByApplicationId(applicationId);
    }

    @Override
    public int hireJobApplication(Long applicationId, String enterpriseRemark)
    {
        JobApplication current = getRequiredApplication(applicationId);
        if (!STATUS_APPLIED.equals(current.getApplicationStatus()))
        {
            throw new ServiceException("仅已投递状态可录用");
        }
        return updateStatus(applicationId, STATUS_HIRED, enterpriseRemark, false);
    }

    @Override
    public int completeJobApplication(Long applicationId, String enterpriseRemark)
    {
        JobApplication current = getRequiredApplication(applicationId);
        if (!STATUS_HIRED.equals(current.getApplicationStatus()))
        {
            throw new ServiceException("仅已录用状态可标记完成");
        }
        return updateStatus(applicationId, STATUS_COMPLETED, enterpriseRemark, true);
    }

    private int updateStatus(Long applicationId, String status, String enterpriseRemark, boolean completed)
    {
        JobApplication update = new JobApplication();
        update.setApplicationId(applicationId);
        update.setApplicationStatus(status);
        update.setEnterpriseRemark(enterpriseRemark);
        Date now = DateUtils.getNowDate();
        update.setProcessTime(now);
        if (completed)
        {
            update.setCompleteTime(now);
        }
        update.setUpdateTime(now);
        return jobApplicationMapper.updateJobApplication(update);
    }

    private JobApplication getRequiredApplication(Long applicationId)
    {
        if (applicationId == null)
        {
            throw new ServiceException("投递记录不能为空");
        }
        JobApplication current = jobApplicationMapper.selectJobApplicationByApplicationId(applicationId);
        if (current == null)
        {
            throw new ServiceException("投递记录不存在");
        }
        return current;
    }
}
