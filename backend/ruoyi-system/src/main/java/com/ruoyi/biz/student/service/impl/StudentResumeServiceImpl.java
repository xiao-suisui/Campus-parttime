package com.ruoyi.biz.student.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.student.mapper.StudentResumeMapper;
import com.ruoyi.biz.student.domain.StudentResume;
import com.ruoyi.biz.student.service.IStudentResumeService;


@Service
public class StudentResumeServiceImpl implements IStudentResumeService 
{
    @Autowired
    private StudentResumeMapper studentResumeMapper;

    
    @Override
    public StudentResume selectStudentResumeByResumeId(Long resumeId)
    {
        return studentResumeMapper.selectStudentResumeByResumeId(resumeId);
    }

    
    @Override
    public List<StudentResume> selectStudentResumeList(StudentResume studentResume)
    {
        return studentResumeMapper.selectStudentResumeList(studentResume);
    }

    
    @Override
    public int insertStudentResume(StudentResume studentResume)
    {
        studentResume.setCreateTime(DateUtils.getNowDate());
        return studentResumeMapper.insertStudentResume(studentResume);
    }

    
    @Override
    public int updateStudentResume(StudentResume studentResume)
    {
        studentResume.setUpdateTime(DateUtils.getNowDate());
        return studentResumeMapper.updateStudentResume(studentResume);
    }

    
    @Override
    public int deleteStudentResumeByResumeIds(Long[] resumeIds)
    {
        return studentResumeMapper.deleteStudentResumeByResumeIds(resumeIds);
    }

    
    @Override
    public int deleteStudentResumeByResumeId(Long resumeId)
    {
        return studentResumeMapper.deleteStudentResumeByResumeId(resumeId);
    }
}


