package com.ruoyi.web.controller.enterprise;

import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.biz.enterprise.service.IEnterpriseFlowService;
import com.ruoyi.biz.recruitment.domain.JobEvaluation;

/**
 * 企业评价管理
 */
@RestController
@RequestMapping("/enterprise/evaluation")
public class EnterpriseEvaluationController extends BaseController
{
    @Autowired
    private IEnterpriseFlowService enterpriseFlowService;

    /**
     * 查询评价列表（企业发出的）
     */
    @PreAuthorize("@ss.hasPermi('enterprise:evaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobEvaluation query)
    {
        startPage();
        Long enterpriseId = SecurityUtils.getUserId();
        List<JobEvaluation> list = enterpriseFlowService.listMyEvaluations(enterpriseId, query);
        return getDataTable(list);
    }

    /**
     * 查询收到的评价（学生对本企业的评价）
     */
    @PreAuthorize("@ss.hasPermi('enterprise:evaluation:list')")
    @GetMapping("/about-me")
    public TableDataInfo aboutMe(JobEvaluation query)
    {
        startPage();
        Long enterpriseId = SecurityUtils.getUserId();
        List<JobEvaluation> list = enterpriseFlowService.listEvaluationsAboutMe(enterpriseId, query);
        return getDataTable(list);
    }

    /**
     * 新增评价
     */
    @PreAuthorize("@ss.hasPermi('enterprise:evaluation:add')")
    @Log(title = "企业评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody EnterpriseEvaluationRequest request)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        int rows = enterpriseFlowService.evaluateStudent(enterpriseId,
                request.getApplicationId(), request.getScore(), request.getContent());
        return toAjax(rows);
    }

    public static class EnterpriseEvaluationRequest
    {
        @NotNull(message = "投递ID不能为空")
        private Long applicationId;

        @NotNull(message = "评分不能为空")
        @Min(value = 1, message = "评分最小为1")
        @Max(value = 5, message = "评分最大为5")
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
