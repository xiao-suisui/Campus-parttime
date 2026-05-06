package com.ruoyi.biz.recruitment.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.recruitment.mapper.JobCollectionMapper;
import com.ruoyi.biz.recruitment.domain.JobCollection;
import com.ruoyi.biz.recruitment.service.IJobCollectionService;


@Service
public class JobCollectionServiceImpl implements IJobCollectionService 
{
    @Autowired
    private JobCollectionMapper jobCollectionMapper;

    
    @Override
    public JobCollection selectJobCollectionByCollectionId(Long collectionId)
    {
        return jobCollectionMapper.selectJobCollectionByCollectionId(collectionId);
    }

    
    @Override
    public List<JobCollection> selectJobCollectionList(JobCollection jobCollection)
    {
        return jobCollectionMapper.selectJobCollectionList(jobCollection);
    }

    
    @Override
    public int insertJobCollection(JobCollection jobCollection)
    {
        jobCollection.setCreateTime(DateUtils.getNowDate());
        return jobCollectionMapper.insertJobCollection(jobCollection);
    }

    
    @Override
    public int updateJobCollection(JobCollection jobCollection)
    {
        jobCollection.setUpdateTime(DateUtils.getNowDate());
        return jobCollectionMapper.updateJobCollection(jobCollection);
    }

    
    @Override
    public int deleteJobCollectionByCollectionIds(Long[] collectionIds)
    {
        return jobCollectionMapper.deleteJobCollectionByCollectionIds(collectionIds);
    }

    
    @Override
    public int deleteJobCollectionByCollectionId(Long collectionId)
    {
        return jobCollectionMapper.deleteJobCollectionByCollectionId(collectionId);
    }
}


