package com.ruoyi.web.controller.student;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.biz.student.service.IStudentFlowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.biz.recruitment.domain.JobCollection;

/**
 * Student-side collection endpoints.
 */
@RestController
@RequestMapping("/student/collection")
public class StudentCollectionController extends BaseController
{
    @Autowired
    private IStudentFlowService studentFlowService;

    @PreAuthorize("@ss.hasPermi('student:collection:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobCollection query)
    {
        startPage();
        Long studentId = SecurityUtils.getUserId();
        List<JobCollection> list = studentFlowService.listMyCollections(studentId, query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('student:collection:add')")
    @PostMapping
    public AjaxResult add(@RequestBody StudentCollectionRequest request)
    {
        Long studentId = SecurityUtils.getUserId();
        boolean added = studentFlowService.collectPost(studentId, request.getPostId());
        return AjaxResult.success(added ? "收藏成功" : "已收藏，无需重复操作");
    }

    @PreAuthorize("@ss.hasPermi('student:collection:remove')")
    @DeleteMapping("/{postId}")
    public AjaxResult remove(@PathVariable Long postId)
    {
        Long studentId = SecurityUtils.getUserId();
        boolean removed = studentFlowService.uncollectPost(studentId, postId);
        return AjaxResult.success(removed ? "取消收藏成功" : "未找到可取消的收藏记录");
    }

    public static class StudentCollectionRequest
    {
        private Long postId;

        public Long getPostId()
        {
            return postId;
        }

        public void setPostId(Long postId)
        {
            this.postId = postId;
        }
    }
}


