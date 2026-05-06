package com.ruoyi.web.controller.student;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.biz.student.service.IStudentFlowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.biz.recruitment.domain.JobApplication;

/**
 * Student-side application endpoints.
 */
@RestController
@RequestMapping("/student/application")
public class StudentApplicationController extends BaseController
{
    @Autowired
    private IStudentFlowService studentFlowService;

    @PreAuthorize("@ss.hasPermi('student:application:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobApplication query)
    {
        startPage();
        Long studentId = SecurityUtils.getUserId();
        List<JobApplication> list = studentFlowService.listMyApplications(studentId, query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('student:application:query')")
    @GetMapping("/{applicationId}")
    public AjaxResult getInfo(@PathVariable Long applicationId)
    {
        Long studentId = SecurityUtils.getUserId();
        return success(studentFlowService.getMyApplicationDetail(studentId, applicationId));
    }

    @PreAuthorize("@ss.hasPermi('student:application:add')")
    @PostMapping
    public AjaxResult add(@RequestBody StudentApplicationRequest request)
    {
        Long studentId = SecurityUtils.getUserId();
        long applicationId = studentFlowService.applyPost(studentId, request.getPostId(), request.getResumeId());
        return AjaxResult.success("投递成功", applicationId);
    }

    public static class StudentApplicationRequest
    {
        private Long postId;

        private Long resumeId;

        public Long getPostId()
        {
            return postId;
        }

        public void setPostId(Long postId)
        {
            this.postId = postId;
        }

        public Long getResumeId()
        {
            return resumeId;
        }

        public void setResumeId(Long resumeId)
        {
            this.resumeId = resumeId;
        }
    }
}


