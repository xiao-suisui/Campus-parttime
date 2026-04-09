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
 * 鍙屽悜浜掕瘎Service涓氬姟灞傚鐞?
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
     * 鏌ヨ鍙屽悜浜掕瘎
     * 
     * @param evaluationId 鍙屽悜浜掕瘎涓婚敭
     * @return 鍙屽悜浜掕瘎
     */
    @Override
    public JobEvaluation selectJobEvaluationByEvaluationId(Long evaluationId)
    {
        return jobEvaluationMapper.selectJobEvaluationByEvaluationId(evaluationId);
    }

    /**
     * 鏌ヨ鍙屽悜浜掕瘎鍒楄〃
     * 
     * @param jobEvaluation 鍙屽悜浜掕瘎
     * @return 鍙屽悜浜掕瘎
     */
    @Override
    public List<JobEvaluation> selectJobEvaluationList(JobEvaluation jobEvaluation)
    {
        return jobEvaluationMapper.selectJobEvaluationList(jobEvaluation);
    }

    /**
     * 鏂板鍙屽悜浜掕瘎
     * 
     * @param jobEvaluation 鍙屽悜浜掕瘎
     * @return 缁撴灉
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
     * 淇敼鍙屽悜浜掕瘎
     * 
     * @param jobEvaluation 鍙屽悜浜掕瘎
     * @return 缁撴灉
     */
    @Override
    public int updateJobEvaluation(JobEvaluation jobEvaluation)
    {
        validateEvaluationAllowed(jobEvaluation.getApplicationId());
        jobEvaluation.setUpdateTime(DateUtils.getNowDate());
        return jobEvaluationMapper.updateJobEvaluation(jobEvaluation);
    }

    /**
     * 鎵归噺鍒犻櫎鍙屽悜浜掕瘎
     * 
     * @param evaluationIds 闇€瑕佸垹闄ょ殑鍙屽悜浜掕瘎涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteJobEvaluationByEvaluationIds(Long[] evaluationIds)
    {
        return jobEvaluationMapper.deleteJobEvaluationByEvaluationIds(evaluationIds);
    }

    /**
     * 鍒犻櫎鍙屽悜浜掕瘎淇℃伅
     * 
     * @param evaluationId 鍙屽悜浜掕瘎涓婚敭
     * @return 缁撴灉
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
            throw new ServiceException("鎶曢€掕褰曚笉鑳戒负绌?);
        }
        JobApplication application = jobApplicationMapper.selectJobApplicationByApplicationId(applicationId);
        if (application == null)
        {
            throw new ServiceException("鎶曢€掕褰曚笉瀛樺湪");
        }
        if (!APPLICATION_STATUS_COMPLETED.equals(application.getApplicationStatus()))
        {
            throw new ServiceException("浠呬紒涓氭爣璁板畬鎴愬悗鎵嶅彲浜掕瘎");
        }
    }
}

