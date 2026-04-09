package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.StudentResumeMapper;
import com.ruoyi.system.domain.StudentResume;
import com.ruoyi.system.service.IStudentResumeService;

/**
 * 瀛︾敓绠€鍘哠ervice涓氬姟灞傚鐞?
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@Service
public class StudentResumeServiceImpl implements IStudentResumeService 
{
    @Autowired
    private StudentResumeMapper StudentResumeMapper;

    /**
     * 鏌ヨ瀛︾敓绠€鍘?
     * 
     * @param resumeId 瀛︾敓绠€鍘嗕富閿?
     * @return 瀛︾敓绠€鍘?
     */
    @Override
    public StudentResume selectStudentResumeByResumeId(Long resumeId)
    {
        return StudentResumeMapper.selectStudentResumeByResumeId(resumeId);
    }

    /**
     * 鏌ヨ瀛︾敓绠€鍘嗗垪琛?
     * 
     * @param StudentResume 瀛︾敓绠€鍘?
     * @return 瀛︾敓绠€鍘?
     */
    @Override
    public List<StudentResume> selectStudentResumeList(StudentResume StudentResume)
    {
        return StudentResumeMapper.selectStudentResumeList(StudentResume);
    }

    /**
     * 鏂板瀛︾敓绠€鍘?
     * 
     * @param StudentResume 瀛︾敓绠€鍘?
     * @return 缁撴灉
     */
    @Override
    public int insertStudentResume(StudentResume StudentResume)
    {
        StudentResume.setCreateTime(DateUtils.getNowDate());
        return StudentResumeMapper.insertStudentResume(StudentResume);
    }

    /**
     * 淇敼瀛︾敓绠€鍘?
     * 
     * @param StudentResume 瀛︾敓绠€鍘?
     * @return 缁撴灉
     */
    @Override
    public int updateStudentResume(StudentResume StudentResume)
    {
        StudentResume.setUpdateTime(DateUtils.getNowDate());
        return StudentResumeMapper.updateStudentResume(StudentResume);
    }

    /**
     * 鎵归噺鍒犻櫎瀛︾敓绠€鍘?
     * 
     * @param resumeIds 闇€瑕佸垹闄ょ殑瀛︾敓绠€鍘嗕富閿?
     * @return 缁撴灉
     */
    @Override
    public int deleteStudentResumeByResumeIds(Long[] resumeIds)
    {
        return StudentResumeMapper.deleteStudentResumeByResumeIds(resumeIds);
    }

    /**
     * 鍒犻櫎瀛︾敓绠€鍘嗕俊鎭?
     * 
     * @param resumeId 瀛︾敓绠€鍘嗕富閿?
     * @return 缁撴灉
     */
    @Override
    public int deleteStudentResumeByResumeId(Long resumeId)
    {
        return StudentResumeMapper.deleteStudentResumeByResumeId(resumeId);
    }
}

