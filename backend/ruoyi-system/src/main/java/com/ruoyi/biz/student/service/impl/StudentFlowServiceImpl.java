package com.ruoyi.biz.student.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.biz.recruitment.service.IJobPostService;
import com.ruoyi.biz.student.service.IStudentFlowService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.biz.recruitment.domain.JobApplication;
import com.ruoyi.biz.recruitment.domain.JobCollection;
import com.ruoyi.biz.recruitment.domain.JobEvaluation;
import com.ruoyi.biz.student.domain.StudentInfo;
import com.ruoyi.biz.student.domain.StudentResume;
import com.ruoyi.biz.recruitment.service.IJobApplicationService;
import com.ruoyi.biz.recruitment.service.IJobCollectionService;
import com.ruoyi.biz.recruitment.service.IJobEvaluationService;
import com.ruoyi.biz.student.service.IStudentInfoService;
import com.ruoyi.biz.student.service.IStudentResumeService;

/**
 * Student-side business flow implementation.
 */
@Service
public class StudentFlowServiceImpl implements IStudentFlowService
{
    private static final String POST_STATUS_ONLINE = "1";
    private static final String DEL_FLAG_DELETED = "2";
    private static final String APPLICATION_STATUS_COMPLETED = "2";
    private static final String EVALUATION_TYPE_STUDENT_TO_ENTERPRISE = "1";
    private static final String RESUME_STATUS_ACTIVE = "0";

    @Autowired
    private IJobPostService jobPostService;

    @Autowired
    private IJobCollectionService jobCollectionService;

    @Autowired
    private IJobApplicationService jobApplicationService;

    @Autowired
    private IJobEvaluationService jobEvaluationService;

    @Autowired
    private IStudentInfoService studentInfoService;

    @Autowired
    private IStudentResumeService studentResumeService;

    @Override
    public List<JobPost> listAvailablePosts(JobPost query)
    {
        JobPost condition = query == null ? new JobPost() : query;
        condition.setPostStatus(POST_STATUS_ONLINE);
        List<JobPost> raw = jobPostService.selectJobPostList(condition);
        List<JobPost> result = new ArrayList<>();
        Date now = new Date();
        for (JobPost post : raw)
        {
            if (isPostAvailable(post, now))
            {
                result.add(post);
            }
        }
        return result;
    }

    @Override
    public JobPost getAvailablePostDetail(Long postId)
    {
        JobPost post = jobPostService.selectJobPostByPostId(postId);
        if (!isPostAvailable(post, new Date()))
        {
            throw new ServiceException("岗位不存在或不可投递");
        }
        return post;
    }

    @Override
    public List<JobCollection> listMyCollections(Long studentId, JobCollection query)
    {
        ensureStudentValid(studentId);
        JobCollection condition = query == null ? new JobCollection() : query;
        condition.setStudentId(studentId);
        return jobCollectionService.selectJobCollectionList(condition);
    }

