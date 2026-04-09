package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 鎶曢€掕褰曞璞?t_job_application
 * 
 * @author wly
 * @date 2026-04-08
 */
public class JobApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 鎶曢€扞D */
    private Long applicationId;

    /** 宀椾綅ID */
    @Excel(name = "宀椾綅ID")
    private Long postId;

    /** 瀛︾敓ID */
    @Excel(name = "瀛︾敓ID")
    private Long studentId;

    /** 绠€鍘咺D */
    @Excel(name = "绠€鍘咺D")
    private Long resumeId;

    /** 鎶曢€掓椂闂?*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "鎶曢€掓椂闂?, width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 澶勭悊鐘舵€侊紙0宸叉姇閫?1宸插綍鐢?2浼佷笟宸插畬鎴愶級 */
    @Excel(name = "澶勭悊鐘舵€?, readConverterExp = "0=宸叉姇閫?1=宸插綍鐢?2=浼佷笟宸插畬鎴?)
    private String applicationStatus;

    /** 鏈€杩戝鐞嗘椂闂?*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "鏈€杩戝鐞嗘椂闂?, width = 30, dateFormat = "yyyy-MM-dd")
    private Date processTime;

    /** 浼佷笟澶囨敞 */
    @Excel(name = "浼佷笟澶囨敞")
    private String enterpriseRemark;

    /** 浼佷笟鏍囪瀹屾垚鏃堕棿 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "浼佷笟鏍囪瀹屾垚鏃堕棿", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;

    /** 鍒犻櫎鏍囧織锛?瀛樺湪 2鍒犻櫎锛?*/
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
            .append("delFlag", getDelFlag())
            .toString();
    }
}

