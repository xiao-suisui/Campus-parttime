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
import com.ruoyi.system.domain.TStudentResume;
import com.ruoyi.system.service.ITStudentResumeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生简历Controller
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/system/resume")
public class TStudentResumeController extends BaseController
{
    @Autowired
    private ITStudentResumeService tStudentResumeService;

    /**
     * 查询学生简历列表
     */
    @PreAuthorize("@ss.hasPermi('system:resume:list')")
    @GetMapping("/list")
    public TableDataInfo list(TStudentResume tStudentResume)
    {
        startPage();
        List<TStudentResume> list = tStudentResumeService.selectTStudentResumeList(tStudentResume);
        return getDataTable(list);
    }

    /**
     * 导出学生简历列表
     */
    @PreAuthorize("@ss.hasPermi('system:resume:export')")
    @Log(title = "学生简历", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TStudentResume tStudentResume)
    {
        List<TStudentResume> list = tStudentResumeService.selectTStudentResumeList(tStudentResume);
        ExcelUtil<TStudentResume> util = new ExcelUtil<TStudentResume>(TStudentResume.class);
        util.exportExcel(response, list, "学生简历数据");
    }

    /**
     * 获取学生简历详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:resume:query')")
    @GetMapping(value = "/{resumeId}")
    public AjaxResult getInfo(@PathVariable("resumeId") Long resumeId)
    {
        return success(tStudentResumeService.selectTStudentResumeByResumeId(resumeId));
    }

    /**
     * 新增学生简历
     */
    @PreAuthorize("@ss.hasPermi('system:resume:add')")
    @Log(title = "学生简历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TStudentResume tStudentResume)
    {
        return toAjax(tStudentResumeService.insertTStudentResume(tStudentResume));
    }

    /**
     * 修改学生简历
     */
    @PreAuthorize("@ss.hasPermi('system:resume:edit')")
    @Log(title = "学生简历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TStudentResume tStudentResume)
    {
        return toAjax(tStudentResumeService.updateTStudentResume(tStudentResume));
    }

    /**
     * 删除学生简历
     */
    @PreAuthorize("@ss.hasPermi('system:resume:remove')")
    @Log(title = "学生简历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{resumeIds}")
    public AjaxResult remove(@PathVariable Long[] resumeIds)
    {
        return toAjax(tStudentResumeService.deleteTStudentResumeByResumeIds(resumeIds));
    }
}
