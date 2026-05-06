package com.ruoyi.web.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.biz.student.domain.StudentInfo;
import com.ruoyi.biz.student.service.IStudentInfoService;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;
import com.ruoyi.biz.enterprise.service.IEnterpriseInfoService;
import com.ruoyi.biz.recruitment.domain.JobPost;
import com.ruoyi.biz.recruitment.service.IJobPostService;
import com.ruoyi.biz.recruitment.domain.JobApplication;
import com.ruoyi.biz.recruitment.service.IJobApplicationService;

/**
 * 管理员运营数据统计
 */
@RestController
@RequestMapping("/admin/stat")
public class AdminStatController extends BaseController
{
    @Autowired
    private IStudentInfoService studentInfoService;

    @Autowired
    private IEnterpriseInfoService enterpriseInfoService;

    @Autowired
    private IJobPostService jobPostService;

    @Autowired
    private IJobApplicationService jobApplicationService;

    /**
     * 核心指标汇总
     */
    @PreAuthorize("@ss.hasPermi('admin:stat:query')")
    @GetMapping("/overview")
    public AjaxResult overview()
    {
        Map<String, Object> data = new HashMap<>();
        data.put("studentCount", studentInfoService.selectStudentInfoList(new StudentInfo()).size());
        data.put("enterpriseCount", enterpriseInfoService.selectEnterpriseInfoList(new EnterpriseInfo()).size());
        data.put("jobCount", jobPostService.selectJobPostList(new JobPost()).size());
        data.put("applicationCount", jobApplicationService.selectJobApplicationList(new JobApplication()).size());
        return success(data);
    }

    /**
     * 近 N 天趋势数据
     */
    @PreAuthorize("@ss.hasPermi('admin:stat:query')")
    @GetMapping("/trend")
    public AjaxResult trend(Integer days)
    {
        if (days == null || days <= 0)
        {
            days = 30;
        }
        Date startDate = getStartDate(days);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> dates = buildDateList(startDate, days, sdf);
        Map<String, Long> studentMap = countByDate(studentInfoService.selectStudentInfoList(new StudentInfo()), startDate, sdf);
        Map<String, Long> enterpriseMap = countByDate(enterpriseInfoService.selectEnterpriseInfoList(new EnterpriseInfo()), startDate, sdf);
        Map<String, Long> jobMap = countByDate(jobPostService.selectJobPostList(new JobPost()), startDate, sdf);
        Map<String, Long> applicationMap = countByDate(jobApplicationService.selectJobApplicationList(new JobApplication()), startDate, sdf);

        List<Long> studentTrend = new ArrayList<>();
        List<Long> enterpriseTrend = new ArrayList<>();
        List<Long> jobTrend = new ArrayList<>();
        List<Long> applicationTrend = new ArrayList<>();
        for (String date : dates)
        {
            studentTrend.add(studentMap.getOrDefault(date, 0L));
            enterpriseTrend.add(enterpriseMap.getOrDefault(date, 0L));
            jobTrend.add(jobMap.getOrDefault(date, 0L));
            applicationTrend.add(applicationMap.getOrDefault(date, 0L));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("dates", dates);
        result.put("studentTrend", studentTrend);
        result.put("enterpriseTrend", enterpriseTrend);
        result.put("jobTrend", jobTrend);
        result.put("applicationTrend", applicationTrend);
        return success(result);
    }

    private Date getStartDate(int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -(days - 1));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private List<String> buildDateList(Date startDate, int days, SimpleDateFormat sdf)
    {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        for (int i = 0; i < days; i++)
        {
            dates.add(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private <T> Map<String, Long> countByDate(List<T> entities, Date startDate, SimpleDateFormat sdf)
    {
        Map<String, Long> map = new LinkedHashMap<>();
        for (T entity : entities)
        {
            Date createTime = null;
            try
            {
                java.lang.reflect.Method method = entity.getClass().getMethod("getCreateTime");
                Object val = method.invoke(entity);
                if (val instanceof Date)
                {
                    createTime = (Date) val;
                }
            }
            catch (Exception ignored)
            {
            }
            if (createTime != null && !createTime.before(startDate))
            {
                String key = sdf.format(createTime);
                map.merge(key, 1L, Long::sum);
            }
        }
        return map;
    }
}
