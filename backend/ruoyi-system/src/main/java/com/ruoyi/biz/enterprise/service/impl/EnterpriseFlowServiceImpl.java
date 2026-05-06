package com.ruoyi.biz.enterprise.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.biz.recruitment.service.IJobPostService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.biz.enterprise.service.IEnterpriseFlowService;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;
import com.ruoyi.biz.recruitment.domain.JobApplication;
import com.ruoyi.biz.recruitment.domain.JobEvaluation;
import com.ruoyi.biz.enterprise.service.IEnterpriseInfoService;
import com.ruoyi.biz.recruitment.service.IJobApplicationService;
import com.ruoyi.biz.recruitment.service.IJobEvaluationService;

/**
 * Enterprise-side business flow implementation.
 */
@Service
public class EnterpriseFlowServiceImpl implements IEnterpriseFlowService
{
    private static final String DEL_FLAG_DELETED = "2";
    private static final String POST_STATUS_DRAFT = "0";
    private static final String POST_STATUS_ONLINE = "1";
    private static final String POST_STATUS_OFFLINE = "2";
    private static final String AUDIT_PENDING = "0";
    private static final String AUDIT_PASS = "1";
    private static final String AUDIT_REJECT = "2";
    private static final String APPLICATION_STATUS_COMPLETED = "2";
    private static final String EVALUATION_TYPE_ENTERPRISE_TO_STUDENT = "2";

    @Autowired
    private IEnterpriseInfoService enterpriseInfoService;

    @Autowired
    private IJobPostService jobPostService;

    @Autowired
    private IJobApplicationService jobApplicationService;

    @Autowired
    private IJobEvaluationService jobEvaluationService;

    @Override
    public EnterpriseInfo getMyProfile(Long enterpriseId)
    {
        ensureEnterpriseUser(enterpriseId);
        return enterpriseInfoService.selectEnterpriseInfoByEnterpriseId(enterpriseId);
    }

    @Override
    public int saveMyProfile(Long enterpriseId, EnterpriseInfo profile)
    {
        ensureEnterpriseUser(enterpriseId);
        if (profile == null)
        {
            throw new ServiceException("企业资料不能为空");
        }
        profile.setEnterpriseId(enterpriseId);
        EnterpriseInfo current = enterpriseInfoService.selectEnterpriseInfoByEnterpriseId(enterpriseId);
        if (current == null)
        {
            profile.setAuditStatus(AUDIT_PENDING);
            profile.setAuditReason(null);
            profile.setAuditBy(null);
            profile.setAuditTime(null);
            profile.setDelFlag("0");
            return enterpriseInfoService.insertEnterpriseInfo(profile);
        }
        profile.setAuditStatus(current.getAuditStatus());
        profile.setAuditReason(current.getAuditReason());
        profile.setAuditBy(current.getAuditBy());
        profile.setAuditTime(current.getAuditTime());
        profile.setDelFlag(current.getDelFlag());
        return enterpriseInfoService.updateEnterpriseInfo(profile);
    }

    @Override
    public int submitMyProfileAudit(Long enterpriseId)
    {
        EnterpriseInfo current = requireEnterpriseProfile(enterpriseId);
        if (AUDIT_PASS.equals(current.getAuditStatus()))
        {
            throw new ServiceException("企业审核已通过，无需重复提交");
        }
        if (AUDIT_PENDING.equals(current.getAuditStatus()))
        {
            throw new ServiceException("企业审核正在处理中，请勿重复提交");
        }
        EnterpriseInfo update = new EnterpriseInfo();
        update.setEnterpriseId(current.getEnterpriseId());
        update.setAuditStatus(AUDIT_PENDING);
        update.setAuditReason(null);
        update.setAuditBy(null);
        update.setAuditTime(null);
        return enterpriseInfoService.updateEnterpriseInfo(update);
    }

    @Override
    public List<JobPost> listMyPosts(Long enterpriseId, JobPost query)
    {
        ensureEnterpriseUser(enterpriseId);
        JobPost condition = query == null ? new JobPost() : query;
        condition.setEnterpriseId(enterpriseId);
        return jobPostService.selectJobPostList(condition);
    }

    @Override
    public JobPost getMyPost(Long enterpriseId, Long postId)
    {
        ensureEnterpriseUser(enterpriseId);
        JobPost post = jobPostService.selectJobPostByPostId(postId);
        ensurePostOwnedByEnterprise(enterpriseId, post);
        return post;
    }

