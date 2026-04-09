package com.ruoyi.campus.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 宀椾綅淇℃伅瀵硅薄 t_job_post
 * 
 * @author wly
 * @date 2026-04-08
 */
public class JobPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 宀椾綅ID */
    private Long postId;

    /** 浼佷笟ID */
    private Long enterpriseId;

    /** 宀椾綅鍚嶇О */
    @Excel(name = "宀椾綅鍚嶇О")
    private String postName;

    /** 钖祫涓嬮檺 */
    @Excel(name = "钖祫涓嬮檺")
    private BigDecimal salaryMin;

    /** 钖祫涓婇檺 */
    @Excel(name = "钖祫涓婇檺")
    private BigDecimal salaryMax;

    /** 缁撶畻鏂瑰紡锛?鏃ョ粨 2鍛ㄧ粨 3鏈堢粨锛?*/
    @Excel(name = "缁撶畻鏂瑰紡", readConverterExp = "1=鏃ョ粨,2=鍛ㄧ粨,3=鏈堢粨")
    private String salaryUnit;

    /** 宸ヤ綔鍦扮偣绫诲瀷 */
    @Excel(name = "宸ヤ綔鍦扮偣绫诲瀷")
    private String workLocationType;

    /** 宸ヤ綔鍦扮偣 */
    @Excel(name = "宸ヤ綔鍦扮偣")
    private String workAddress;

    /** 宸ヤ綔绫诲瀷 */
    @Excel(name = "宸ヤ綔绫诲瀷")
    private String workType;

    /** 宸ヤ綔鏃堕棿鎻忚堪 */
    @Excel(name = "宸ヤ綔鏃堕棿鎻忚堪")
    private String workTimeDesc;

    /** 宀椾綅瑕佹眰 */
    private String requirementDesc;

    /** 鎷涜仒浜烘暟 */
    private Long recruitCount;

    /** 鎴鏃堕棿 */
    private Date deadlineTime;

    /** 鑱旂郴浜?*/
    private String contactName;

    /** 鑱旂郴鏂瑰紡-鐢佃瘽 */
    private String contactPhone;

    /** 宀椾綅鐘舵€侊紙0鑽夌 1涓婃灦 2涓嬫灦锛?*/
    private String postStatus;

    /** 娴忚閲?*/
    private Long viewCount;

    /** 鏀惰棌閲?*/
    private Long collectCount;

    /** 鎶曢€掗噺 */
    private Long applyCount;

    /** 褰曠敤浜烘暟 */
    private Long hireCount;

    /** 瀹屾垚浜烘暟 */
    private Long completeCount;

    /** 鍒犻櫎鏍囧織锛?瀛樺湪 2鍒犻櫎锛?*/
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

