package com.ruoyi.web.controller.enterprise;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.biz.enterprise.service.IEnterpriseFlowService;

/**
 * 企业岗位管理
 */
@RestController
@RequestMapping("/enterprise/post")
public class EnterprisePostController extends BaseController
{
    @Autowired
    private IEnterpriseFlowService enterpriseFlowService;

    /**
     * 查询岗位列表
     */
    @PreAuthorize("@ss.hasPermi('enterprise:post:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobPost query)
    {
        startPage();
        Long enterpriseId = SecurityUtils.getUserId();
        List<JobPost> list = enterpriseFlowService.listMyPosts(enterpriseId, query);
        return getDataTable(list);
    }

    /**
     * 获取岗位详细信息
     */
    @PreAuthorize("@ss.hasPermi('enterprise:post:query')")
    @GetMapping("/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return success(enterpriseFlowService.getMyPost(enterpriseId, postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('enterprise:post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody JobPost post)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.createMyPost(enterpriseId, post));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermi('enterprise:post:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody JobPost post)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.updateMyPost(enterpriseId, post));
    }

    /**
     * 上架岗位
     */
    @PreAuthorize("@ss.hasPermi('enterprise:post:up')")
    @Log(title = "岗位上架", businessType = BusinessType.UPDATE)
    @PutMapping("/{postId}/up")
    public AjaxResult up(@PathVariable Long postId)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.upMyPost(enterpriseId, postId));
    }

    /**
     * 下架岗位
     */
    @PreAuthorize("@ss.hasPermi('enterprise:post:down')")
    @Log(title = "岗位下架", businessType = BusinessType.UPDATE)
    @PutMapping("/{postId}/down")
    public AjaxResult down(@PathVariable Long postId)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.downMyPost(enterpriseId, postId));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermi('enterprise:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postId}")
    public AjaxResult remove(@PathVariable Long postId)
    {
        Long enterpriseId = SecurityUtils.getUserId();
        return toAjax(enterpriseFlowService.deleteMyPost(enterpriseId, postId));
    }
}
