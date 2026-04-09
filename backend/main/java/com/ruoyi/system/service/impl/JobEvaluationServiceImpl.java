package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.JobApplication;
import com.ruoyi.system.mapper.JobApplicationMapper;
import com.ruoyi.system.mapper.JobEvaluationMapper;
import com.ruoyi.system.domain.JobEvaluation;
import com.ruoyi.system.service.IJobEvaluationService;

/**
 * 双向互评Service业务层处理
 * 
 * @author wly
 * @date 2026-04-08
 */
@Service
public class JobEvaluationServiceImpl implements IJobEvaluationService 
{
    private static final String APPLICATION_STATUS_COMPLETED = "2";

    @Autowired
    private JobEvaluationMapper jobEvaluationMapper;

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    /**
     * 查询双向互评
     * 
     * @param evaluationId 双向互评主键
     * @return 双向互评
     */
    @Override
    public JobEvaluation selectJobEvaluationByEvaluationId(Long evaluationId)
    {
        return jobEvaluationMapper.selectJobEvaluationByEvaluationId(evaluationId);
    }

    /**
     * 查询双向互评列表
     * 
     * @param jobEvaluation 双向互评
     * @return 双向互评
     */
    @Override
    public List<JobEvaluation> selectJobEvaluationList(JobEvaluation jobEvaluation)
    {
        return jobEvaluationMapper.selectJobEvaluationList(jobEvaluation);
    }

    /**
     * 新增双向互评
     * 
     * @param jobEvaluation 双向互评
     * @return 结果
     */
    @Override
    public int insertJobEvaluation(JobEvaluation jobEvaluation)
    {
        validateEvaluationAllowed(jobEvaluation.getApplicationId());
        if (jobEvaluation.getEvaluationTime() == null)
        {
            jobEvaluation.setEvaluationTime(new Date());
        }
        jobEvaluation.setCreateTime(DateUtils.getNowDate());
        return jobEvaluationMapper.insertJobEvaluation(jobEvaluation);
    }

    /**
     * 修改双向互评
     * 
     * @param jobEvaluation 双向互评
     * @return 结果
     */
    @Override
    public int updateJobEvaluation(JobEvaluation jobEvaluation)
    {
        validateEvaluationAllowed(jobEvaluation.getApplicationId());
        jobEvaluation.setUpdateTime(DateUtils.getNowDate());
        return jobEvaluationMapper.updateJobEvaluation(jobEvaluation);
    }

    /**
     * 批量删除双向互评
     * 
     * @param evaluationIds 需要删除的双向互评主键
     * @return 结果
     */
    @Override
    public int deleteJobEvaluationByEvaluationIds(Long[] evaluationIds)
    {
        return jobEvaluationMapper.deleteJobEvaluationByEvaluationIds(evaluationIds);
    }

    /**
     * 删除双向互评信息
     * 
     * @param evaluationId 双向互评主键
     * @return 结果
     */
    @Override
    public int deleteJobEvaluationByEvaluationId(Long evaluationId)
    {
        return jobEvaluationMapper.deleteJobEvaluationByEvaluationId(evaluationId);
    }

    private void validateEvaluationAllowed(Long applicationId)
    {
        if (applicationId == null)
        {
            throw new ServiceException("投递记录不能为空");
        }
        JobApplication application = jobApplicationMapper.selectJobApplicationByApplicationId(applicationId);
        if (application == null)
        {
            throw new ServiceException("投递记录不存在");
        }
        if (!APPLICATION_STATUS_COMPLETED.equals(application.getApplicationStatus()))
        {
            throw new ServiceException("仅企业标记完成后才可互评");
        }
    }
}
