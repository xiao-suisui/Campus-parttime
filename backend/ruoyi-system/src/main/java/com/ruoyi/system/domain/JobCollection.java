package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 宀椾綅鏀惰棌瀵硅薄 t_job_collection
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
public class JobCollection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 鏀惰棌ID */
    private Long collectionId;

    /** 瀛︾敓ID */
    @Excel(name = "瀛︾敓ID")
    private Long studentId;

    /** 宀椾綅ID */
    @Excel(name = "宀椾綅ID")
    private Long postId;

    /** 鏀惰棌鏃堕棿 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "鏀惰棌鏃堕棿", width = 30, dateFormat = "yyyy-MM-dd")
    private Date collectionTime;

    /** 鍒犻櫎鏍囧織锛?瀛樺湪 2鍒犻櫎锛?*/
    private String delFlag;

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

