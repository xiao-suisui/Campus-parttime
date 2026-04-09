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
import com.ruoyi.system.domain.StudentInfo;
import com.ruoyi.system.service.IStudentInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 瀛︾敓淇℃伅Controller
 * 
 * @author wly
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/system/studentInfo")
public class StudentInfoController extends BaseController
{
    @Autowired
    private IStudentInfoService studentInfoService;

    /**
     * 鏌ヨ瀛︾敓淇℃伅鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('system:studentInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentInfo studentInfo)
    {
        startPage();
        List<StudentInfo> list = studentInfoService.selectStudentInfoList(studentInfo);
        return getDataTable(list);
    }

    /**
     * 瀵煎嚭瀛︾敓淇℃伅鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('system:studentInfo:export')")
    @Log(title = "瀛︾敓淇℃伅", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentInfo studentInfo)
    {
        List<StudentInfo> list = studentInfoService.selectStudentInfoList(studentInfo);
        ExcelUtil<StudentInfo> util = new ExcelUtil<StudentInfo>(StudentInfo.class);
        util.exportExcel(response, list, "瀛︾敓淇℃伅鏁版嵁");
    }

    /**
     * 鑾峰彇瀛︾敓淇℃伅璇︾粏淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:studentInfo:query')")
    @GetMapping(value = "/{studentId}")
    public AjaxResult getInfo(@PathVariable("studentId") Long studentId)
    {
        return success(studentInfoService.selectStudentInfoByStudentId(studentId));
    }

    /**
     * 鏂板瀛︾敓淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:studentInfo:add')")
    @Log(title = "瀛︾敓淇℃伅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentInfo studentInfo)
    {
        return toAjax(studentInfoService.insertStudentInfo(studentInfo));
    }

    /**
     * 淇敼瀛︾敓淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:studentInfo:edit')")
    @Log(title = "瀛︾敓淇℃伅", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentInfo studentInfo)
    {
        return toAjax(studentInfoService.updateStudentInfo(studentInfo));
    }

    /**
     * 鍒犻櫎瀛︾敓淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:studentInfo:remove')")
    @Log(title = "瀛︾敓淇℃伅", businessType = BusinessType.DELETE)
	@DeleteMapping("/{studentIds}")
    public AjaxResult remove(@PathVariable Long[] studentIds)
    {
        return toAjax(studentInfoService.deleteStudentInfoByStudentIds(studentIds));
    }
}

