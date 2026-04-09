package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.StudentInfoMapper;
import com.ruoyi.system.domain.StudentInfo;
import com.ruoyi.system.service.IStudentInfoService;

/**
 * 瀛︾敓淇℃伅Service涓氬姟灞傚鐞?
 * 
 * @author wly
 * @date 2026-04-08
 */
@Service
public class StudentInfoServiceImpl implements IStudentInfoService 
{
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    /**
     * 鏌ヨ瀛︾敓淇℃伅
     * 
     * @param studentId 瀛︾敓淇℃伅涓婚敭
     * @return 瀛︾敓淇℃伅
     */
    @Override
    public StudentInfo selectStudentInfoByStudentId(Long studentId)
    {
        return studentInfoMapper.selectStudentInfoByStudentId(studentId);
    }

    /**
     * 鏌ヨ瀛︾敓淇℃伅鍒楄〃
     * 
     * @param studentInfo 瀛︾敓淇℃伅
     * @return 瀛︾敓淇℃伅
     */
    @Override
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo)
    {
        return studentInfoMapper.selectStudentInfoList(studentInfo);
    }

    /**
     * 鏂板瀛︾敓淇℃伅
     * 
     * @param studentInfo 瀛︾敓淇℃伅
     * @return 缁撴灉
     */
    @Override
    public int insertStudentInfo(StudentInfo studentInfo)
    {
        studentInfo.setCreateTime(DateUtils.getNowDate());
        return studentInfoMapper.insertStudentInfo(studentInfo);
    }

    /**
     * 淇敼瀛︾敓淇℃伅
     * 
     * @param studentInfo 瀛︾敓淇℃伅
     * @return 缁撴灉
     */
    @Override
    public int updateStudentInfo(StudentInfo studentInfo)
    {
        studentInfo.setUpdateTime(DateUtils.getNowDate());
        return studentInfoMapper.updateStudentInfo(studentInfo);
    }

    /**
     * 鎵归噺鍒犻櫎瀛︾敓淇℃伅
     * 
     * @param studentIds 闇€瑕佸垹闄ょ殑瀛︾敓淇℃伅涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteStudentInfoByStudentIds(Long[] studentIds)
    {
        return studentInfoMapper.deleteStudentInfoByStudentIds(studentIds);
    }

    /**
     * 鍒犻櫎瀛︾敓淇℃伅淇℃伅
     * 
     * @param studentId 瀛︾敓淇℃伅涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteStudentInfoByStudentId(Long studentId)
    {
        return studentInfoMapper.deleteStudentInfoByStudentId(studentId);
    }
}

