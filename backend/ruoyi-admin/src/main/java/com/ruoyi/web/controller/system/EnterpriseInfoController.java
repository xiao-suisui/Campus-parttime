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
import com.ruoyi.system.domain.EnterpriseInfo;
import com.ruoyi.system.service.IEnterpriseInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 浼佷笟淇℃伅Controller
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
     * 鏌ヨ浼佷笟淇℃伅鍒楄〃
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
     * 瀵煎嚭浼佷笟淇℃伅鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "浼佷笟淇℃伅", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EnterpriseInfo enterpriseInfo)
    {
        List<EnterpriseInfo> list = enterpriseInfoService.selectEnterpriseInfoList(enterpriseInfo);
        ExcelUtil<EnterpriseInfo> util = new ExcelUtil<EnterpriseInfo>(EnterpriseInfo.class);
        util.exportExcel(response, list, "浼佷笟淇℃伅鏁版嵁");
    }

    /**
     * 鑾峰彇浼佷笟淇℃伅璇︾粏淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{enterpriseId}")
    public AjaxResult getInfo(@PathVariable("enterpriseId") Long enterpriseId)
    {
        return success(enterpriseInfoService.selectEnterpriseInfoByEnterpriseId(enterpriseId));
    }

    /**
     * 鏂板浼佷笟淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "浼佷笟淇℃伅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return toAjax(enterpriseInfoService.insertEnterpriseInfo(enterpriseInfo));
    }

    /**
     * 淇敼浼佷笟淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "浼佷笟淇℃伅", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return toAjax(enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo));
    }

    /**
     * 鍒犻櫎浼佷笟淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "浼佷笟淇℃伅", businessType = BusinessType.DELETE)
	@DeleteMapping("/{enterpriseIds}")
    public AjaxResult remove(@PathVariable Long[] enterpriseIds)
    {
        return toAjax(enterpriseInfoService.deleteEnterpriseInfoByEnterpriseIds(enterpriseIds));
    }
}

