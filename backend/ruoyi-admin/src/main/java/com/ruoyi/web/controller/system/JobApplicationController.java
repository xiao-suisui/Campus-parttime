package com.ruoyi.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.JobApplication;
import com.ruoyi.system.service.IJobApplicationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 鎶曢€掕褰旵ontroller
 * 
 * @author wly
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/system/application")
public class JobApplicationController extends BaseController
{
    @Autowired
    private IJobApplicationService jobApplicationService;

    /**
     * 鏌ヨ鎶曢€掕褰曞垪琛?
     */
    @PreAuthorize("@ss.hasPermi('system:application:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobApplication jobApplication)
    {
        startPage();
        List<JobApplication> list = jobApplicationService.selectJobApplicationList(jobApplication);
        return getDataTable(list);
    }

    /**
     * 瀵煎嚭鎶曢€掕褰曞垪琛?
     */
    @PreAuthorize("@ss.hasPermi('system:application:export')")
    @Log(title = "鎶曢€掕褰?, businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JobApplication jobApplication)
    {
        List<JobApplication> list = jobApplicationService.selectJobApplicationList(jobApplication);
        ExcelUtil<JobApplication> util = new ExcelUtil<JobApplication>(JobApplication.class);
        util.exportExcel(response, list, "鎶曢€掕褰曟暟鎹?);
    }

    /**
     * 鑾峰彇鎶曢€掕褰曡缁嗕俊鎭?
     */
    @PreAuthorize("@ss.hasPermi('system:application:query')")
    @GetMapping(value = "/{applicationId}")
    public AjaxResult getInfo(@PathVariable("applicationId") Long applicationId)
    {
        return success(jobApplicationService.selectJobApplicationByApplicationId(applicationId));
    }

    /**
     * 鏂板鎶曢€掕褰?
     */
    @PreAuthorize("@ss.hasPermi('system:application:add')")
    @Log(title = "鎶曢€掕褰?, businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JobApplication jobApplication)
    {
        return toAjax(jobApplicationService.insertJobApplication(jobApplication));
    }

    /**
     * 淇敼鎶曢€掕褰?
     */
    @PreAuthorize("@ss.hasPermi('system:application:edit')")
    @Log(title = "鎶曢€掕褰?, businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JobApplication jobApplication)
    {
        return toAjax(jobApplicationService.updateJobApplication(jobApplication));
    }

    /**
     * 鍒犻櫎鎶曢€掕褰?
     */
    @PreAuthorize("@ss.hasPermi('system:application:remove')")
    @Log(title = "鎶曢€掕褰?, businessType = BusinessType.DELETE)
	@DeleteMapping("/{applicationIds}")
    public AjaxResult remove(@PathVariable Long[] applicationIds)
    {
        return toAjax(jobApplicationService.deleteJobApplicationByApplicationIds(applicationIds));
    }

    /**
     * 浼佷笟褰曠敤鎶曢€掕褰曪紙0->1锛?
     */
    @PreAuthorize("@ss.hasPermi('system:application:edit')")
    @Log(title = "鎶曢€掕褰?, businessType = BusinessType.UPDATE)
    @PutMapping("/{applicationId}/hire")
    public AjaxResult hire(@PathVariable Long applicationId, @RequestBody(required = false) JobApplication jobApplication)
    {
        String enterpriseRemark = jobApplication == null ? null : jobApplication.getEnterpriseRemark();
        return toAjax(jobApplicationService.hireJobApplication(applicationId, enterpriseRemark));
    }

    /**
     * 浼佷笟鏍囪鎶曢€掑畬鎴愶紙1->2锛?
     */
    @PreAuthorize("@ss.hasPermi('system:application:edit')")
    @Log(title = "鎶曢€掕褰?, businessType = BusinessType.UPDATE)
    @PutMapping("/{applicationId}/complete")
    public AjaxResult complete(@PathVariable Long applicationId, @RequestBody(required = false) JobApplication jobApplication)
    {
        String enterpriseRemark = jobApplication == null ? null : jobApplication.getEnterpriseRemark();
        return toAjax(jobApplicationService.completeJobApplication(applicationId, enterpriseRemark));
    }
}

