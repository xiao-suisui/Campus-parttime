package com.ruoyi.campus.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.campus.mapper.JobPostMapper;
import com.ruoyi.campus.domain.JobPost;
import com.ruoyi.campus.service.IJobPostService;

/**
 * 宀椾綅淇℃伅Service涓氬姟灞傚鐞?
 * 
 * @author wly
 * @date 2026-04-08
 */
@Service
public class JobPostServiceImpl implements IJobPostService 
{
    @Autowired
    private JobPostMapper jobPostMapper;

    /**
     * 鏌ヨ宀椾綅淇℃伅
     * 
     * @param postId 宀椾綅淇℃伅涓婚敭
     * @return 宀椾綅淇℃伅
     */
    @Override
    public JobPost selectJobPostByPostId(Long postId)
    {
        return jobPostMapper.selectJobPostByPostId(postId);
    }

    /**
     * 鏌ヨ宀椾綅淇℃伅鍒楄〃
     * 
     * @param jobPost 宀椾綅淇℃伅
     * @return 宀椾綅淇℃伅
     */
    @Override
    public List<JobPost> selectJobPostList(JobPost jobPost)
    {
        return jobPostMapper.selectJobPostList(jobPost);
    }

    /**
     * 鏂板宀椾綅淇℃伅
     * 
     * @param jobPost 宀椾綅淇℃伅
     * @return 缁撴灉
     */
    @Override
    public int insertJobPost(JobPost jobPost)
    {
        jobPost.setCreateTime(DateUtils.getNowDate());
        return jobPostMapper.insertJobPost(jobPost);
    }

    /**
     * 淇敼宀椾綅淇℃伅
     * 
     * @param jobPost 宀椾綅淇℃伅
     * @return 缁撴灉
     */
    @Override
    public int updateJobPost(JobPost jobPost)
    {
        jobPost.setUpdateTime(DateUtils.getNowDate());
        return jobPostMapper.updateJobPost(jobPost);
    }

    /**
     * 鎵归噺鍒犻櫎宀椾綅淇℃伅
     * 
     * @param postIds 闇€瑕佸垹闄ょ殑宀椾綅淇℃伅涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteJobPostByPostIds(Long[] postIds)
    {
        return jobPostMapper.deleteJobPostByPostIds(postIds);
    }

    /**
     * 鍒犻櫎宀椾綅淇℃伅淇℃伅
     * 
     * @param postId 宀椾綅淇℃伅涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteJobPostByPostId(Long postId)
    {
        return jobPostMapper.deleteJobPostByPostId(postId);
    }
}

