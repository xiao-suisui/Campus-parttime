package com.ruoyi.web.controller.student;

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
import com.ruoyi.biz.student.service.IStudentFlowService;
import com.ruoyi.biz.student.domain.StudentResume;

/**
 * 学生简历管理
 */
@RestController
@RequestMapping("/student/resume")
public class StudentResumeController extends BaseController
{
    @Autowired
    private IStudentFlowService studentFlowService;

    /**
     * 查询当前学生简历
     */
    @PreAuthorize("@ss.hasPermi('student:resume:query')")
    @GetMapping
    public AjaxResult getResume()
    {
        Long studentId = SecurityUtils.getUserId();
        return success(studentFlowService.getMyResume(studentId));
    }

    /**
     * 新增简历
     */
    @PreAuthorize("@ss.hasPermi('student:resume:add')")
    @Log(title = "学生简历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody StudentResume resume)
    {
        Long studentId = SecurityUtils.getUserId();
        return toAjax(studentFlowService.saveMyResume(studentId, resume));
    }

    /**
     * 修改简历
     */
    @PreAuthorize("@ss.hasPermi('student:resume:edit')")
    @Log(title = "学生简历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StudentResume resume)
    {
        Long studentId = SecurityUtils.getUserId();
        return toAjax(studentFlowService.saveMyResume(studentId, resume));
    }
}
