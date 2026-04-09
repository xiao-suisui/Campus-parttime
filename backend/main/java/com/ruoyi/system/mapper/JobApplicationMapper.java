package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JobApplication;

/**
 * 投递记录Mapper接口
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface JobApplicationMapper 
{
    /**
     * 查询投递记录
     * 
     * @param applicationId 投递记录主键
     * @return 投递记录
     */
    public JobApplication selectJobApplicationByApplicationId(Long applicationId);

    /**
     * 查询投递记录列表
     * 
     * @param jobApplication 投递记录
     * @return 投递记录集合
     */
    public List<JobApplication> selectJobApplicationList(JobApplication jobApplication);

    /**
     * 新增投递记录
     * 
     * @param jobApplication 投递记录
     * @return 结果
     */
    public int insertJobApplication(JobApplication jobApplication);

    /**
     * 修改投递记录
     * 
     * @param jobApplication 投递记录
     * @return 结果
     */
    public int updateJobApplication(JobApplication jobApplication);

    /**
     * 删除投递记录
     * 
     * @param applicationId 投递记录主键
     * @return 结果
     */
    public int deleteJobApplicationByApplicationId(Long applicationId);

    /**
     * 批量删除投递记录
     * 
     * @param applicationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJobApplicationByApplicationIds(Long[] applicationIds);
}
