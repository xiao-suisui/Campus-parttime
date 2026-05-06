package com.ruoyi.biz.recruitment.mapper;

import java.util.List;
import com.ruoyi.biz.recruitment.domain.JobCollection;


public interface JobCollectionMapper 
{
    
    public JobCollection selectJobCollectionByCollectionId(Long collectionId);

    
    public List<JobCollection> selectJobCollectionList(JobCollection JobCollection);

    
    public int insertJobCollection(JobCollection JobCollection);

    
    public int updateJobCollection(JobCollection JobCollection);

    
    public int deleteJobCollectionByCollectionId(Long collectionId);

    
    public int deleteJobCollectionByCollectionIds(Long[] collectionIds);
}

