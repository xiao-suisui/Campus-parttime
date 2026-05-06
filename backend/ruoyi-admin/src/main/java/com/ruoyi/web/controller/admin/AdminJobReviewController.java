package com.ruoyi.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.biz.recruitment.service.IJobPostService;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;
import com.ruoyi.biz.enterprise.service.IEnterpriseInfoService;

/**
 * 管理员岗位监管
 */
@RestController
@RequestMapping("/admin/job-review")
public class AdminJobReviewController extends BaseController
{
    private static final String POST_STATUS_OFFLINE = "2";

    @Autowired
    private IJobPostService jobPostService;

    @Autowired
    private IEnterpriseInfoService enterpriseInfoService;

    /**
     * 查询全平台岗位列表
     */
    @PreAuthorize("@ss.hasPermi('admin:job-review:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobPost query)
    {
        startPage();
        List<JobPost> list = jobPostService.selectJobPostList(query);
        List<JobReviewVO> voList = enrichWithEnterpriseName(list);
        return getDataTable(voList);
    }

    /**
     * 查询岗位详情
     */
    @PreAuthorize("@ss.hasPermi('admin:job-review:query')")
    @GetMapping("/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId)
    {
        JobPost post = jobPostService.selectJobPostByPostId(postId);
        if (post == null)
        {
            throw new ServiceException("岗位不存在");
        }
        JobReviewVO vo = new JobReviewVO();
        BeanUtils.copyProperties(post, vo);
        EnterpriseInfo enterprise = enterpriseInfoService.selectEnterpriseInfoByEnterpriseId(post.getEnterpriseId());
        if (enterprise != null)
        {
            vo.setEnterpriseName(enterprise.getEnterpriseName());
        }
        return success(vo);
    }

    /**
     * 强制下架岗位
     */
    @PreAuthorize("@ss.hasPermi('admin:job-review:force-down')")
    @PutMapping("/{postId}/force-down")
    public AjaxResult forceDown(@PathVariable Long postId)
    {
        JobPost post = jobPostService.selectJobPostByPostId(postId);
        if (post == null)
        {
            throw new ServiceException("岗位不存在");
        }
        if (POST_STATUS_OFFLINE.equals(post.getPostStatus()))
        {
            return toAjax(true);
        }
        JobPost update = new JobPost();
        update.setPostId(postId);
        update.setPostStatus(POST_STATUS_OFFLINE);
        return toAjax(jobPostService.updateJobPost(update));
    }

    private List<JobReviewVO> enrichWithEnterpriseName(List<JobPost> list)
    {
        Map<Long, String> nameMap = new HashMap<>();
        for (JobPost post : list)
        {
            if (post.getEnterpriseId() != null && !nameMap.containsKey(post.getEnterpriseId()))
            {
                EnterpriseInfo enterprise = enterpriseInfoService.selectEnterpriseInfoByEnterpriseId(post.getEnterpriseId());
                nameMap.put(post.getEnterpriseId(), enterprise != null ? enterprise.getEnterpriseName() : "");
            }
        }
        return list.stream().map(post -> {
            JobReviewVO vo = new JobReviewVO();
            BeanUtils.copyProperties(post, vo);
            vo.setEnterpriseName(nameMap.getOrDefault(post.getEnterpriseId(), ""));
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 岗位审核视图对象（包含企业名称）
     */
    public static class JobReviewVO extends JobPost
    {
        private String enterpriseName;

        public String getEnterpriseName()
        {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName)
        {
            this.enterpriseName = enterpriseName;
        }
    }
}
