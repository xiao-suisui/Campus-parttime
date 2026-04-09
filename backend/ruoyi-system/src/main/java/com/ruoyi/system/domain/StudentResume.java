package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 瀛︾敓绠€鍘嗗璞?t_student_resume
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
public class StudentResume extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 绠€鍘咺D */
    private Long resumeId;

    /** 瀛︾敓ID锛堝叧鑱攕ys_user.user_id锛?*/
    @Excel(name = "瀛︾敓ID", readConverterExp = "鍏?鑱攕ys_user.user_id")
    private Long studentId;

    /** 姹傝亴鎰忓悜 */
    @Excel(name = "姹傝亴鎰忓悜")
    private String expectedPosition;

    /** 鏈熸湜钖祫涓嬮檺 */
    @Excel(name = "鏈熸湜钖祫涓嬮檺")
    private BigDecimal expectedSalaryMin;

    /** 鏈熸湜钖祫涓婇檺 */
    @Excel(name = "鏈熸湜钖祫涓婇檺")
    private BigDecimal expectedSalaryMax;

    /** 鏁欒偛缁忓巻 */
    private String educationExperience;

    /** 鍏艰亴缁忓巻 */
    private String partTimeExperience;

    /** 鎶€鑳界壒闀?*/
    private String skillTags;

    /** 鑷垜浠嬬粛 */
    private String selfIntro;

    /** 鐘舵€侊紙0鏈夋晥 1鍋滅敤锛?*/
    @Excel(name = "鐘舵€?, readConverterExp = "0=鏈夋晥,1=鍋滅敤")
    private String status;

    /** 鍒犻櫎鏍囧織锛?瀛樺湪 2鍒犻櫎锛?*/
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

