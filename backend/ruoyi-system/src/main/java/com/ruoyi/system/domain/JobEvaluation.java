package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 鍙屽悜浜掕瘎瀵硅薄 t_job_evaluation
 * 
 * @author wly
 * @date 2026-04-08
 */
public class JobEvaluation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 璇勪环ID */
    private Long evaluationId;

    /** 鎶曢€掕褰?*/
    @Excel(name = "鎶曢€掕褰?)
    private Long applicationId;

    /** 宀椾綅ID */
    private Long postId;

    /** 瀛︾敓ID */
    private Long studentId;

    /** 浼佷笟ID */
    private Long enterpriseId;

    /** 璇勪环绫诲瀷 */
    @Excel(name = "璇勪环绫诲瀷")
    private String evaluationType;

    /** 璇勫垎锛?-5锛?*/
    @Excel(name = "璇勫垎", readConverterExp = "1=-5")
    private Integer score;

    /** 璇勪环鍐呭 */
    @Excel(name = "璇勪环鍐呭")
    private String content;

    /** 璇勪环鏃堕棿 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "璇勪环鏃堕棿", width = 30, dateFormat = "yyyy-MM-dd")
    private Date evaluationTime;

    /** 鍒犻櫎鏍囧織锛?瀛樺湪 2鍒犻櫎锛?*/
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

