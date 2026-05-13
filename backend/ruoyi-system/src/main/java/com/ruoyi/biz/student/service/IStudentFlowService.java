package com.ruoyi.biz.student.service;

import java.util.List;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.biz.recruitment.domain.JobApplication;
import com.ruoyi.biz.recruitment.domain.JobCollection;
import com.ruoyi.biz.recruitment.domain.JobEvaluation;
import com.ruoyi.biz.student.domain.StudentInfo;
import com.ruoyi.biz.student.domain.StudentResume;

/**
 * Student flow service for campus part-time business.
 */
public interface IStudentFlowService
{
    List<JobPost> listAvailablePosts(JobPost query);

    JobPost getAvailablePostDetail(Long postId);

    List<JobCollection> listMyCollections(Long studentId, JobCollection query);

    boolean collectPost(Long studentId, Long postId);

    boolean uncollectPost(Long studentId, Long postId);

    List<JobApplication> listMyApplications(Long studentId, JobApplication query);

    JobApplication getMyApplicationDetail(Long studentId, Long applicationId);

    long applyPost(Long studentId, Long postId, Long resumeId);

    long evaluateCompletedApplication(Long studentId, Long applicationId, Integer score, String content);

    List<JobEvaluation> listMyEvaluations(Long studentId, JobEvaluation query);

    List<JobEvaluation> listEnterpriseEvaluations(Long enterpriseId, JobEvaluation query);

    List<JobEvaluation> listEvaluationsAboutMe(Long studentId, JobEvaluation query);

    StudentInfo getMyInfo(Long studentId);

    int saveMyInfo(Long studentId, StudentInfo info);

    StudentResume getMyResume(Long studentId);

    int saveMyResume(Long studentId, StudentResume resume);
}


