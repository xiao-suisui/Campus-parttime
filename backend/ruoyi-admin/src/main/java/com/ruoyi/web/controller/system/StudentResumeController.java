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
import com.ruoyi.system.domain.StudentResume;
import com.ruoyi.system.service.IStudentResumeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 瀛︾敓绠€鍘咰ontroller
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/system/resume")
public class StudentResumeController extends BaseController
{
    @Autowired
    private IStudentResumeService StudentResumeService;

    /**
     * 鏌ヨ瀛︾敓绠€鍘嗗垪琛?
     */
    @PreAuthorize("@ss.hasPermi('system:resume:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentResume StudentResume)
    {
        startPage();
        List<StudentResume> list = StudentResumeService.selectStudentResumeList(StudentResume);
        return getDataTable(list);
    }

    /**
     * 瀵煎嚭瀛︾敓绠€鍘嗗垪琛?
     */
    @PreAuthorize("@ss.hasPermi('system:resume:export')")
    @Log(title = "瀛︾敓绠€鍘?, businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentResume StudentResume)
    {
        List<StudentResume> list = StudentResumeService.selectStudentResumeList(StudentResume);
        ExcelUtil<StudentResume> util = new ExcelUtil<StudentResume>(StudentResume.class);
        util.exportExcel(response, list, "瀛︾敓绠€鍘嗘暟鎹?);
    }

    /**
     * 鑾峰彇瀛︾敓绠€鍘嗚缁嗕俊鎭?
     */
    @PreAuthorize("@ss.hasPermi('system:resume:query')")
    @GetMapping(value = "/{resumeId}")
    public AjaxResult getInfo(@PathVariable("resumeId") Long resumeId)
    {
        return success(StudentResumeService.selectStudentResumeByResumeId(resumeId));
    }

    /**
     * 鏂板瀛︾敓绠€鍘?
     */
    @PreAuthorize("@ss.hasPermi('system:resume:add')")
    @Log(title = "瀛︾敓绠€鍘?, businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentResume StudentResume)
    {
        return toAjax(StudentResumeService.insertStudentResume(StudentResume));
    }

    /**
     * 淇敼瀛︾敓绠€鍘?
     */
    @PreAuthorize("@ss.hasPermi('system:resume:edit')")
    @Log(title = "瀛︾敓绠€鍘?, businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentResume StudentResume)
    {
        return toAjax(StudentResumeService.updateStudentResume(StudentResume));
    }

    /**
     * 鍒犻櫎瀛︾敓绠€鍘?
     */
    @PreAuthorize("@ss.hasPermi('system:resume:remove')")
    @Log(title = "瀛︾敓绠€鍘?, businessType = BusinessType.DELETE)
	@DeleteMapping("/{resumeIds}")
    public AjaxResult remove(@PathVariable Long[] resumeIds)
    {
        return toAjax(StudentResumeService.deleteStudentResumeByResumeIds(resumeIds));
    }
}

