package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.JobEvaluation;

/**
 * 双向互评Service接口
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface IJobEvaluationService 
{
    /**
     * 查询双向互评
     * 
     * @param evaluationId 双向互评主键
     * @return 双向互评
     */
    public JobEvaluation selectJobEvaluationByEvaluationId(Long evaluationId);

    /**
     * 查询双向互评列表
     * 
     * @param jobEvaluation 双向互评
     * @return 双向互评集合
     */
    public List<JobEvaluation> selectJobEvaluationList(JobEvaluation jobEvaluation);

    /**
     * 新增双向互评
     * 
     * @param jobEvaluation 双向互评
     * @return 结果
     */
    public int insertJobEvaluation(JobEvaluation jobEvaluation);

    /**
     * 修改双向互评
     * 
     * @param jobEvaluation 双向互评
     * @return 结果
     */
    public int updateJobEvaluation(JobEvaluation jobEvaluation);

    /**
     * 批量删除双向互评
     * 
     * @param evaluationIds 需要删除的双向互评主键集合
     * @return 结果
     */
    public int deleteJobEvaluationByEvaluationIds(Long[] evaluationIds);

    /**
     * 删除双向互评信息
     * 
     * @param evaluationId 双向互评主键
     * @return 结果
     */
    public int deleteJobEvaluationByEvaluationId(Long evaluationId);
}
