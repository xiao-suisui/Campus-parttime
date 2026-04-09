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
import com.ruoyi.system.domain.JobEvaluation;
import com.ruoyi.system.service.IJobEvaluationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 双向互评Controller
 * 
 * @author wly
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/system/evaluation")
public class JobEvaluationController extends BaseController
{
    @Autowired
    private IJobEvaluationService jobEvaluationService;

    /**
     * 查询双向互评列表
     */
    @PreAuthorize("@ss.hasPermi('system:evaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobEvaluation jobEvaluation)
    {
        startPage();
        List<JobEvaluation> list = jobEvaluationService.selectJobEvaluationList(jobEvaluation);
        return getDataTable(list);
    }

    /**
     * 导出双向互评列表
     */
    @PreAuthorize("@ss.hasPermi('system:evaluation:export')")
    @Log(title = "双向互评", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JobEvaluation jobEvaluation)
    {
        List<JobEvaluation> list = jobEvaluationService.selectJobEvaluationList(jobEvaluation);
        ExcelUtil<JobEvaluation> util = new ExcelUtil<JobEvaluation>(JobEvaluation.class);
        util.exportExcel(response, list, "双向互评数据");
    }

    /**
     * 获取双向互评详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:evaluation:query')")
    @GetMapping(value = "/{evaluationId}")
    public AjaxResult getInfo(@PathVariable("evaluationId") Long evaluationId)
    {
        return success(jobEvaluationService.selectJobEvaluationByEvaluationId(evaluationId));
    }

    /**
     * 新增双向互评
     */
    @PreAuthorize("@ss.hasPermi('system:evaluation:add')")
    @Log(title = "双向互评", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JobEvaluation jobEvaluation)
    {
        return toAjax(jobEvaluationService.insertJobEvaluation(jobEvaluation));
    }

    /**
     * 修改双向互评
     */
    @PreAuthorize("@ss.hasPermi('system:evaluation:edit')")
    @Log(title = "双向互评", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JobEvaluation jobEvaluation)
    {
        return toAjax(jobEvaluationService.updateJobEvaluation(jobEvaluation));
    }

    /**
     * 删除双向互评
     */
    @PreAuthorize("@ss.hasPermi('system:evaluation:remove')")
    @Log(title = "双向互评", businessType = BusinessType.DELETE)
	@DeleteMapping("/{evaluationIds}")
    public AjaxResult remove(@PathVariable Long[] evaluationIds)
    {
        return toAjax(jobEvaluationService.deleteJobEvaluationByEvaluationIds(evaluationIds));
    }
}
