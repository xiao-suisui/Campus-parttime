package com.ruoyi.biz.enterprise.mapper;

import java.util.List;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;


public interface EnterpriseInfoMapper 
{
    
    public EnterpriseInfo selectEnterpriseInfoByEnterpriseId(Long enterpriseId);

    
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo);

    
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    
    public int deleteEnterpriseInfoByEnterpriseId(Long enterpriseId);

    
    public int deleteEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds);
}

