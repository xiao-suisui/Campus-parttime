package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息对象 t_student_info
 * 
 * @author wly
 * @date 2026-04-08
 */
public class StudentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学生ID（关联sys_user.user_id） */
    private Long studentId;

    /** 学号 */
    @Excel(name = "学号")
    private String studentNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String realName;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 学校名称 */
    @Excel(name = "学校名称")
    private String schoolName;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String majorName;

    /** 入学年份 */
    @Excel(name = "入学年份")
    private Long gradeYear;

    /** 联系电话 */
    private String phone;

    /** 删除标志（0存在 2删除） */
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
