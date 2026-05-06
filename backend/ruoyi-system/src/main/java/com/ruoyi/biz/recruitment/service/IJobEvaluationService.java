package com.ruoyi.biz.recruitment.service;

import java.util.List;
import com.ruoyi.biz.recruitment.domain.JobEvaluation;


public interface IJobEvaluationService 
{
    
    public JobEvaluation selectJobEvaluationByEvaluationId(Long evaluationId);

    
    public List<JobEvaluation> selectJobEvaluationList(JobEvaluation jobEvaluation);

    
    public int insertJobEvaluation(JobEvaluation jobEvaluation);

    
    public int updateJobEvaluation(JobEvaluation jobEvaluation);

    
    public int deleteJobEvaluationByEvaluationIds(Long[] evaluationIds);

    
    public int deleteJobEvaluationByEvaluationId(Long evaluationId);
}

