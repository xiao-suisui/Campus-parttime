package com.ruoyi.biz.student.mapper;

import java.util.List;
import com.ruoyi.biz.student.domain.StudentResume;


public interface StudentResumeMapper 
{
    
    public StudentResume selectStudentResumeByResumeId(Long resumeId);

    
    public List<StudentResume> selectStudentResumeList(StudentResume StudentResume);

    
    public int insertStudentResume(StudentResume StudentResume);

    
    public int updateStudentResume(StudentResume StudentResume);

    
    public int deleteStudentResumeByResumeId(Long resumeId);

    
    public int deleteStudentResumeByResumeIds(Long[] resumeIds);
}

