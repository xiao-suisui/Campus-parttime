package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.JobApplication;

/**
 * 鎶曢€掕褰昐ervice鎺ュ彛
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface IJobApplicationService 
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
     * 鎵归噺鍒犻櫎鎶曢€掕褰?
     * 
     * @param applicationIds 闇€瑕佸垹闄ょ殑鎶曢€掕褰曚富閿泦鍚?
     * @return 缁撴灉
     */
    public int deleteJobApplicationByApplicationIds(Long[] applicationIds);

    /**
     * 鍒犻櫎鎶曢€掕褰曚俊鎭?
     * 
     * @param applicationId 鎶曢€掕褰曚富閿?
     * @return 缁撴灉
     */
    public int deleteJobApplicationByApplicationId(Long applicationId);

    /**
     * 浼佷笟褰曠敤鎶曢€掕褰曪紙0->1锛?
     * 
     * @param applicationId 鎶曢€掕褰曚富閿?
     * @param enterpriseRemark 浼佷笟澶囨敞
     * @return 缁撴灉
     */
    public int hireJobApplication(Long applicationId, String enterpriseRemark);

    /**
     * 浼佷笟鏍囪鎶曢€掑畬鎴愶紙1->2锛?
     * 
     * @param applicationId 鎶曢€掕褰曚富閿?
     * @param enterpriseRemark 浼佷笟澶囨敞
     * @return 缁撴灉
     */
    public int completeJobApplication(Long applicationId, String enterpriseRemark);
}

