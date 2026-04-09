package com.ruoyi.campus.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 岗位信息对象 t_job_post
 * 
 * @author wly
 * @date 2026-04-08
 */
public class JobPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 岗位ID */
    private Long postId;

    /** 企业ID */
    private Long enterpriseId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 薪资下限 */
    @Excel(name = "薪资下限")
    private BigDecimal salaryMin;

    /** 薪资上限 */
    @Excel(name = "薪资上限")
    private BigDecimal salaryMax;

    /** 结算方式（1日结 2周结 3月结） */
    @Excel(name = "结算方式", readConverterExp = "1=日结,2=周结,3=月结")
    private String salaryUnit;

    /** 工作地点类型 */
    @Excel(name = "工作地点类型")
    private String workLocationType;

    /** 工作地点 */
    @Excel(name = "工作地点")
    private String workAddress;

    /** 工作类型 */
    @Excel(name = "工作类型")
    private String workType;

    /** 工作时间描述 */
    @Excel(name = "工作时间描述")
    private String workTimeDesc;

    /** 岗位要求 */
    private String requirementDesc;

    /** 招聘人数 */
    private Long recruitCount;

    /** 截止时间 */
    private Date deadlineTime;

    /** 联系人 */
    private String contactName;

    /** 联系方式-电话 */
    private String contactPhone;

    /** 岗位状态（0草稿 1上架 2下架） */
    private String postStatus;

    /** 浏览量 */
    private Long viewCount;

    /** 收藏量 */
    private Long collectCount;

    /** 投递量 */
    private Long applyCount;

    /** 录用人数 */
    private Long hireCount;

    /** 完成人数 */
    private Long completeCount;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setPostId(Long postId) 
    {
        this.postId = postId;
    }

    public Long getPostId() 
    {
        return postId;
    }

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setPostName(String postName) 
    {
        this.postName = postName;
    }

    public String getPostName() 
    {
        return postName;
    }

    public void setSalaryMin(BigDecimal salaryMin) 
    {
        this.salaryMin = salaryMin;
    }

    public BigDecimal getSalaryMin() 
    {
        return salaryMin;
    }

    public void setSalaryMax(BigDecimal salaryMax) 
    {
        this.salaryMax = salaryMax;
    }

    public BigDecimal getSalaryMax() 
    {
        return salaryMax;
    }

    public void setSalaryUnit(String salaryUnit) 
    {
        this.salaryUnit = salaryUnit;
    }

    public String getSalaryUnit() 
    {
        return salaryUnit;
    }

    public void setWorkLocationType(String workLocationType) 
    {
        this.workLocationType = workLocationType;
    }

    public String getWorkLocationType() 
    {
        return workLocationType;
    }

    public void setWorkAddress(String workAddress) 
    {
        this.workAddress = workAddress;
    }

    public String getWorkAddress() 
    {
        return workAddress;
    }

    public void setWorkType(String workType) 
    {
        this.workType = workType;
    }

    public String getWorkType() 
    {
        return workType;
    }

    public void setWorkTimeDesc(String workTimeDesc) 
    {
        this.workTimeDesc = workTimeDesc;
    }

    public String getWorkTimeDesc() 
    {
        return workTimeDesc;
    }

    public void setRequirementDesc(String requirementDesc) 
    {
        this.requirementDesc = requirementDesc;
    }

    public String getRequirementDesc() 
    {
        return requirementDesc;
    }

    public void setRecruitCount(Long recruitCount) 
    {
        this.recruitCount = recruitCount;
    }

    public Long getRecruitCount() 
    {
        return recruitCount;
    }

    public void setDeadlineTime(Date deadlineTime) 
    {
        this.deadlineTime = deadlineTime;
    }

    public Date getDeadlineTime() 
    {
        return deadlineTime;
    }

    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getContactName() 
    {
        return contactName;
    }

    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }

    public void setPostStatus(String postStatus) 
    {
        this.postStatus = postStatus;
    }

    public String getPostStatus() 
    {
        return postStatus;
    }

    public void setViewCount(Long viewCount) 
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount() 
    {
        return viewCount;
    }

    public void setCollectCount(Long collectCount) 
    {
        this.collectCount = collectCount;
    }

    public Long getCollectCount() 
    {
        return collectCount;
    }

    public void setApplyCount(Long applyCount) 
    {
        this.applyCount = applyCount;
    }

    public Long getApplyCount() 
    {
        return applyCount;
    }

    public void setHireCount(Long hireCount) 
    {
        this.hireCount = hireCount;
    }

    public Long getHireCount() 
    {
        return hireCount;
    }

    public void setCompleteCount(Long completeCount) 
    {
        this.completeCount = completeCount;
    }

    public Long getCompleteCount() 
    {
        return completeCount;
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
            .append("postId", getPostId())
            .append("enterpriseId", getEnterpriseId())
            .append("postName", getPostName())
            .append("salaryMin", getSalaryMin())
            .append("salaryMax", getSalaryMax())
            .append("salaryUnit", getSalaryUnit())
            .append("workLocationType", getWorkLocationType())
            .append("workAddress", getWorkAddress())
            .append("workType", getWorkType())
            .append("workTimeDesc", getWorkTimeDesc())
            .append("requirementDesc", getRequirementDesc())
            .append("recruitCount", getRecruitCount())
            .append("deadlineTime", getDeadlineTime())
            .append("contactName", getContactName())
            .append("contactPhone", getContactPhone())
            .append("postStatus", getPostStatus())
            .append("viewCount", getViewCount())
            .append("collectCount", getCollectCount())
            .append("applyCount", getApplyCount())
            .append("hireCount", getHireCount())
            .append("completeCount", getCompleteCount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
