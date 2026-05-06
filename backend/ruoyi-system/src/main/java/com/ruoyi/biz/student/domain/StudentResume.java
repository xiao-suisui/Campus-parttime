package com.ruoyi.biz.student.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生简历对象 t_student_resume
 *
 * @author ruoyi
 * @date 2026-04-08
 */
public class StudentResume extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 简历ID */
    private Long resumeId;

    /** 学生ID（关联sys_user.user_id） */
    @Excel(name = "学生ID")
    private Long studentId;

    /** 求职意向 */
    @Excel(name = "求职意向")
    private String expectedPosition;

    /** 期望薪资下限 */
    @Excel(name = "期望薪资下限")
    private BigDecimal expectedSalaryMin;

    /** 期望薪资上限 */
    @Excel(name = "期望薪资上限")
    private BigDecimal expectedSalaryMax;

    /** 教育经历 */
    private String educationExperience;

    /** 兼职经历 */
    private String partTimeExperience;

    /** 技能特长 */
    private String skillTags;

    /** 自我介绍 */
    private String selfIntro;

    /** 状态（0有效 1停用） */
    @Excel(name = "状态", readConverterExp = "0=有效,1=停用")
    private String status;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setResumeId(Long resumeId)
    {
        this.resumeId = resumeId;
    }

    public Long getResumeId()
    {
        return resumeId;
    }

    public void setStudentId(Long studentId)
    {
        this.studentId = studentId;
    }

    public Long getStudentId()
    {
        return studentId;
    }

    public void setExpectedPosition(String expectedPosition)
    {
        this.expectedPosition = expectedPosition;
    }

    public String getExpectedPosition()
    {
        return expectedPosition;
    }

    public void setExpectedSalaryMin(BigDecimal expectedSalaryMin)
    {
        this.expectedSalaryMin = expectedSalaryMin;
    }

    public BigDecimal getExpectedSalaryMin()
    {
        return expectedSalaryMin;
    }

    public void setExpectedSalaryMax(BigDecimal expectedSalaryMax)
    {
        this.expectedSalaryMax = expectedSalaryMax;
    }

    public BigDecimal getExpectedSalaryMax()
    {
        return expectedSalaryMax;
    }

    public void setEducationExperience(String educationExperience)
    {
        this.educationExperience = educationExperience;
    }

    public String getEducationExperience()
    {
        return educationExperience;
    }

    public void setPartTimeExperience(String partTimeExperience)
    {
        this.partTimeExperience = partTimeExperience;
    }

    public String getPartTimeExperience()
    {
        return partTimeExperience;
    }

    public void setSkillTags(String skillTags)
    {
        this.skillTags = skillTags;
    }

    public String getSkillTags()
    {
        return skillTags;
    }

    public void setSelfIntro(String selfIntro)
    {
        this.selfIntro = selfIntro;
    }

    public String getSelfIntro()
    {
        return selfIntro;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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
            .append("resumeId", getResumeId())
            .append("studentId", getStudentId())
            .append("expectedPosition", getExpectedPosition())
            .append("expectedSalaryMin", getExpectedSalaryMin())
            .append("expectedSalaryMax", getExpectedSalaryMax())
            .append("educationExperience", getEducationExperience())
            .append("partTimeExperience", getPartTimeExperience())
            .append("skillTags", getSkillTags())
            .append("selfIntro", getSelfIntro())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}


