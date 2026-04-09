package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 浼佷笟淇℃伅瀵硅薄 t_enterprise_info
 * 
 * @author wly
 * @date 2026-04-08
 */
public class EnterpriseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 浼佷笟ID锛堝叧鑱攕ys_user.user_id锛?*/
    private Long enterpriseId;

    /** 浼佷笟鍚嶇О */
    private String enterpriseName;

    /** 缁熶竴绀句細淇＄敤浠ｇ爜 */
    private String unifiedCode;

    /** 娉曚汉淇℃伅 */
    private String legalPerson;

    /** 鑱旂郴浜?*/
    private String contactName;

    /** 鑱旂郴鐢佃瘽 */
    @Excel(name = "鑱旂郴鐢佃瘽")
    private String contactPhone;

    /** 钀ヤ笟鎵х収鍥剧墖璺緞 */
    @Excel(name = "钀ヤ笟鎵х収鍥剧墖璺緞")
    private String licenseUrl;

    /** 浼佷笟绠€浠?*/
    @Excel(name = "浼佷笟绠€浠?)
    private String intro;

    /** 瀹℃牳鐘舵€侊紙0寰呭鏍?1閫氳繃 2椹冲洖锛?*/
    @Excel(name = "瀹℃牳鐘舵€?, readConverterExp = "0=寰呭鏍?1=閫氳繃,2=椹冲洖")
    private String auditStatus;

    /** 椹冲洖鍘熷洜 */
    @Excel(name = "椹冲洖鍘熷洜")
    private String auditReason;

    /** 瀹℃牳浜猴紙sys_user.user_id锛?*/
    private Long auditBy;

    /** 瀹℃牳鏃堕棿 */
    private Date auditTime;

    /** 鍒犻櫎鏍囧織锛?瀛樺湪 2鍒犻櫎锛?*/
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

