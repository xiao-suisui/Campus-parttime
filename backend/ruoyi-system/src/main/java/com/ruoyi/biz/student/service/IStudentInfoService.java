package com.ruoyi.biz.student.service;

import java.util.List;
import com.ruoyi.biz.student.domain.StudentInfo;


public interface IStudentInfoService 
{
    
    public StudentInfo selectStudentInfoByStudentId(Long studentId);

    
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo);

    
    public int insertStudentInfo(StudentInfo studentInfo);

    
    public int updateStudentInfo(StudentInfo studentInfo);

    
    public int deleteStudentInfoByStudentIds(Long[] studentIds);

    
    public int deleteStudentInfoByStudentId(Long studentId);
}

