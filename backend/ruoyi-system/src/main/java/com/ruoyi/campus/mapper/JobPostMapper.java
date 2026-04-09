package com.ruoyi.campus.mapper;

import java.util.List;
import com.ruoyi.campus.domain.JobPost;

/**
 * 宀椾綅淇℃伅Mapper鎺ュ彛
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface JobPostMapper 
{
    /**
     * 鏌ヨ宀椾綅淇℃伅
     * 
     * @param postId 宀椾綅淇℃伅涓婚敭
     * @return 宀椾綅淇℃伅
     */
    public JobPost selectJobPostByPostId(Long postId);

    /**
     * 鏌ヨ宀椾綅淇℃伅鍒楄〃
     * 
     * @param jobPost 宀椾綅淇℃伅
     * @return 宀椾綅淇℃伅闆嗗悎
     */
    public List<JobPost> selectJobPostList(JobPost jobPost);

    /**
     * 鏂板宀椾綅淇℃伅
     * 
     * @param jobPost 宀椾綅淇℃伅
     * @return 缁撴灉
     */
    public int insertJobPost(JobPost jobPost);

    /**
     * 淇敼宀椾綅淇℃伅
     * 
     * @param jobPost 宀椾綅淇℃伅
     * @return 缁撴灉
     */
    public int updateJobPost(JobPost jobPost);

    /**
     * 鍒犻櫎宀椾綅淇℃伅
     * 
     * @param postId 宀椾綅淇℃伅涓婚敭
     * @return 缁撴灉
     */
    public int deleteJobPostByPostId(Long postId);

    /**
     * 鎵归噺鍒犻櫎宀椾綅淇℃伅
     * 
     * @param postIds 闇€瑕佸垹闄ょ殑鏁版嵁涓婚敭闆嗗悎
     * @return 缁撴灉
     */
    public int deleteJobPostByPostIds(Long[] postIds);
}

