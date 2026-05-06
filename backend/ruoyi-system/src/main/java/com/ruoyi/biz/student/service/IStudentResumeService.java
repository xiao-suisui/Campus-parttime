package com.ruoyi.biz.student.service;

import java.util.List;
import com.ruoyi.biz.student.domain.StudentResume;


public interface IStudentResumeService 
{
    
    public StudentResume selectStudentResumeByResumeId(Long resumeId);

    
    public List<StudentResume> selectStudentResumeList(StudentResume studentResume);

    
    public int insertStudentResume(StudentResume studentResume);

    
    public int updateStudentResume(StudentResume studentResume);

    
    public int deleteStudentResumeByResumeIds(Long[] resumeIds);

    
    public int deleteStudentResumeByResumeId(Long resumeId);
}


