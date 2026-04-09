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
import com.ruoyi.system.domain.JobCollection;
import com.ruoyi.system.service.IJobCollectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 宀椾綅鏀惰棌Controller
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/system/collection")
public class JobCollectionController extends BaseController
{
    @Autowired
    private IJobCollectionService JobCollectionService;

    /**
     * 鏌ヨ宀椾綅鏀惰棌鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('system:collection:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobCollection JobCollection)
    {
        startPage();
        List<JobCollection> list = JobCollectionService.selectJobCollectionList(JobCollection);
        return getDataTable(list);
    }

    /**
     * 瀵煎嚭宀椾綅鏀惰棌鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('system:collection:export')")
    @Log(title = "宀椾綅鏀惰棌", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JobCollection JobCollection)
    {
        List<JobCollection> list = JobCollectionService.selectJobCollectionList(JobCollection);
        ExcelUtil<JobCollection> util = new ExcelUtil<JobCollection>(JobCollection.class);
        util.exportExcel(response, list, "宀椾綅鏀惰棌鏁版嵁");
    }

    /**
     * 鑾峰彇宀椾綅鏀惰棌璇︾粏淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('system:collection:query')")
    @GetMapping(value = "/{collectionId}")
    public AjaxResult getInfo(@PathVariable("collectionId") Long collectionId)
    {
        return success(JobCollectionService.selectJobCollectionByCollectionId(collectionId));
    }

    /**
     * 鏂板宀椾綅鏀惰棌
     */
    @PreAuthorize("@ss.hasPermi('system:collection:add')")
    @Log(title = "宀椾綅鏀惰棌", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JobCollection JobCollection)
    {
        return toAjax(JobCollectionService.insertJobCollection(JobCollection));
    }

    /**
     * 淇敼宀椾綅鏀惰棌
     */
    @PreAuthorize("@ss.hasPermi('system:collection:edit')")
    @Log(title = "宀椾綅鏀惰棌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JobCollection JobCollection)
    {
        return toAjax(JobCollectionService.updateJobCollection(JobCollection));
    }

    /**
     * 鍒犻櫎宀椾綅鏀惰棌
     */
    @PreAuthorize("@ss.hasPermi('system:collection:remove')")
    @Log(title = "宀椾綅鏀惰棌", businessType = BusinessType.DELETE)
	@DeleteMapping("/{collectionIds}")
    public AjaxResult remove(@PathVariable Long[] collectionIds)
    {
        return toAjax(JobCollectionService.deleteJobCollectionByCollectionIds(collectionIds));
    }
}

