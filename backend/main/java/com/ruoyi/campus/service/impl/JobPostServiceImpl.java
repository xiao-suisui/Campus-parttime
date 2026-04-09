package com.ruoyi.campus.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.campus.mapper.JobPostMapper;
import com.ruoyi.campus.domain.JobPost;
import com.ruoyi.campus.service.IJobPostService;

/**
 * 岗位信息Service业务层处理
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
     * 查询岗位信息
     * 
     * @param postId 岗位信息主键
     * @return 岗位信息
     */
    @Override
    public JobPost selectJobPostByPostId(Long postId)
    {
        return jobPostMapper.selectJobPostByPostId(postId);
    }

    /**
     * 查询岗位信息列表
     * 
     * @param jobPost 岗位信息
     * @return 岗位信息
     */
    @Override
    public List<JobPost> selectJobPostList(JobPost jobPost)
    {
        return jobPostMapper.selectJobPostList(jobPost);
    }

    /**
     * 新增岗位信息
     * 
     * @param jobPost 岗位信息
     * @return 结果
     */
    @Override
    public int insertJobPost(JobPost jobPost)
    {
        jobPost.setCreateTime(DateUtils.getNowDate());
        return jobPostMapper.insertJobPost(jobPost);
    }

    /**
     * 修改岗位信息
     * 
     * @param jobPost 岗位信息
     * @return 结果
     */
    @Override
    public int updateJobPost(JobPost jobPost)
    {
        jobPost.setUpdateTime(DateUtils.getNowDate());
        return jobPostMapper.updateJobPost(jobPost);
    }

    /**
     * 批量删除岗位信息
     * 
     * @param postIds 需要删除的岗位信息主键
     * @return 结果
     */
    @Override
    public int deleteJobPostByPostIds(Long[] postIds)
    {
        return jobPostMapper.deleteJobPostByPostIds(postIds);
    }

    /**
     * 删除岗位信息信息
     * 
     * @param postId 岗位信息主键
     * @return 结果
     */
    @Override
    public int deleteJobPostByPostId(Long postId)
    {
        return jobPostMapper.deleteJobPostByPostId(postId);
    }
}
