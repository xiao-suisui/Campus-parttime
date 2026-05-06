package com.ruoyi.biz.recruitment.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.biz.recruitment.domain.JobApplication;


public interface JobApplicationMapper
{

    public JobApplication selectJobApplicationByApplicationId(Long applicationId);


    public List<JobApplication> selectJobApplicationList(JobApplication jobApplication);


    public int insertJobApplication(JobApplication jobApplication);


    public int updateJobApplication(JobApplication jobApplication);


    public int deleteJobApplicationByApplicationId(Long applicationId);


    public int deleteJobApplicationByApplicationIds(Long[] applicationIds);

    public List<JobApplication> selectApplicationListByEnterpriseId(@Param("enterpriseId") Long enterpriseId,
                                                                    @Param("applicationStatus") String applicationStatus);
}

