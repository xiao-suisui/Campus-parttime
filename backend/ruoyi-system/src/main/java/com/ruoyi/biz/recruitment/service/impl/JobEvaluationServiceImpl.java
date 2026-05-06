package com.ruoyi.biz.recruitment.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.recruitment.domain.JobApplication;
import com.ruoyi.biz.recruitment.mapper.JobApplicationMapper;
import com.ruoyi.biz.recruitment.mapper.JobEvaluationMapper;
import com.ruoyi.biz.recruitment.domain.JobEvaluation;
import com.ruoyi.biz.recruitment.service.IJobEvaluationService;


@Service
public class JobEvaluationServiceImpl implements IJobEvaluationService 
{
    private static final String APPLICATION_STATUS_COMPLETED = "2";
    private static final String EVALUATION_TYPE_STUDENT = "1";
    private static final String EVALUATION_TYPE_ENTERPRISE = "2";

    @Autowired
    private JobEvaluationMapper jobEvaluationMapper;

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    
    @Override
    public JobEvaluation selectJobEvaluationByEvaluationId(Long evaluationId)
    {
        return jobEvaluationMapper.selectJobEvaluationByEvaluationId(evaluationId);
    }

    
    @Override
    public List<JobEvaluation> selectJobEvaluationList(JobEvaluation jobEvaluation)
    {
        return jobEvaluationMapper.selectJobEvaluationList(jobEvaluation);
    }

    
    @Override
    public int insertJobEvaluation(JobEvaluation jobEvaluation)
    {
        validateEvaluationAllowed(jobEvaluation.getApplicationId());
        validateEvaluationPayload(jobEvaluation);
        validateDuplicateEvaluation(jobEvaluation.getApplicationId(), jobEvaluation.getEvaluationType(), null);
        if (jobEvaluation.getEvaluationTime() == null)
        {
            jobEvaluation.setEvaluationTime(new Date());
        }
        jobEvaluation.setCreateTime(DateUtils.getNowDate());
        return jobEvaluationMapper.insertJobEvaluation(jobEvaluation);
    }

    
    @Override
    public int updateJobEvaluation(JobEvaluation jobEvaluation)
    {
        validateEvaluationAllowed(jobEvaluation.getApplicationId());
        validateEvaluationPayload(jobEvaluation);
        validateDuplicateEvaluation(jobEvaluation.getApplicationId(), jobEvaluation.getEvaluationType(), jobEvaluation.getEvaluationId());
        jobEvaluation.setUpdateTime(DateUtils.getNowDate());
        return jobEvaluationMapper.updateJobEvaluation(jobEvaluation);
    }

    
    @Override
    public int deleteJobEvaluationByEvaluationIds(Long[] evaluationIds)
    {
        return jobEvaluationMapper.deleteJobEvaluationByEvaluationIds(evaluationIds);
    }

    
    @Override
    public int deleteJobEvaluationByEvaluationId(Long evaluationId)
    {
        return jobEvaluationMapper.deleteJobEvaluationByEvaluationId(evaluationId);
    }

    private void validateEvaluationAllowed(Long applicationId)
    {
        if (applicationId == null)
        {
            throw new ServiceException("Application record id cannot be null");
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

    private void validateEvaluationPayload(JobEvaluation jobEvaluation)
    {
        if (jobEvaluation == null)
        {
            throw new ServiceException("评价数据不能为空");
        }
        String evaluationType = jobEvaluation.getEvaluationType();
        if (!EVALUATION_TYPE_STUDENT.equals(evaluationType) && !EVALUATION_TYPE_ENTERPRISE.equals(evaluationType))
        {
            throw new ServiceException("评价类型不合法");
        }
        Integer score = jobEvaluation.getScore();
        if (score == null || score < 1 || score > 5)
        {
            throw new ServiceException("评分必须在 1 到 5 之间");
        }
    }

    private void validateDuplicateEvaluation(Long applicationId, String evaluationType, Long excludeEvaluationId)
    {
        int count = jobEvaluationMapper.countByApplicationAndType(applicationId, evaluationType, excludeEvaluationId);
        if (count > 0)
        {
            throw new ServiceException("同一投递记录下该评价类型已存在，不能重复评价");
        }
    }
}

