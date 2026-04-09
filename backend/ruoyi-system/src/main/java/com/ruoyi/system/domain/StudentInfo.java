package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 瀛︾敓淇℃伅瀵硅薄 t_student_info
 * 
 * @author wly
 * @date 2026-04-08
 */
public class StudentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 瀛︾敓ID锛堝叧鑱攕ys_user.user_id锛?*/
    private Long studentId;

    /** 瀛﹀彿 */
    @Excel(name = "瀛﹀彿")
    private String studentNo;

    /** 濮撳悕 */
    @Excel(name = "濮撳悕")
    private String realName;

    /** 鎬у埆 */
    @Excel(name = "鎬у埆")
    private String gender;

    /** 瀛︽牎鍚嶇О */
    @Excel(name = "瀛︽牎鍚嶇О")
    private String schoolName;

    /** 涓撲笟鍚嶇О */
    @Excel(name = "涓撲笟鍚嶇О")
    private String majorName;

    /** 鍏ュ骞翠唤 */
    @Excel(name = "鍏ュ骞翠唤")
    private Long gradeYear;

    /** 鑱旂郴鐢佃瘽 */
    private String phone;

    /** 鍒犻櫎鏍囧織锛?瀛樺湪 2鍒犻櫎锛?*/
    private String delFlag;

    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }

    public void setStudentNo(String studentNo) 
    {
        this.studentNo = studentNo;
    }

    public String getStudentNo() 
    {
        return studentNo;
    }

    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setSchoolName(String schoolName) 
    {
        this.schoolName = schoolName;
    }

    public String getSchoolName() 
    {
        return schoolName;
    }

    public void setMajorName(String majorName) 
    {
        this.majorName = majorName;
    }

    public String getMajorName() 
    {
        return majorName;
    }

    public void setGradeYear(Long gradeYear) 
    {
        this.gradeYear = gradeYear;
    }

    public Long getGradeYear() 
    {
        return gradeYear;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
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
            .append("studentId", getStudentId())
            .append("studentNo", getStudentNo())
            .append("realName", getRealName())
            .append("gender", getGender())
            .append("schoolName", getSchoolName())
            .append("majorName", getMajorName())
            .append("gradeYear", getGradeYear())
            .append("phone", getPhone())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}

