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
import com.ruoyi.biz.student.domain.StudentInfo;

/**
 * 学生个人信息管理
 */
@RestController
@RequestMapping("/student/info")
public class StudentInfoController extends BaseController
{
    @Autowired
    private IStudentFlowService studentFlowService;

    /**
     * 查询当前学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:query')")
    @GetMapping
    public AjaxResult getInfo()
    {
        Long studentId = SecurityUtils.getUserId();
        return success(studentFlowService.getMyInfo(studentId));
    }

    /**
     * 新增学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody StudentInfo info)
    {
        Long studentId = SecurityUtils.getUserId();
        return toAjax(studentFlowService.saveMyInfo(studentId, info));
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StudentInfo info)
    {
        Long studentId = SecurityUtils.getUserId();
        return toAjax(studentFlowService.saveMyInfo(studentId, info));
    }
}
