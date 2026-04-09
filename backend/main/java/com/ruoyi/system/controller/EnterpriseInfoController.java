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
import com.ruoyi.system.domain.EnterpriseInfo;
import com.ruoyi.system.service.IEnterpriseInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业信息Controller
 * 
 * @author wly
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/system/info")
public class EnterpriseInfoController extends BaseController
{
    @Autowired
    private IEnterpriseInfoService enterpriseInfoService;

    /**
     * 查询企业信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(EnterpriseInfo enterpriseInfo)
    {
        startPage();
        List<EnterpriseInfo> list = enterpriseInfoService.selectEnterpriseInfoList(enterpriseInfo);
        return getDataTable(list);
    }

    /**
     * 导出企业信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "企业信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EnterpriseInfo enterpriseInfo)
    {
        List<EnterpriseInfo> list = enterpriseInfoService.selectEnterpriseInfoList(enterpriseInfo);
        ExcelUtil<EnterpriseInfo> util = new ExcelUtil<EnterpriseInfo>(EnterpriseInfo.class);
        util.exportExcel(response, list, "企业信息数据");
    }

    /**
     * 获取企业信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{enterpriseId}")
    public AjaxResult getInfo(@PathVariable("enterpriseId") Long enterpriseId)
    {
        return success(enterpriseInfoService.selectEnterpriseInfoByEnterpriseId(enterpriseId));
    }

    /**
     * 新增企业信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "企业信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return toAjax(enterpriseInfoService.insertEnterpriseInfo(enterpriseInfo));
    }

    /**
     * 修改企业信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "企业信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return toAjax(enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo));
    }

    /**
     * 删除企业信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "企业信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{enterpriseIds}")
    public AjaxResult remove(@PathVariable Long[] enterpriseIds)
    {
        return toAjax(enterpriseInfoService.deleteEnterpriseInfoByEnterpriseIds(enterpriseIds));
    }
}
