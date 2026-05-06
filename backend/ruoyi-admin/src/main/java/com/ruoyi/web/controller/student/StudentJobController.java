package com.ruoyi.web.controller.student;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.biz.student.service.IStudentFlowService;
import com.ruoyi.biz.student.service.IRecommendService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * Student-side job browsing endpoints.
 */
@RestController
@RequestMapping("/student/post")
public class StudentJobController extends BaseController
{
    @Autowired
    private IStudentFlowService studentFlowService;

    @Autowired
    private IRecommendService recommendService;

    @PreAuthorize("@ss.hasPermi('student:job:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobPost query)
    {
        startPage();
        List<JobPost> list = studentFlowService.listAvailablePosts(query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('student:job:query')")
    @GetMapping("/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId)
    {
        return success(studentFlowService.getAvailablePostDetail(postId));
    }

    /**
     * 智能推荐岗位
     */
    @PreAuthorize("@ss.hasPermi('student:job:recommend')")
    @GetMapping("/recommend")
    public AjaxResult recommend(Integer limit)
    {
        Long studentId = SecurityUtils.getUserId();
        int maxCount = (limit != null && limit > 0) ? limit : 10;
        return success(recommendService.recommendPosts(studentId, maxCount));
    }
}


