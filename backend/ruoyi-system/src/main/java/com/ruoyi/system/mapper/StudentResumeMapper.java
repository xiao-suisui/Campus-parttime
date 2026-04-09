package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.StudentResume;

/**
 * 瀛︾敓绠€鍘哅apper鎺ュ彛
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
public interface StudentResumeMapper 
{
    /**
     * 鏌ヨ瀛︾敓绠€鍘?
     * 
     * @param resumeId 瀛︾敓绠€鍘嗕富閿?
     * @return 瀛︾敓绠€鍘?
     */
    public StudentResume selectStudentResumeByResumeId(Long resumeId);

    /**
     * 鏌ヨ瀛︾敓绠€鍘嗗垪琛?
     * 
     * @param StudentResume 瀛︾敓绠€鍘?
     * @return 瀛︾敓绠€鍘嗛泦鍚?
     */
    public List<StudentResume> selectStudentResumeList(StudentResume StudentResume);

    /**
     * 鏂板瀛︾敓绠€鍘?
     * 
     * @param StudentResume 瀛︾敓绠€鍘?
     * @return 缁撴灉
     */
    public int insertStudentResume(StudentResume StudentResume);

    /**
     * 淇敼瀛︾敓绠€鍘?
     * 
     * @param StudentResume 瀛︾敓绠€鍘?
     * @return 缁撴灉
     */
    public int updateStudentResume(StudentResume StudentResume);

    /**
     * 鍒犻櫎瀛︾敓绠€鍘?
     * 
     * @param resumeId 瀛︾敓绠€鍘嗕富閿?
     * @return 缁撴灉
     */
    public int deleteStudentResumeByResumeId(Long resumeId);

    /**
     * 鎵归噺鍒犻櫎瀛︾敓绠€鍘?
     * 
     * @param resumeIds 闇€瑕佸垹闄ょ殑鏁版嵁涓婚敭闆嗗悎
     * @return 缁撴灉
     */
    public int deleteStudentResumeByResumeIds(Long[] resumeIds);
}

