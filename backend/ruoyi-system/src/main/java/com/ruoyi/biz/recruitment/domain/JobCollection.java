package com.ruoyi.biz.recruitment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 岗位收藏对象 t_job_collection
 *
 * @author ruoyi
 * @date 2026-04-08
 */
public class JobCollection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 收藏ID */
    private Long collectionId;

    /** 学生ID */
    @Excel(name = "学生ID")
    private Long studentId;

    /** 岗位ID */
    @Excel(name = "岗位ID")
    private Long postId;

    /** 收藏时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收藏时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date collectionTime;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    /** ---- 以下为关联查询的岗位信息（非表字段） ---- */

    /** 岗位名称 */
    private String postName;

    /** 薪资下限 */
    private java.math.BigDecimal salaryMin;

    /** 薪资上限 */
    private java.math.BigDecimal salaryMax;

    /** 结算方式 */
    private String salaryUnit;

    /** 工作地点 */
    private String workAddress;

    public void setPostName(String postName) { this.postName = postName; }
    public String getPostName() { return postName; }

    public void setSalaryMin(java.math.BigDecimal salaryMin) { this.salaryMin = salaryMin; }
    public java.math.BigDecimal getSalaryMin() { return salaryMin; }

    public void setSalaryMax(java.math.BigDecimal salaryMax) { this.salaryMax = salaryMax; }
    public java.math.BigDecimal getSalaryMax() { return salaryMax; }

    public void setSalaryUnit(String salaryUnit) { this.salaryUnit = salaryUnit; }
    public String getSalaryUnit() { return salaryUnit; }

    public void setWorkAddress(String workAddress) { this.workAddress = workAddress; }
    public String getWorkAddress() { return workAddress; }

    public void setCollectionId(Long collectionId)
    {
        this.collectionId = collectionId;
    }

    public Long getCollectionId()
    {
        return collectionId;
    }

    public void setStudentId(Long studentId)
    {
        this.studentId = studentId;
    }

    public Long getStudentId()
    {
        return studentId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public Long getPostId()
    {
        return postId;
    }

    public void setCollectionTime(Date collectionTime)
    {
        this.collectionTime = collectionTime;
    }

    public Date getCollectionTime()
    {
        return collectionTime;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("collectionId", getCollectionId())
            .append("studentId", getStudentId())
            .append("postId", getPostId())
            .append("collectionTime", getCollectionTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}


