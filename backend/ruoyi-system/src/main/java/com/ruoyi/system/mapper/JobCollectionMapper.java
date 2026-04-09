package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JobCollection;

/**
 * 宀椾綅鏀惰棌Mapper鎺ュ彛
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
public interface JobCollectionMapper 
{
    /**
     * 鏌ヨ宀椾綅鏀惰棌
     * 
     * @param collectionId 宀椾綅鏀惰棌涓婚敭
     * @return 宀椾綅鏀惰棌
     */
    public JobCollection selectJobCollectionByCollectionId(Long collectionId);

    /**
     * 鏌ヨ宀椾綅鏀惰棌鍒楄〃
     * 
     * @param JobCollection 宀椾綅鏀惰棌
     * @return 宀椾綅鏀惰棌闆嗗悎
     */
    public List<JobCollection> selectJobCollectionList(JobCollection JobCollection);

    /**
     * 鏂板宀椾綅鏀惰棌
     * 
     * @param JobCollection 宀椾綅鏀惰棌
     * @return 缁撴灉
     */
    public int insertJobCollection(JobCollection JobCollection);

    /**
     * 淇敼宀椾綅鏀惰棌
     * 
     * @param JobCollection 宀椾綅鏀惰棌
     * @return 缁撴灉
     */
    public int updateJobCollection(JobCollection JobCollection);

    /**
     * 鍒犻櫎宀椾綅鏀惰棌
     * 
     * @param collectionId 宀椾綅鏀惰棌涓婚敭
     * @return 缁撴灉
     */
    public int deleteJobCollectionByCollectionId(Long collectionId);

    /**
     * 鎵归噺鍒犻櫎宀椾綅鏀惰棌
     * 
     * @param collectionIds 闇€瑕佸垹闄ょ殑鏁版嵁涓婚敭闆嗗悎
     * @return 缁撴灉
     */
    public int deleteJobCollectionByCollectionIds(Long[] collectionIds);
}

