package com.ruoyi.biz.recruitment.service;

import java.util.List;
import com.ruoyi.biz.recruitment.domain.JobCollection;


public interface IJobCollectionService 
{
    
    public JobCollection selectJobCollectionByCollectionId(Long collectionId);

    
    public List<JobCollection> selectJobCollectionList(JobCollection jobCollection);

    
    public int insertJobCollection(JobCollection jobCollection);

    
    public int updateJobCollection(JobCollection jobCollection);

    
    public int deleteJobCollectionByCollectionIds(Long[] collectionIds);

    
    public int deleteJobCollectionByCollectionId(Long collectionId);
}


