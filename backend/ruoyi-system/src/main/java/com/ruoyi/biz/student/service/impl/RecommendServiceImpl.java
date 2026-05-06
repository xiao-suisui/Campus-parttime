package com.ruoyi.biz.student.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.recruitment.domain.JobApplication;
import com.ruoyi.biz.recruitment.domain.JobCollection;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.biz.recruitment.service.IJobApplicationService;
import com.ruoyi.biz.recruitment.service.IJobCollectionService;
import com.ruoyi.biz.recruitment.service.IJobPostService;
import com.ruoyi.biz.student.domain.StudentResume;
import com.ruoyi.biz.student.service.IRecommendService;
import com.ruoyi.biz.student.service.IStudentResumeService;

/**
 * 基于规则的岗位推荐实现
 */
@Service
public class RecommendServiceImpl implements IRecommendService
{
    private static final String POST_STATUS_ONLINE = "1";
    private static final String DEL_FLAG_DELETED = "2";

    @Autowired
    private IStudentResumeService studentResumeService;

    @Autowired
    private IJobPostService jobPostService;

    @Autowired
    private IJobApplicationService jobApplicationService;

    @Autowired
    private IJobCollectionService jobCollectionService;

    @Override
    public List<JobPost> recommendPosts(Long studentId, int limit)
    {
        if (studentId == null || limit <= 0)
        {
            return Collections.emptyList();
        }

        // 1. 获取学生简历信息
        StudentResume resume = getStudentResume(studentId);
        Set<String> studentTags = parseTags(resume);

        // 2. 获取学生历史投递和收藏的岗位关键词
        Set<String> historyKeywords = getHistoryKeywords(studentId);

        // 3. 获取所有上架岗位
        JobPost condition = new JobPost();
        condition.setPostStatus(POST_STATUS_ONLINE);
        List<JobPost> allPosts = jobPostService.selectJobPostList(condition);
        Date now = new Date();

        // 4. 计算匹配分数并排序
        List<ScoredPost> scored = new ArrayList<>();
        for (JobPost post : allPosts)
        {
            if (DEL_FLAG_DELETED.equals(post.getDelFlag()))
            {
                continue;
            }
            if (post.getDeadlineTime() != null && post.getDeadlineTime().before(now))
            {
                continue;
            }
            double score = calculateScore(post, studentTags, historyKeywords);
            if (score > 0)
            {
                scored.add(new ScoredPost(post, score));
            }
        }

        scored.sort(Comparator.comparingDouble(ScoredPost::getScore).reversed());

        List<JobPost> result = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, scored.size()); i++)
        {
            result.add(scored.get(i).getPost());
        }
        return result;
    }

    private StudentResume getStudentResume(Long studentId)
    {
        StudentResume condition = new StudentResume();
        condition.setStudentId(studentId);
        List<StudentResume> list = studentResumeService.selectStudentResumeList(condition);
        return list.isEmpty() ? null : list.get(0);
    }

    private Set<String> parseTags(StudentResume resume)
    {
        Set<String> tags = new HashSet<>();
        if (resume == null)
        {
            return tags;
        }
        // 解析技能标签
        if (resume.getSkillTags() != null && !resume.getSkillTags().isEmpty())
        {
            for (String tag : resume.getSkillTags().split("[,，\\s]+"))
            {
                String trimmed = tag.trim();
                if (!trimmed.isEmpty())
                {
                    tags.add(trimmed.toLowerCase());
                }
            }
        }
        // 解析求职意向关键词
        if (resume.getExpectedPosition() != null && !resume.getExpectedPosition().isEmpty())
        {
            for (String word : resume.getExpectedPosition().split("[,，\\s]+"))
            {
                String trimmed = word.trim();
                if (!trimmed.isEmpty())
                {
                    tags.add(trimmed.toLowerCase());
                }
            }
        }
        return tags;
    }

    private Set<String> getHistoryKeywords(Long studentId)
    {
        Set<String> keywords = new HashSet<>();

        // 从投递记录中提取岗位名称关键词
        JobApplication appCondition = new JobApplication();
        appCondition.setStudentId(studentId);
        List<JobApplication> apps = jobApplicationService.selectJobApplicationList(appCondition);
        for (JobApplication app : apps)
        {
            JobPost post = jobPostService.selectJobPostByPostId(app.getPostId());
            if (post != null)
            {
                addNameKeywords(post.getPostName(), keywords);
                if (post.getWorkType() != null)
                {
                    keywords.add(post.getWorkType());
                }
            }
        }

        // 从收藏记录中提取岗位名称关键词
        JobCollection colCondition = new JobCollection();
        colCondition.setStudentId(studentId);
        List<JobCollection> cols = jobCollectionService.selectJobCollectionList(colCondition);
        for (JobCollection col : cols)
        {
            JobPost post = jobPostService.selectJobPostByPostId(col.getPostId());
            if (post != null)
            {
                addNameKeywords(post.getPostName(), keywords);
                if (post.getWorkType() != null)
                {
                    keywords.add(post.getWorkType());
                }
            }
        }

        return keywords;
    }

    private void addNameKeywords(String name, Set<String> keywords)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }
        for (String word : name.split("[\\s]+"))
        {
            String trimmed = word.trim();
            if (trimmed.length() >= 2)
            {
                keywords.add(trimmed.toLowerCase());
            }
        }
    }

    private double calculateScore(JobPost post, Set<String> studentTags, Set<String> historyKeywords)
    {
        double score = 0;

        // 1. 技能标签匹配（权重最高）
        String postText = buildPostText(post);
        String postLower = postText.toLowerCase();
        for (String tag : studentTags)
        {
            if (postLower.contains(tag))
            {
                score += 10;
            }
        }

        // 2. 历史偏好匹配
        for (String keyword : historyKeywords)
        {
            if (postLower.contains(keyword))
            {
                score += 5;
            }
        }

        // 3. 岗位名称与求职意向的交集
        if (post.getPostName() != null)
        {
            String postNameLower = post.getPostName().toLowerCase();
            for (String tag : studentTags)
            {
                if (postNameLower.contains(tag))
                {
                    score += 8;
                }
            }
        }

        // 4. 轻微加权：热门岗位（投递量高的优先展示）
        if (post.getApplyCount() != null && post.getApplyCount() > 0)
        {
            score += Math.min(post.getApplyCount(), 10) * 0.5;
        }

        return score;
    }

    private String buildPostText(JobPost post)
    {
        StringBuilder sb = new StringBuilder();
        if (post.getPostName() != null)
        {
            sb.append(post.getPostName()).append(" ");
        }
        if (post.getRequirementDesc() != null)
        {
            sb.append(post.getRequirementDesc()).append(" ");
        }
        if (post.getWorkAddress() != null)
        {
            sb.append(post.getWorkAddress()).append(" ");
        }
        return sb.toString();
    }

    private static class ScoredPost
    {
        private final JobPost post;
        private final double score;

        ScoredPost(JobPost post, double score)
        {
            this.post = post;
            this.score = score;
        }

        JobPost getPost()
        {
            return post;
        }

        double getScore()
        {
            return score;
        }
    }
}
