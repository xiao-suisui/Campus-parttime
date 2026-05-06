package com.ruoyi.biz.enterprise.service;

import java.util.List;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;
import com.ruoyi.biz.recruitment.domain.JobApplication;
import com.ruoyi.biz.recruitment.domain.JobEvaluation;

/**
 * Enterprise-side business flow service.
 */
public interface IEnterpriseFlowService
{
    EnterpriseInfo getMyProfile(Long enterpriseId);

    int saveMyProfile(Long enterpriseId, EnterpriseInfo profile);

    int submitMyProfileAudit(Long enterpriseId);

    List<JobPost> listMyPosts(Long enterpriseId, JobPost query);

    JobPost getMyPost(Long enterpriseId, Long postId);

    int createMyPost(Long enterpriseId, JobPost post);

    int updateMyPost(Long enterpriseId, JobPost post);

    int upMyPost(Long enterpriseId, Long postId);

    int downMyPost(Long enterpriseId, Long postId);

    int deleteMyPost(Long enterpriseId, Long postId);

    List<JobApplication> listMyApplications(Long enterpriseId, JobApplication query);

    JobApplication getMyApplication(Long enterpriseId, Long applicationId);

    int hireMyApplication(Long enterpriseId, Long applicationId, String enterpriseRemark);

    int completeMyApplication(Long enterpriseId, Long applicationId, String enterpriseRemark);

    int rejectMyApplication(Long enterpriseId, Long applicationId, String enterpriseRemark);

    List<JobEvaluation> listMyEvaluations(Long enterpriseId, JobEvaluation query);

    int evaluateStudent(Long enterpriseId, Long applicationId, Integer score, String content);

    List<EnterpriseInfo> listEnterpriseForAudit(EnterpriseInfo query);

    EnterpriseInfo getEnterpriseForAudit(Long enterpriseId);

    int passEnterpriseAudit(Long auditorId, Long enterpriseId);

    int rejectEnterpriseAudit(Long auditorId, Long enterpriseId, String reason);
}


