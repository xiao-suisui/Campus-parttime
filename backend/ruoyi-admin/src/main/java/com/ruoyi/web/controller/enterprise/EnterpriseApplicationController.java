package com.ruoyi.web.controller.enterprise;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.ruoyi.biz.recruitment.domain.JobApplication;

/**
 * 企业投递管理
 */
@RestController
@RequestMapping("/enterprise/application")
public class EnterpriseApplicationController extends BaseController
{
    @Autowired
    private IEnterpriseFlowService enterpriseFlowService;

    /**
     * 查询投递列表
     */
    @PreAuthorize("@ss.hasPermi('enterprise:application:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobApplication query)
    {
        startPage();
        Long enterpriseId = SecurityUtils.getUserId();
        List<JobApplication> list = enterpriseFlowService.listMyApplications(enterpriseId, query);
        return getDataTable(list);
    }

    /**
     * 获取投递详细信息
     */
    @PreAuthorize("@ss.hasPermi('enterprise:application:query')")
    @GetMapping("/{applicationId}")
    public AjaxResult getInfo(@PathVariable Long applicationId)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return success(enterpriseFlowService.getMyApplication(enterpriseId, applicationId));
    }

    /**
     * 录用
     */
    @PreAuthorize("@ss.hasPermi('enterprise:application:hire')")
    @Log(title = "投递处理", businessType = BusinessType.UPDATE)
    @PutMapping("/{applicationId}/hire")
    public AjaxResult hire(@PathVariable Long applicationId, @RequestBody(required = false) EnterpriseApplicationActionRequest request)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        String remark = request == null ? null : request.getEnterpriseRemark();
        return toAjax(enterpriseFlowService.hireMyApplication(enterpriseId, applicationId, remark));
    }

    /**
     * 标记完成
     */
    @PreAuthorize("@ss.hasPermi('enterprise:application:complete')")
    @Log(title = "投递处理", businessType = BusinessType.UPDATE)
    @PutMapping("/{applicationId}/complete")
    public AjaxResult complete(@PathVariable Long applicationId, @RequestBody(required = false) EnterpriseApplicationActionRequest request)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        String remark = request == null ? null : request.getEnterpriseRemark();
        return toAjax(enterpriseFlowService.completeMyApplication(enterpriseId, applicationId, remark));
    }

    /**
     * 拒绝投递
     */
    @PreAuthorize("@ss.hasPermi('enterprise:application:reject')")
    @Log(title = "投递处理", businessType = BusinessType.UPDATE)
    @PutMapping("/{applicationId}/reject")
    public AjaxResult reject(@PathVariable Long applicationId, @RequestBody(required = false) EnterpriseApplicationActionRequest request)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        String remark = request == null ? null : request.getEnterpriseRemark();
        return toAjax(enterpriseFlowService.rejectMyApplication(enterpriseId, applicationId, remark));
    }

    public static class EnterpriseApplicationActionRequest
    {
        private String enterpriseRemark;

        public String getEnterpriseRemark()
        {
            return enterpriseRemark;
        }

        public void setEnterpriseRemark(String enterpriseRemark)
        {
            this.enterpriseRemark = enterpriseRemark;
        }
    }
}
