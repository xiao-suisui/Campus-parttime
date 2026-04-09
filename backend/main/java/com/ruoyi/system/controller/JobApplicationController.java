package com.ruoyi.system.controller;

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
 * 投递记录Controller
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
     * 查询投递记录列表
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
     * 导出投递记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:application:export')")
    @Log(title = "投递记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JobApplication jobApplication)
    {
        List<JobApplication> list = jobApplicationService.selectJobApplicationList(jobApplication);
        ExcelUtil<JobApplication> util = new ExcelUtil<JobApplication>(JobApplication.class);
        util.exportExcel(response, list, "投递记录数据");
    }

    /**
     * 获取投递记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:application:query')")
    @GetMapping(value = "/{applicationId}")
    public AjaxResult getInfo(@PathVariable("applicationId") Long applicationId)
    {
        return success(jobApplicationService.selectJobApplicationByApplicationId(applicationId));
    }

    /**
     * 新增投递记录
     */
    @PreAuthorize("@ss.hasPermi('system:application:add')")
    @Log(title = "投递记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JobApplication jobApplication)
    {
        return toAjax(jobApplicationService.insertJobApplication(jobApplication));
    }

    /**
     * 修改投递记录
     */
    @PreAuthorize("@ss.hasPermi('system:application:edit')")
    @Log(title = "投递记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JobApplication jobApplication)
    {
        return toAjax(jobApplicationService.updateJobApplication(jobApplication));
    }

    /**
     * 删除投递记录
     */
    @PreAuthorize("@ss.hasPermi('system:application:remove')")
    @Log(title = "投递记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{applicationIds}")
    public AjaxResult remove(@PathVariable Long[] applicationIds)
    {
        return toAjax(jobApplicationService.deleteJobApplicationByApplicationIds(applicationIds));
    }

    /**
     * 企业录用投递记录（0->1）
     */
    @PreAuthorize("@ss.hasPermi('system:application:edit')")
    @Log(title = "投递记录", businessType = BusinessType.UPDATE)
    @PutMapping("/{applicationId}/hire")
    public AjaxResult hire(@PathVariable Long applicationId, @RequestBody(required = false) JobApplication jobApplication)
    {
        String enterpriseRemark = jobApplication == null ? null : jobApplication.getEnterpriseRemark();
        return toAjax(jobApplicationService.hireJobApplication(applicationId, enterpriseRemark));
    }

    /**
     * 企业标记投递完成（1->2）
     */
    @PreAuthorize("@ss.hasPermi('system:application:edit')")
    @Log(title = "投递记录", businessType = BusinessType.UPDATE)
    @PutMapping("/{applicationId}/complete")
    public AjaxResult complete(@PathVariable Long applicationId, @RequestBody(required = false) JobApplication jobApplication)
    {
        String enterpriseRemark = jobApplication == null ? null : jobApplication.getEnterpriseRemark();
        return toAjax(jobApplicationService.completeJobApplication(applicationId, enterpriseRemark));
    }
}
