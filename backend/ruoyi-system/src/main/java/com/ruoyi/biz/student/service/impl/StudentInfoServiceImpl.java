package com.ruoyi.biz.student.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.student.mapper.StudentInfoMapper;
import com.ruoyi.biz.student.domain.StudentInfo;
import com.ruoyi.biz.student.service.IStudentInfoService;


@Service
public class StudentInfoServiceImpl implements IStudentInfoService 
{
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    
    @Override
    public StudentInfo selectStudentInfoByStudentId(Long studentId)
    {
        return studentInfoMapper.selectStudentInfoByStudentId(studentId);
    }

    
    @Override
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo)
    {
        return studentInfoMapper.selectStudentInfoList(studentInfo);
    }

    
    @Override
    public int insertStudentInfo(StudentInfo studentInfo)
    {
        studentInfo.setCreateTime(DateUtils.getNowDate());
        return studentInfoMapper.insertStudentInfo(studentInfo);
    }

    
    @Override
    public int updateStudentInfo(StudentInfo studentInfo)
    {
        studentInfo.setUpdateTime(DateUtils.getNowDate());
        return studentInfoMapper.updateStudentInfo(studentInfo);
    }

    
    @Override
    public int deleteStudentInfoByStudentIds(Long[] studentIds)
    {
        return studentInfoMapper.deleteStudentInfoByStudentIds(studentIds);
    }

    
    @Override
    public int deleteStudentInfoByStudentId(Long studentId)
    {
        return studentInfoMapper.deleteStudentInfoByStudentId(studentId);
    }
}

