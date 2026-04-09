package com.ruoyi.campus.mapper;

import java.util.List;
import com.ruoyi.campus.domain.JobPost;

/**
 * 岗位信息Mapper接口
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface JobPostMapper 
{
    /**
     * 查询岗位信息
     * 
     * @param postId 岗位信息主键
     * @return 岗位信息
     */
    public JobPost selectJobPostByPostId(Long postId);

    /**
     * 查询岗位信息列表
     * 
     * @param jobPost 岗位信息
     * @return 岗位信息集合
     */
    public List<JobPost> selectJobPostList(JobPost jobPost);

    /**
     * 新增岗位信息
     * 
     * @param jobPost 岗位信息
     * @return 结果
     */
    public int insertJobPost(JobPost jobPost);

    /**
     * 修改岗位信息
     * 
     * @param jobPost 岗位信息
     * @return 结果
     */
    public int updateJobPost(JobPost jobPost);

    /**
     * 删除岗位信息
     * 
     * @param postId 岗位信息主键
     * @return 结果
     */
    public int deleteJobPostByPostId(Long postId);

    /**
     * 批量删除岗位信息
     * 
     * @param postIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJobPostByPostIds(Long[] postIds);
}
