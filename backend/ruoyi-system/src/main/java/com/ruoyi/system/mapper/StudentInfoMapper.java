package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.StudentInfo;

/**
 * 瀛︾敓淇℃伅Mapper鎺ュ彛
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface StudentInfoMapper 
{
    /**
     * 鏌ヨ瀛︾敓淇℃伅
     * 
     * @param studentId 瀛︾敓淇℃伅涓婚敭
     * @return 瀛︾敓淇℃伅
     */
    public StudentInfo selectStudentInfoByStudentId(Long studentId);

    /**
     * 鏌ヨ瀛︾敓淇℃伅鍒楄〃
     * 
     * @param studentInfo 瀛︾敓淇℃伅
     * @return 瀛︾敓淇℃伅闆嗗悎
     */
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo);

    /**
     * 鏂板瀛︾敓淇℃伅
     * 
     * @param studentInfo 瀛︾敓淇℃伅
     * @return 缁撴灉
     */
    public int insertStudentInfo(StudentInfo studentInfo);

    /**
     * 淇敼瀛︾敓淇℃伅
     * 
     * @param studentInfo 瀛︾敓淇℃伅
     * @return 缁撴灉
     */
    public int updateStudentInfo(StudentInfo studentInfo);

    /**
     * 鍒犻櫎瀛︾敓淇℃伅
     * 
     * @param studentId 瀛︾敓淇℃伅涓婚敭
     * @return 缁撴灉
     */
    public int deleteStudentInfoByStudentId(Long studentId);

    /**
     * 鎵归噺鍒犻櫎瀛︾敓淇℃伅
     * 
     * @param studentIds 闇€瑕佸垹闄ょ殑鏁版嵁涓婚敭闆嗗悎
     * @return 缁撴灉
     */
    public int deleteStudentInfoByStudentIds(Long[] studentIds);
}