    @Override
    public int createMyPost(Long enterpriseId, JobPost post)
    {
        ensureEnterpriseApproved(enterpriseId);
        if (post == null)
        {
            throw new ServiceException("岗位信息不能为空");
        }
        post.setEnterpriseId(enterpriseId);
        if (post.getPostStatus() == null)
        {
            post.setPostStatus(POST_STATUS_DRAFT);
        }
        post.setDelFlag("0");
        return jobPostService.insertJobPost(post);
    }

    @Override
    public int updateMyPost(Long enterpriseId, JobPost post)
    {
        ensureEnterpriseApproved(enterpriseId);
        if (post == null || post.getPostId() == null)
        {
            throw new ServiceException("岗位ID不能为空");
        }
        JobPost current = getMyPost(enterpriseId, post.getPostId());
        post.setEnterpriseId(enterpriseId);
        // Keep current status in generic edit path; status should be changed by up/down actions.
        post.setPostStatus(current.getPostStatus());
        post.setDelFlag(current.getDelFlag());
        return jobPostService.updateJobPost(post);
    }

    @Override
    public int upMyPost(Long enterpriseId, Long postId)
    {
        ensureEnterpriseApproved(enterpriseId);
        JobPost current = getMyPost(enterpriseId, postId);
        if (POST_STATUS_ONLINE.equals(current.getPostStatus()))
        {
            return 1;
        }
        JobPost update = new JobPost();
        update.setPostId(postId);
        update.setEnterpriseId(enterpriseId);
        update.setPostStatus(POST_STATUS_ONLINE);
        return jobPostService.updateJobPost(update);
    }

    @Override
    public int downMyPost(Long enterpriseId, Long postId)
    {
        ensureEnterpriseApproved(enterpriseId);
        JobPost current = getMyPost(enterpriseId, postId);
        if (POST_STATUS_OFFLINE.equals(current.getPostStatus()))
        {
            return 1;
        }
        JobPost update = new JobPost();
        update.setPostId(postId);
        update.setEnterpriseId(enterpriseId);
        update.setPostStatus(POST_STATUS_OFFLINE);
        return jobPostService.updateJobPost(update);
    }

    @Override
    public int deleteMyPost(Long enterpriseId, Long postId)
    {
        ensureEnterpriseApproved(enterpriseId);
        JobPost current = getMyPost(enterpriseId, postId);
        JobPost update = new JobPost();
        update.setPostId(postId);
        update.setEnterpriseId(enterpriseId);
        update.setDelFlag(DEL_FLAG_DELETED);
        return jobPostService.updateJobPost(update);
    }

    @Override
    public List<JobApplication> listMyApplications(Long enterpriseId, JobApplication query)
    {
        ensureEnterpriseApproved(enterpriseId);
        String applicationStatus = query == null ? null : query.getApplicationStatus();
        return jobApplicationService.selectApplicationListByEnterpriseId(enterpriseId, applicationStatus);
    }

    @Override
    public JobApplication getMyApplication(Long enterpriseId, Long applicationId)
    {
        ensureEnterpriseApproved(enterpriseId);
        JobApplication application = jobApplicationService.selectJobApplicationByApplicationId(applicationId);
        if (application == null || !isApplicationOwnedByEnterprise(enterpriseId, application))
        {
            throw new ServiceException("投递记录不存在或无权限访问");
        }
        return application;
    }

    @Override
    public int hireMyApplication(Long enterpriseId, Long applicationId, String enterpriseRemark)
    {
        getMyApplication(enterpriseId, applicationId);
        return jobApplicationService.hireJobApplication(applicationId, enterpriseRemark);
    }

    @Override
    public int completeMyApplication(Long enterpriseId, Long applicationId, String enterpriseRemark)
    {
        getMyApplication(enterpriseId, applicationId);
        return jobApplicationService.completeJobApplication(applicationId, enterpriseRemark);
    }

    @Override
    public int rejectMyApplication(Long enterpriseId, Long applicationId, String enterpriseRemark)
    {
        getMyApplication(enterpriseId, applicationId);
        return jobApplicationService.rejectJobApplication(applicationId, enterpriseRemark);
    }

    @Override
    public List<JobEvaluation> listMyEvaluations(Long enterpriseId, JobEvaluation query)
    {
        ensureEnterpriseApproved(enterpriseId);
        JobEvaluation condition = query == null ? new JobEvaluation() : query;
        condition.setEvaluationType(EVALUATION_TYPE_ENTERPRISE_TO_STUDENT);
        condition.setEnterpriseId(enterpriseId);
        return jobEvaluationService.selectJobEvaluationList(condition);
    }

