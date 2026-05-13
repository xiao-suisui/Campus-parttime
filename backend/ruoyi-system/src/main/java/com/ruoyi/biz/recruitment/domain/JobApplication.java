package com.ruoyi.biz.recruitment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 投递记录对象 t_job_application
 * 
 * @author wly
 * @date 2026-04-08
 */
public class JobApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 投递ID */
    private Long applicationId;

    /** 岗位ID */
    @Excel(name = "岗位ID")
    private Long postId;

    /** 学生ID */
    @Excel(name = "学生ID")
    private Long studentId;

    /** 简历ID */
    @Excel(name = "简历ID")
    private Long resumeId;

    /** 投递时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投递时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 处理状态（0已投递 1已录用 2已完成） */
    @Excel(name = "处理状态", readConverterExp = "0=已投递,1=已录用,2=已完成")
    private String applicationStatus;

    /** 最近处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最近处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date processTime;

    /** 企业备注 */
    @Excel(name = "企业备注")
    private String enterpriseRemark;

    /** 企业标记完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "企业标记完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;

    /** 岗位名称（关联查询） */
    private String postName;

    /** 企业名称（关联查询） */
    private String enterpriseName;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setApplicationId(Long applicationId) 
    {
        this.applicationId = applicationId;
    }

    public Long getApplicationId() 
    {
        return applicationId;
    }

    public void setPostId(Long postId) 
    {
        this.postId = postId;
    }

    public Long getPostId() 
    {
        return postId;
    }

    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }

    public void setResumeId(Long resumeId) 
    {
        this.resumeId = resumeId;
    }

    public Long getResumeId() 
    {
        return resumeId;
    }

    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }

    public void setApplicationStatus(String applicationStatus) 
    {
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationStatus() 
    {
        return applicationStatus;
    }

    public void setProcessTime(Date processTime) 
    {
        this.processTime = processTime;
    }

    public Date getProcessTime() 
    {
        return processTime;
    }

    public void setEnterpriseRemark(String enterpriseRemark) 
    {
        this.enterpriseRemark = enterpriseRemark;
    }

    public String getEnterpriseRemark() 
    {
        return enterpriseRemark;
    }

    public void setCompleteTime(Date completeTime) 
    {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime() 
    {
        return completeTime;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getEnterpriseName()
    {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName)
    {
        this.enterpriseName = enterpriseName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("applicationId", getApplicationId())
            .append("postId", getPostId())
            .append("studentId", getStudentId())
            .append("resumeId", getResumeId())
            .append("applyTime", getApplyTime())
            .append("applicationStatus", getApplicationStatus())
            .append("processTime", getProcessTime())
            .append("enterpriseRemark", getEnterpriseRemark())
            .append("completeTime", getCompleteTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("postName", getPostName())
            .append("enterpriseName", getEnterpriseName())
            .append("delFlag", getDelFlag())
            .toString();
    }
}


