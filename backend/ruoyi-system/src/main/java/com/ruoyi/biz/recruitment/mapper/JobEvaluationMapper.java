package com.ruoyi.biz.recruitment.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.biz.recruitment.domain.JobEvaluation;


public interface JobEvaluationMapper 
{
    
    public JobEvaluation selectJobEvaluationByEvaluationId(Long evaluationId);

    
    public List<JobEvaluation> selectJobEvaluationList(JobEvaluation jobEvaluation);

    
    public int insertJobEvaluation(JobEvaluation jobEvaluation);

    
    public int updateJobEvaluation(JobEvaluation jobEvaluation);

    
    public int deleteJobEvaluationByEvaluationId(Long evaluationId);

    
    public int deleteJobEvaluationByEvaluationIds(Long[] evaluationIds);

    
    public int countByApplicationAndType(@Param("applicationId") Long applicationId,
                                         @Param("evaluationType") String evaluationType,
                                         @Param("excludeEvaluationId") Long excludeEvaluationId);
}

