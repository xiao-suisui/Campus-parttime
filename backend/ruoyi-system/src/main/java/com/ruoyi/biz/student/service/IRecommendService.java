package com.ruoyi.biz.student.service;

import java.util.List;
import com.ruoyi.biz.recruitment.domain.JobPost;

/**
 * 智能推荐服务
 */
public interface IRecommendService
{
    /**
     * 为学生推荐岗位
     *
     * @param studentId 学生ID
     * @param limit     推荐数量
     * @return 推荐的岗位列表（按匹配度降序）
     */
    List<JobPost> recommendPosts(Long studentId, int limit);
}
