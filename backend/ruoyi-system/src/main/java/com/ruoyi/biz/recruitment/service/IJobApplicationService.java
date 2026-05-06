package com.ruoyi.biz.recruitment.service;

import java.util.List;
import com.ruoyi.biz.recruitment.domain.JobApplication;


public interface IJobApplicationService 
{
    
    public JobApplication selectJobApplicationByApplicationId(Long applicationId);

    
    public List<JobApplication> selectJobApplicationList(JobApplication jobApplication);

    
    public int insertJobApplication(JobApplication jobApplication);

    
    public int updateJobApplication(JobApplication jobApplication);

    
    public int deleteJobApplicationByApplicationIds(Long[] applicationIds);

    
    public int deleteJobApplicationByApplicationId(Long applicationId);

    
    public int hireJobApplication(Long applicationId, String enterpriseRemark);


    public int completeJobApplication(Long applicationId, String enterpriseRemark);

    public int rejectJobApplication(Long applicationId, String enterpriseRemark);

    public List<JobApplication> selectApplicationListByEnterpriseId(Long enterpriseId, String applicationStatus);
}