    @Override
    public boolean collectPost(Long studentId, Long postId)
    {
        ensureStudentValid(studentId);
        getAvailablePostDetail(postId);

        JobCollection condition = new JobCollection();
        condition.setStudentId(studentId);
        condition.setPostId(postId);
        List<JobCollection> exists = jobCollectionService.selectJobCollectionList(condition);
        if (!exists.isEmpty())
        {
            return false;
        }

        JobCollection collection = new JobCollection();
        collection.setStudentId(studentId);
        collection.setPostId(postId);
        collection.setCollectionTime(new Date());
        try
        {
            jobCollectionService.insertJobCollection(collection);
        }
        catch (DuplicateKeyException ex)
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean uncollectPost(Long studentId, Long postId)
    {
        ensureStudentValid(studentId);
        JobCollection condition = new JobCollection();
        condition.setStudentId(studentId);
        condition.setPostId(postId);
        List<JobCollection> exists = jobCollectionService.selectJobCollectionList(condition);
        if (exists.isEmpty())
        {
            return false;
        }
        Long[] ids = exists.stream().map(JobCollection::getCollectionId).toArray(Long[]::new);
        return jobCollectionService.deleteJobCollectionByCollectionIds(ids) > 0;
    }

    @Override
    public List<JobApplication> listMyApplications(Long studentId, JobApplication query)
    {
        ensureStudentValid(studentId);
        JobApplication condition = query == null ? new JobApplication() : query;
        condition.setStudentId(studentId);
        return jobApplicationService.selectJobApplicationList(condition);
    }

    @Override
    public JobApplication getMyApplicationDetail(Long studentId, Long applicationId)
    {
        ensureStudentValid(studentId);
        JobApplication application = jobApplicationService.selectJobApplicationByApplicationId(applicationId);
        if (application == null || !studentId.equals(application.getStudentId()))
        {
            throw new ServiceException("投递记录不存在");
        }
        return application;
    }

    @Override
    public long applyPost(Long studentId, Long postId, Long resumeId)
    {
        ensureStudentValid(studentId);
        getAvailablePostDetail(postId);
        ensureResumeValidForStudent(studentId, resumeId);

        JobApplication application = new JobApplication();
        application.setStudentId(studentId);
        application.setPostId(postId);
        application.setResumeId(resumeId);

        try
        {
            jobApplicationService.insertJobApplication(application);
        }
        catch (DuplicateKeyException ex)
        {
            throw new ServiceException("请勿重复投递同一岗位");
        }
        return application.getApplicationId();
    }

    @Override
    public long evaluateCompletedApplication(Long studentId, Long applicationId, Integer score, String content)
    {
        ensureStudentValid(studentId);
        JobApplication application = getMyApplicationDetail(studentId, applicationId);
        if (!APPLICATION_STATUS_COMPLETED.equals(application.getApplicationStatus()))
        {
            throw new ServiceException("仅企业标记完成后可互评");
        }

        JobEvaluation evaluation = new JobEvaluation();
        evaluation.setApplicationId(applicationId);
        evaluation.setPostId(application.getPostId());
        evaluation.setStudentId(studentId);
        evaluation.setEvaluationType(EVALUATION_TYPE_STUDENT_TO_ENTERPRISE);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setEvaluationTime(new Date());
        try
        {
            jobEvaluationService.insertJobEvaluation(evaluation);
        }
        catch (DuplicateKeyException ex)
        {
            throw new ServiceException("该投递记录已完成学生评价");
        }
        return evaluation.getEvaluationId();
    }

    @Override
    public List<JobEvaluation> listMyEvaluations(Long studentId, JobEvaluation query)
    {
        ensureStudentValid(studentId);
        JobEvaluation condition = query == null ? new JobEvaluation() : query;
        condition.setStudentId(studentId);
        condition.setEvaluationType(EVALUATION_TYPE_STUDENT_TO_ENTERPRISE);
        return jobEvaluationService.selectJobEvaluationList(condition);
    }

    @Override
    public StudentInfo getMyInfo(Long studentId)
    {
        ensureStudentValid(studentId);
        return studentInfoService.selectStudentInfoByStudentId(studentId);
    }

    @Override
    public int saveMyInfo(Long studentId, StudentInfo info)
    {
        ensureStudentValid(studentId);
        if (info == null)
        {
            throw new ServiceException("学生信息不能为空");
        }
        info.setStudentId(studentId);
        StudentInfo current = studentInfoService.selectStudentInfoByStudentId(studentId);
        if (current == null)
        {
            info.setDelFlag("0");
            return studentInfoService.insertStudentInfo(info);
        }
        info.setDelFlag(current.getDelFlag());
        return studentInfoService.updateStudentInfo(info);
    }

    @Override
    public StudentResume getMyResume(Long studentId)
    {
        ensureStudentValid(studentId);
        StudentResume condition = new StudentResume();
        condition.setStudentId(studentId);
        List<StudentResume> list = studentResumeService.selectStudentResumeList(condition);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int saveMyResume(Long studentId, StudentResume resume)
    {
        ensureStudentValid(studentId);
        if (resume == null)
        {
            throw new ServiceException("简历信息不能为空");
        }
        resume.setStudentId(studentId);
        StudentResume current = getMyResume(studentId);
        if (current == null)
        {
            resume.setStatus(RESUME_STATUS_ACTIVE);
            resume.setDelFlag("0");
            return studentResumeService.insertStudentResume(resume);
        }
        resume.setResumeId(current.getResumeId());
        resume.setStatus(current.getStatus());
        resume.setDelFlag(current.getDelFlag());
        return studentResumeService.updateStudentResume(resume);
    }

    private void ensureStudentValid(Long studentId)
    {
        if (studentId == null)
        {
            throw new ServiceException("学生身份无效");
        }
        StudentInfo studentInfo = studentInfoService.selectStudentInfoByStudentId(studentId);
        if (studentInfo == null || DEL_FLAG_DELETED.equals(studentInfo.getDelFlag()))
        {
            throw new ServiceException("学生信息不存在");
        }
    }

    private void ensureResumeValidForStudent(Long studentId, Long resumeId)
    {
        if (resumeId == null)
        {
            throw new ServiceException("简历不能为空");
        }
        StudentResume resume = studentResumeService.selectStudentResumeByResumeId(resumeId);
        if (resume == null || !studentId.equals(resume.getStudentId()))
        {
            throw new ServiceException("简历不存在或不属于当前学生");
        }
        if (!RESUME_STATUS_ACTIVE.equals(resume.getStatus()) || DEL_FLAG_DELETED.equals(resume.getDelFlag()))
        {
            throw new ServiceException("简历不可用，请先启用有效简历");
        }
    }

    private boolean isPostAvailable(JobPost post, Date now)
    {
        if (post == null)
        {
            return false;
        }
        if (!POST_STATUS_ONLINE.equals(post.getPostStatus()))
        {
            return false;
        }
        if (DEL_FLAG_DELETED.equals(post.getDelFlag()))
        {
            return false;
        }
        return post.getDeadlineTime() == null || !post.getDeadlineTime().before(now);
    }
}


