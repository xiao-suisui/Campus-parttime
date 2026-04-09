package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JobEvaluation;

/**
 * 鍙屽悜浜掕瘎Mapper鎺ュ彛
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface JobEvaluationMapper 
{
    /**
     * 鏌ヨ鍙屽悜浜掕瘎
     * 
     * @param evaluationId 鍙屽悜浜掕瘎涓婚敭
     * @return 鍙屽悜浜掕瘎
     */
    public JobEvaluation selectJobEvaluationByEvaluationId(Long evaluationId);

    /**
     * 鏌ヨ鍙屽悜浜掕瘎鍒楄〃
     * 
     * @param jobEvaluation 鍙屽悜浜掕瘎
     * @return 鍙屽悜浜掕瘎闆嗗悎
     */
    public List<JobEvaluation> selectJobEvaluationList(JobEvaluation jobEvaluation);

    /**
     * 鏂板鍙屽悜浜掕瘎
     * 
     * @param jobEvaluation 鍙屽悜浜掕瘎
     * @return 缁撴灉
     */
    public int insertJobEvaluation(JobEvaluation jobEvaluation);

    /**
     * 淇敼鍙屽悜浜掕瘎
     * 
     * @param jobEvaluation 鍙屽悜浜掕瘎
     * @return 缁撴灉
     */
    public int updateJobEvaluation(JobEvaluation jobEvaluation);

    /**
     * 鍒犻櫎鍙屽悜浜掕瘎
     * 
     * @param evaluationId 鍙屽悜浜掕瘎涓婚敭
     * @return 缁撴灉
     */
    public int deleteJobEvaluationByEvaluationId(Long evaluationId);

    /**
     * 鎵归噺鍒犻櫎鍙屽悜浜掕瘎
     * 
     * @param evaluationIds 闇€瑕佸垹闄ょ殑鏁版嵁涓婚敭闆嗗悎
     * @return 缁撴灉
     */
    public int deleteJobEvaluationByEvaluationIds(Long[] evaluationIds);
}

