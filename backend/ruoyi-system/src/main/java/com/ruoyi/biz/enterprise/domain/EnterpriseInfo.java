package com.ruoyi.biz.enterprise.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业信息对象 t_enterprise_info
 * 
 * @author wly
 * @date 2026-04-08
 */
public class EnterpriseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 企业ID（关联sys_user.user_id） */
    private Long enterpriseId;

    /** 企业名称 */
    private String enterpriseName;

    /** 统一社会信用代码 */
    private String unifiedCode;

    /** 法人信息 */
    private String legalPerson;

    /** 联系人 */
    private String contactName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 营业执照图片路径 */
    @Excel(name = "营业执照")
    private String licenseUrl;

    /** 企业简介 */
    @Excel(name = "企业简介")
    private String intro;

    /** 审核状态（0待审核 1通过 2驳回） */
    @Excel(name = "审核状态", readConverterExp = "0=待审核,1=通过,2=驳回")
    private String auditStatus;

    /** 驳回原因 */
    @Excel(name = "驳回原因")
    private String auditReason;

    /** 审核人（sys_user.user_id） */
    private Long auditBy;

    /** 审核时间 */
    private Date auditTime;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setEnterpriseName(String enterpriseName) 
    {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseName() 
    {
        return enterpriseName;
    }

    public void setUnifiedCode(String unifiedCode) 
    {
        this.unifiedCode = unifiedCode;
    }

    public String getUnifiedCode() 
    {
        return unifiedCode;
    }

    public void setLegalPerson(String legalPerson) 
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() 
    {
        return legalPerson;
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

    public void setLicenseUrl(String licenseUrl) 
    {
        this.licenseUrl = licenseUrl;
    }

    public String getLicenseUrl() 
    {
        return licenseUrl;
    }

    public void setIntro(String intro) 
    {
        this.intro = intro;
    }

    public String getIntro() 
    {
        return intro;
    }

    public void setAuditStatus(String auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() 
    {
        return auditStatus;
    }

    public void setAuditReason(String auditReason) 
    {
        this.auditReason = auditReason;
    }

    public String getAuditReason() 
    {
        return auditReason;
    }

    public void setAuditBy(Long auditBy) 
    {
        this.auditBy = auditBy;
    }

    public Long getAuditBy() 
    {
        return auditBy;
    }

    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
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
            .append("enterpriseId", getEnterpriseId())
            .append("enterpriseName", getEnterpriseName())
            .append("unifiedCode", getUnifiedCode())
            .append("legalPerson", getLegalPerson())
            .append("contactName", getContactName())
            .append("contactPhone", getContactPhone())
            .append("licenseUrl", getLicenseUrl())
            .append("intro", getIntro())
            .append("auditStatus", getAuditStatus())
            .append("auditReason", getAuditReason())
            .append("auditBy", getAuditBy())
            .append("auditTime", getAuditTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}


