package com.ruoyi.biz.enterprise.service;

import java.util.List;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;


public interface IEnterpriseInfoService 
{
    
    public EnterpriseInfo selectEnterpriseInfoByEnterpriseId(Long enterpriseId);

    
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo);

    
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    
    public int deleteEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds);

    
    public int deleteEnterpriseInfoByEnterpriseId(Long enterpriseId);
}