    @Override
    public int evaluateStudent(Long enterpriseId, Long applicationId, Integer score, String content)
    {
        JobApplication application = getMyApplication(enterpriseId, applicationId);
        if (!APPLICATION_STATUS_COMPLETED.equals(application.getApplicationStatus()))
        {
            throw new ServiceException("仅企业标记完成后可评价学生");
        }

        JobEvaluation evaluation = new JobEvaluation();
        evaluation.setApplicationId(applicationId);
        evaluation.setPostId(application.getPostId());
        evaluation.setStudentId(application.getStudentId());
        evaluation.setEnterpriseId(enterpriseId);
        evaluation.setEvaluationType(EVALUATION_TYPE_ENTERPRISE_TO_STUDENT);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setEvaluationTime(new Date());
        return jobEvaluationService.insertJobEvaluation(evaluation);
    }

    @Override
    public List<EnterpriseInfo> listEnterpriseForAudit(EnterpriseInfo query)
    {
        EnterpriseInfo condition = query == null ? new EnterpriseInfo() : query;
        return enterpriseInfoService.selectEnterpriseInfoList(condition);
    }

    @Override
    public EnterpriseInfo getEnterpriseForAudit(Long enterpriseId)
    {
        EnterpriseInfo info = enterpriseInfoService.selectEnterpriseInfoByEnterpriseId(enterpriseId);
        if (info == null || DEL_FLAG_DELETED.equals(info.getDelFlag()))
        {
            throw new ServiceException("企业资料不存在");
        }
        return info;
    }

    @Override
    public int passEnterpriseAudit(Long auditorId, Long enterpriseId)
    {
        EnterpriseInfo current = getEnterpriseForAudit(enterpriseId);
        EnterpriseInfo update = new EnterpriseInfo();
        update.setEnterpriseId(current.getEnterpriseId());
        update.setAuditStatus(AUDIT_PASS);
        update.setAuditReason(null);
        update.setAuditBy(auditorId);
        update.setAuditTime(DateUtils.getNowDate());
        return enterpriseInfoService.updateEnterpriseInfo(update);
    }

    @Override
    public int rejectEnterpriseAudit(Long auditorId, Long enterpriseId, String reason)
    {
        EnterpriseInfo current = getEnterpriseForAudit(enterpriseId);
        EnterpriseInfo update = new EnterpriseInfo();
        update.setEnterpriseId(current.getEnterpriseId());
        update.setAuditStatus(AUDIT_REJECT);
        update.setAuditReason(reason);
        update.setAuditBy(auditorId);
        update.setAuditTime(DateUtils.getNowDate());
        return enterpriseInfoService.updateEnterpriseInfo(update);
    }

    private void ensureEnterpriseUser(Long enterpriseId)
    {
        if (enterpriseId == null)
        {
            throw new ServiceException("企业身份无效");
        }
    }

    private EnterpriseInfo requireEnterpriseProfile(Long enterpriseId)
    {
        ensureEnterpriseUser(enterpriseId);
        EnterpriseInfo profile = enterpriseInfoService.selectEnterpriseInfoByEnterpriseId(enterpriseId);
        if (profile == null || DEL_FLAG_DELETED.equals(profile.getDelFlag()))
        {
            throw new ServiceException("企业资料不存在");
        }
        return profile;
    }

    private void ensureEnterpriseApproved(Long enterpriseId)
    {
        EnterpriseInfo profile = requireEnterpriseProfile(enterpriseId);
        if (!AUDIT_PASS.equals(profile.getAuditStatus()))
        {
            throw new ServiceException("企业审核未通过，暂不可执行该操作");
        }
    }

    private void ensurePostOwnedByEnterprise(Long enterpriseId, JobPost post)
    {
        if (post == null || !enterpriseId.equals(post.getEnterpriseId()))
        {
            throw new ServiceException("岗位不存在或无权限访问");
        }
        if (DEL_FLAG_DELETED.equals(post.getDelFlag()))
        {
            throw new ServiceException("岗位已删除");
        }
    }

    private boolean isApplicationOwnedByEnterprise(Long enterpriseId, JobApplication application)
    {
        if (application == null || application.getPostId() == null)
        {
            return false;
        }
        JobPost post = jobPostService.selectJobPostByPostId(application.getPostId());
        return post != null && enterpriseId.equals(post.getEnterpriseId()) && !DEL_FLAG_DELETED.equals(post.getDelFlag());
    }
}


