package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 双向互评对象 t_job_evaluation
 * 
 * @author wly
 * @date 2026-04-08
 */
public class JobEvaluation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评价ID */
    private Long evaluationId;

    /** 投递记录 */
    @Excel(name = "投递记录")
    private Long applicationId;

    /** 岗位ID */
    private Long postId;

    /** 学生ID */
    private Long studentId;

    /** 企业ID */
    private Long enterpriseId;

    /** 评价类型 */
    @Excel(name = "评价类型")
    private String evaluationType;

    /** 评分（1-5） */
    @Excel(name = "评分", readConverterExp = "1=-5")
    private Integer score;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String content;

    /** 评价时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评价时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date evaluationTime;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setEvaluationId(Long evaluationId) 
    {
        this.evaluationId = evaluationId;
    }

    public Long getEvaluationId() 
    {
        return evaluationId;
    }

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

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setEvaluationType(String evaluationType) 
    {
        this.evaluationType = evaluationType;
    }

    public String getEvaluationType() 
    {
        return evaluationType;
    }

    public void setScore(Integer score) 
    {
        this.score = score;
    }

    public Integer getScore() 
    {
        return score;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setEvaluationTime(Date evaluationTime) 
    {
        this.evaluationTime = evaluationTime;
    }

    public Date getEvaluationTime() 
    {
        return evaluationTime;
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
            .append("evaluationId", getEvaluationId())
            .append("applicationId", getApplicationId())
            .append("postId", getPostId())
            .append("studentId", getStudentId())
            .append("enterpriseId", getEnterpriseId())
            .append("evaluationType", getEvaluationType())
            .append("score", getScore())
            .append("content", getContent())
            .append("evaluationTime", getEvaluationTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
