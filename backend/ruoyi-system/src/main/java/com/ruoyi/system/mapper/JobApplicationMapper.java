package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JobApplication;

/**
 * 鎶曢€掕褰昅apper鎺ュ彛
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface JobApplicationMapper 
{
    /**
     * 鏌ヨ鎶曢€掕褰?
     * 
     * @param applicationId 鎶曢€掕褰曚富閿?
     * @return 鎶曢€掕褰?
     */
    public JobApplication selectJobApplicationByApplicationId(Long applicationId);

    /**
     * 鏌ヨ鎶曢€掕褰曞垪琛?
     * 
     * @param jobApplication 鎶曢€掕褰?
     * @return 鎶曢€掕褰曢泦鍚?
     */
    public List<JobApplication> selectJobApplicationList(JobApplication jobApplication);

    /**
     * 鏂板鎶曢€掕褰?
     * 
     * @param jobApplication 鎶曢€掕褰?
     * @return 缁撴灉
     */
    public int insertJobApplication(JobApplication jobApplication);

    /**
     * 淇敼鎶曢€掕褰?
     * 
     * @param jobApplication 鎶曢€掕褰?
     * @return 缁撴灉
     */
    public int updateJobApplication(JobApplication jobApplication);

    /**
     * 鍒犻櫎鎶曢€掕褰?
     * 
     * @param applicationId 鎶曢€掕褰曚富閿?
     * @return 缁撴灉
     */
    public int deleteJobApplicationByApplicationId(Long applicationId);

    /**
     * 鎵归噺鍒犻櫎鎶曢€掕褰?
     * 
     * @param applicationIds 闇€瑕佸垹闄ょ殑鏁版嵁涓婚敭闆嗗悎
     * @return 缁撴灉
     */
    public int deleteJobApplicationByApplicationIds(Long[] applicationIds);
}

