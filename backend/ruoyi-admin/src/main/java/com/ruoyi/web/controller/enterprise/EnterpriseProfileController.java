package com.ruoyi.web.controller.enterprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.biz.enterprise.service.IEnterpriseFlowService;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;

/**
 * 企业资料管理
 */
@RestController
@RequestMapping("/enterprise/profile")
public class EnterpriseProfileController extends BaseController
{
    @Autowired
    private IEnterpriseFlowService enterpriseFlowService;

    /**
     * 查询企业资料
     */
    @PreAuthorize("@ss.hasPermi('enterprise:profile:query')")
    @GetMapping
    public AjaxResult getProfile()
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return success(enterpriseFlowService.getMyProfile(enterpriseId));
    }

    /**
     * 新增企业资料
     */
    @PreAuthorize("@ss.hasPermi('enterprise:profile:add')")
    @Log(title = "企业资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody EnterpriseInfo profile)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.saveMyProfile(enterpriseId, profile));
    }

    /**
     * 修改企业资料
     */
    @PreAuthorize("@ss.hasPermi('enterprise:profile:edit')")
    @Log(title = "企业资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody EnterpriseInfo profile)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.saveMyProfile(enterpriseId, profile));
    }

    /**
     * 提交企业审核
     */
    @PreAuthorize("@ss.hasPermi('enterprise:profile:submit')")
    @Log(title = "企业审核", businessType = BusinessType.INSERT)
    @PostMapping("/submit-audit")
    public AjaxResult submitAudit()
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.submitMyProfileAudit(enterpriseId));
    }
}
