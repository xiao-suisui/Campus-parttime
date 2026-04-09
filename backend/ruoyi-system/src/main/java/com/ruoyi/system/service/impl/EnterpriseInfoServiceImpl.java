package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.EnterpriseInfoMapper;
import com.ruoyi.system.domain.EnterpriseInfo;
import com.ruoyi.system.service.IEnterpriseInfoService;

/**
 * 浼佷笟淇℃伅Service涓氬姟灞傚鐞?
 * 
 * @author wly
 * @date 2026-04-08
 */
@Service
public class EnterpriseInfoServiceImpl implements IEnterpriseInfoService 
{
    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;

    /**
     * 鏌ヨ浼佷笟淇℃伅
     * 
     * @param enterpriseId 浼佷笟淇℃伅涓婚敭
     * @return 浼佷笟淇℃伅
     */
    @Override
    public EnterpriseInfo selectEnterpriseInfoByEnterpriseId(Long enterpriseId)
    {
        return enterpriseInfoMapper.selectEnterpriseInfoByEnterpriseId(enterpriseId);
    }

    /**
     * 鏌ヨ浼佷笟淇℃伅鍒楄〃
     * 
     * @param enterpriseInfo 浼佷笟淇℃伅
     * @return 浼佷笟淇℃伅
     */
    @Override
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoMapper.selectEnterpriseInfoList(enterpriseInfo);
    }

    /**
     * 鏂板浼佷笟淇℃伅
     * 
     * @param enterpriseInfo 浼佷笟淇℃伅
     * @return 缁撴灉
     */
    @Override
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo)
    {
        enterpriseInfo.setCreateTime(DateUtils.getNowDate());
        return enterpriseInfoMapper.insertEnterpriseInfo(enterpriseInfo);
    }

    /**
     * 淇敼浼佷笟淇℃伅
     * 
     * @param enterpriseInfo 浼佷笟淇℃伅
     * @return 缁撴灉
     */
    @Override
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo)
    {
        enterpriseInfo.setUpdateTime(DateUtils.getNowDate());
        return enterpriseInfoMapper.updateEnterpriseInfo(enterpriseInfo);
    }

    /**
     * 鎵归噺鍒犻櫎浼佷笟淇℃伅
     * 
     * @param enterpriseIds 闇€瑕佸垹闄ょ殑浼佷笟淇℃伅涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds)
    {
        return enterpriseInfoMapper.deleteEnterpriseInfoByEnterpriseIds(enterpriseIds);
    }

    /**
     * 鍒犻櫎浼佷笟淇℃伅淇℃伅
     * 
     * @param enterpriseId 浼佷笟淇℃伅涓婚敭
     * @return 缁撴灉
     */
    @Override
    public int deleteEnterpriseInfoByEnterpriseId(Long enterpriseId)
    {
        return enterpriseInfoMapper.deleteEnterpriseInfoByEnterpriseId(enterpriseId);
    }
}

