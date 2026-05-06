package com.ruoyi.biz.enterprise.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.enterprise.mapper.EnterpriseInfoMapper;
import com.ruoyi.biz.enterprise.domain.EnterpriseInfo;
import com.ruoyi.biz.enterprise.service.IEnterpriseInfoService;


@Service
public class EnterpriseInfoServiceImpl implements IEnterpriseInfoService 
{
    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;

    
    @Override
    public EnterpriseInfo selectEnterpriseInfoByEnterpriseId(Long enterpriseId)
    {
        return enterpriseInfoMapper.selectEnterpriseInfoByEnterpriseId(enterpriseId);
    }

    
    @Override
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoMapper.selectEnterpriseInfoList(enterpriseInfo);
    }

    
    @Override
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo)
    {
        enterpriseInfo.setCreateTime(DateUtils.getNowDate());
        return enterpriseInfoMapper.insertEnterpriseInfo(enterpriseInfo);
    }

    
    @Override
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo)
    {
        enterpriseInfo.setUpdateTime(DateUtils.getNowDate());
        return enterpriseInfoMapper.updateEnterpriseInfo(enterpriseInfo);
    }

    
    @Override
    public int deleteEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds)
    {
        return enterpriseInfoMapper.deleteEnterpriseInfoByEnterpriseIds(enterpriseIds);
    }

    
    @Override
    public int deleteEnterpriseInfoByEnterpriseId(Long enterpriseId)
    {
        return enterpriseInfoMapper.deleteEnterpriseInfoByEnterpriseId(enterpriseId);
    }
}

