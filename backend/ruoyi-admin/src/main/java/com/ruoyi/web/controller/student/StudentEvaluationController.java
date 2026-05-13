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
import com.ruoyi.biz.recruitment.domain.JobEvaluation;

/**
 * Student-side evaluation endpoints.
 */
@RestController
@RequestMapping("/student/evaluation")
public class StudentEvaluationController extends BaseController
{
    @Autowired
    private IStudentFlowService studentFlowService;

    @PreAuthorize("@ss.hasPermi('student:evaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobEvaluation query)
    {
        startPage();
        Long studentId = SecurityUtils.getUserId();
        List<JobEvaluation> list = studentFlowService.listMyEvaluations(studentId, query);
        return getDataTable(list);
    }

    /**
     * 查看某企业收到的评价（学生对企业的评价）
     */
    @GetMapping("/enterprise/{enterpriseId}")
    public TableDataInfo enterpriseEvaluations(@PathVariable Long enterpriseId, JobEvaluation query)
    {
        startPage();
        List<JobEvaluation> list = studentFlowService.listEnterpriseEvaluations(enterpriseId, query);
        return getDataTable(list);
    }

    /**
     * 查询收到的评价（企业对当前学生的评价）
     */
    @PreAuthorize("@ss.hasPermi('student:evaluation:list')")
    @GetMapping("/about-me")
    public TableDataInfo aboutMe(JobEvaluation query)
    {
        startPage();
        Long studentId = SecurityUtils.getUserId();
        List<JobEvaluation> list = studentFlowService.listEvaluationsAboutMe(studentId, query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('student:evaluation:add')")
    @PostMapping
    public AjaxResult add(@RequestBody StudentEvaluationRequest request)
    {
        Long studentId = SecurityUtils.getUserId();
        long evaluationId = studentFlowService.evaluateCompletedApplication(studentId,
                request.getApplicationId(), request.getScore(), request.getContent());
        return AjaxResult.success("评价成功", evaluationId);
    }

    public static class StudentEvaluationRequest
    {
        private Long applicationId;

        private Integer score;

        private String content;

        public Long getApplicationId()
        {
            return applicationId;
        }

        public void setApplicationId(Long applicationId)
        {
            this.applicationId = applicationId;
        }

        public Integer getScore()
        {
            return score;
        }

        public void setScore(Integer score)
        {
            this.score = score;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }
    }
}


