package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JobCollectionMapper;
import com.ruoyi.system.domain.JobCollection;
import com.ruoyi.system.service.IJobCollectionService;

/**
 * 宀椾綅鏀惰棌Service涓氬姟灞傚鐞?
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@Service
public class JobCollectionServiceImpl implements IJobCollectionService 
{
    @Autowired
    private JobCollectionMapper JobCollectionMapper;

    /**
     * 鏌ヨ宀椾綅鏀惰棌
     * 
     * @param collectionId 宀椾綅鏀惰棌涓婚敭
     * @return 宀椾綅鏀惰棌
     */
    @Override
    public JobCollection selectJobCollectionByCollectionId(Long collectionId)
    {
        return JobCollectionMapper.selectJobCollectionByCollectionId(collectionId);
    }

    /**
     * 鏌ヨ宀椾綅鏀惰棌鍒楄〃
     * 
     * @param JobCollection 宀椾綅鏀惰棌
     * @return 宀椾綅鏀惰棌
     */
    @Override
    public List<JobCollection> selectJobCollectionList(JobCollection JobCollection)
    {
        return JobCollectionMapper.selectJobCollectionList(JobCollection);
    }

    /**
     * 鏂板宀椾綅鏀惰棌
     * 
     * @param JobCollection 宀椾綅鏀惰棌
     * @return 缁撴灉
     */
    @Override
    public int insertJobCollection(JobCollection JobCollection)
    {
        JobCollection.setCreateTime(DateUtils.getNowDate());
        return JobCollectionMapper.insertJobCollection(JobCollection);
    }

    /**
     * 淇敼宀椾綅鏀惰棌
     * 
     * @param JobCollection 宀椾綅鏀惰棌
     * @return 缁撴灉
     */
    @Override
    public int updateJobCollection(JobCollection JobCollection)
    {
        JobCollection.setUpdateTime(DateUtils.getNowDate());
        return JobCollectionMapper.updateJobCollection(JobCollection);
    }

    /**
     * 鎵归噺鍒犻櫎宀椾綅鏀惰棌
     * 
     * @param collectionIds 闇€瑕佸垹闄ょ殑宀椾綅鏀惰棌涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteJobCollectionByCollectionIds(Long[] collectionIds)
    {
        return JobCollectionMapper.deleteJobCollectionByCollectionIds(collectionIds);
    }

    /**
     * 鍒犻櫎宀椾綅鏀惰棌淇℃伅
     * 
     * @param collectionId 宀椾綅鏀惰棌涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteJobCollectionByCollectionId(Long collectionId)
    {
        return JobCollectionMapper.deleteJobCollectionByCollectionId(collectionId);
    }
}

