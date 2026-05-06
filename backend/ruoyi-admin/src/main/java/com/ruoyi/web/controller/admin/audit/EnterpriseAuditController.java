package com.ruoyi.web.controller.admin.audit;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.biz.enterprise.service.IEnterpriseFlowService;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;

/**
 * Admin endpoints for enterprise audit only.
 */
@RestController
@RequestMapping("/admin/audit/enterprise")
public class EnterpriseAuditController extends BaseController
{
    @Autowired
    private IEnterpriseFlowService enterpriseFlowService;

    @PreAuthorize("@ss.hasPermi('admin:enterprise:audit:list')")
    @GetMapping("/list")
    public TableDataInfo list(EnterpriseInfo query)
    {
        startPage();
        List<EnterpriseInfo> list = enterpriseFlowService.listEnterpriseForAudit(query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('admin:enterprise:audit:query')")
    @GetMapping("/{enterpriseId}")
    public AjaxResult getInfo(@PathVariable Long enterpriseId)
    {
        return success(enterpriseFlowService.getEnterpriseForAudit(enterpriseId));
    }

    @PreAuthorize("@ss.hasPermi('admin:enterprise:audit:pass')")
    @PutMapping("/{enterpriseId}/pass")
    public AjaxResult pass(@PathVariable Long enterpriseId)
    {
        Long auditorId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.passEnterpriseAudit(auditorId, enterpriseId));
    }

    @PreAuthorize("@ss.hasPermi('admin:enterprise:audit:reject')")
    @PutMapping("/{enterpriseId}/reject")
    public AjaxResult reject(@PathVariable Long enterpriseId, @RequestBody(required = false) RejectRequest request)
    {
        Long auditorId = SecurityUtils.getUserId();
        String reason = request == null ? null : request.getReason();
        return toAjax(enterpriseFlowService.rejectEnterpriseAudit(auditorId, enterpriseId, reason));
    }

    public static class RejectRequest
    {
        private String reason;

        public String getReason()
        {
            return reason;
        }

        public void setReason(String reason)
        {
            this.reason = reason;
        }
    }
}


