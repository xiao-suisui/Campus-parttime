package com.ruoyi.biz.student.mapper;

import java.util.List;
import com.ruoyi.biz.student.domain.StudentInfo;


public interface StudentInfoMapper 
{
    
    public StudentInfo selectStudentInfoByStudentId(Long studentId);

    
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo);

    
    public int insertStudentInfo(StudentInfo studentInfo);

    
    public int updateStudentInfo(StudentInfo studentInfo);

    
    public int deleteStudentInfoByStudentId(Long studentId);

    
    public int deleteStudentInfoByStudentIds(Long[] studentIds);
}

