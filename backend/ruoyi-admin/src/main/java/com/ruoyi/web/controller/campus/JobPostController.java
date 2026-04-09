package com.ruoyi.web.controller.campus;

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
import com.ruoyi.campus.domain.JobPost;
import com.ruoyi.campus.service.IJobPostService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 宀椾綅淇℃伅Controller
 * 
 * @author wly
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/campus/post")
public class JobPostController extends BaseController
{
    @Autowired
    private IJobPostService jobPostService;

    /**
     * 鏌ヨ宀椾綅淇℃伅鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('campus:post:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobPost jobPost)
    {
        startPage();
        List<JobPost> list = jobPostService.selectJobPostList(jobPost);
        return getDataTable(list);
    }

    /**
     * 瀵煎嚭宀椾綅淇℃伅鍒楄〃
     */
    @PreAuthorize("@ss.hasPermi('campus:post:export')")
    @Log(title = "宀椾綅淇℃伅", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JobPost jobPost)
    {
        List<JobPost> list = jobPostService.selectJobPostList(jobPost);
        ExcelUtil<JobPost> util = new ExcelUtil<JobPost>(JobPost.class);
        util.exportExcel(response, list, "宀椾綅淇℃伅鏁版嵁");
    }

    /**
     * 鑾峰彇宀椾綅淇℃伅璇︾粏淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('campus:post:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable("postId") Long postId)
    {
        return success(jobPostService.selectJobPostByPostId(postId));
    }

    /**
     * 鏂板宀椾綅淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('campus:post:add')")
    @Log(title = "宀椾綅淇℃伅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JobPost jobPost)
    {
        return toAjax(jobPostService.insertJobPost(jobPost));
    }

    /**
     * 淇敼宀椾綅淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('campus:post:edit')")
    @Log(title = "宀椾綅淇℃伅", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JobPost jobPost)
    {
        return toAjax(jobPostService.updateJobPost(jobPost));
    }

    /**
     * 鍒犻櫎宀椾綅淇℃伅
     */
    @PreAuthorize("@ss.hasPermi('campus:post:remove')")
    @Log(title = "宀椾綅淇℃伅", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds)
    {
        return toAjax(jobPostService.deleteJobPostByPostIds(postIds));
    }
}

