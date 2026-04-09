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
 * 鎶曢€掕褰昐ervice涓氬姟灞傚鐞?
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
     * 鏌ヨ鎶曢€掕褰?
     * 
     * @param applicationId 鎶曢€掕褰曚富閿?
     * @return 鎶曢€掕褰?
     */
    @Override
    public JobApplication selectJobApplicationByApplicationId(Long applicationId)
    {
        return jobApplicationMapper.selectJobApplicationByApplicationId(applicationId);
    }

    /**
     * 鏌ヨ鎶曢€掕褰曞垪琛?
     * 
     * @param jobApplication 鎶曢€掕褰?
     * @return 鎶曢€掕褰?
     */
    @Override
    public List<JobApplication> selectJobApplicationList(JobApplication jobApplication)
    {
        return jobApplicationMapper.selectJobApplicationList(jobApplication);
    }

    /**
     * 鏂板鎶曢€掕褰?
     * 
     * @param jobApplication 鎶曢€掕褰?
     * @return 缁撴灉
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
     * 淇敼鎶曢€掕褰?
     * 
     * @param jobApplication 鎶曢€掕褰?
     * @return 缁撴灉
     */
    @Override
    public int updateJobApplication(JobApplication jobApplication)
    {
        JobApplication current = getRequiredApplication(jobApplication.getApplicationId());
        if (jobApplication.getApplicationStatus() != null
                && !jobApplication.getApplicationStatus().equals(current.getApplicationStatus()))
        {
            throw new ServiceException("鐘舵€佸彉鏇磋浣跨敤褰曠敤/鏍囪瀹屾垚鍔ㄤ綔鎺ュ彛");
        }
        // 闃叉閫氳繃閫氱敤缂栬緫瑕嗙洊娴佺▼鍏抽敭鏃堕棿瀛楁
        jobApplication.setApplicationStatus(current.getApplicationStatus());
        jobApplication.setProcessTime(current.getProcessTime());
        jobApplication.setCompleteTime(current.getCompleteTime());
        jobApplication.setUpdateTime(DateUtils.getNowDate());
        return jobApplicationMapper.updateJobApplication(jobApplication);
    }

    /**
     * 鎵归噺鍒犻櫎鎶曢€掕褰?
     * 
     * @param applicationIds 闇€瑕佸垹闄ょ殑鎶曢€掕褰曚富閿?
     * @return 缁撴灉
     */
    @Override
    public int deleteJobApplicationByApplicationIds(Long[] applicationIds)
    {
        return jobApplicationMapper.deleteJobApplicationByApplicationIds(applicationIds);
    }

    /**
     * 鍒犻櫎鎶曢€掕褰曚俊鎭?
     * 
     * @param applicationId 鎶曢€掕褰曚富閿?
     * @return 缁撴灉
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
            throw new ServiceException("浠呭凡鎶曢€掔姸鎬佸彲褰曠敤");
        }
        return updateStatus(applicationId, STATUS_HIRED, enterpriseRemark, false);
    }

    @Override
    public int completeJobApplication(Long applicationId, String enterpriseRemark)
    {
        JobApplication current = getRequiredApplication(applicationId);
        if (!STATUS_HIRED.equals(current.getApplicationStatus()))
        {
            throw new ServiceException("浠呭凡褰曠敤鐘舵€佸彲鏍囪瀹屾垚");
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
            throw new ServiceException("鎶曢€掕褰曚笉鑳戒负绌?);
        }
        JobApplication current = jobApplicationMapper.selectJobApplicationByApplicationId(applicationId);
        if (current == null)
        {
            throw new ServiceException("鎶曢€掕褰曚笉瀛樺湪");
        }
        return current;
    }
}

