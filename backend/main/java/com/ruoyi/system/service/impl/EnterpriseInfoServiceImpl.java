package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.EnterpriseInfoMapper;
import com.ruoyi.system.domain.EnterpriseInfo;
import com.ruoyi.system.service.IEnterpriseInfoService;

/**
 * 企业信息Service业务层处理
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
     * 查询企业信息
     * 
     * @param enterpriseId 企业信息主键
     * @return 企业信息
     */
    @Override
    public EnterpriseInfo selectEnterpriseInfoByEnterpriseId(Long enterpriseId)
    {
        return enterpriseInfoMapper.selectEnterpriseInfoByEnterpriseId(enterpriseId);
    }

    /**
     * 查询企业信息列表
     * 
     * @param enterpriseInfo 企业信息
     * @return 企业信息
     */
    @Override
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoMapper.selectEnterpriseInfoList(enterpriseInfo);
    }

    /**
     * 新增企业信息
     * 
     * @param enterpriseInfo 企业信息
     * @return 结果
     */
    @Override
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo)
    {
        enterpriseInfo.setCreateTime(DateUtils.getNowDate());
        return enterpriseInfoMapper.insertEnterpriseInfo(enterpriseInfo);
    }

    /**
     * 修改企业信息
     * 
     * @param enterpriseInfo 企业信息
     * @return 结果
     */
    @Override
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo)
    {
        enterpriseInfo.setUpdateTime(DateUtils.getNowDate());
        return enterpriseInfoMapper.updateEnterpriseInfo(enterpriseInfo);
    }

    /**
     * 批量删除企业信息
     * 
     * @param enterpriseIds 需要删除的企业信息主键
     * @return 结果
     */
    @Override
    public int deleteEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds)
    {
        return enterpriseInfoMapper.deleteEnterpriseInfoByEnterpriseIds(enterpriseIds);
    }

    /**
     * 删除企业信息信息
     * 
     * @param enterpriseId 企业信息主键
     * @return 结果
     */
    @Override
    public int deleteEnterpriseInfoByEnterpriseId(Long enterpriseId)
    {
        return enterpriseInfoMapper.deleteEnterpriseInfoByEnterpriseId(enterpriseId);
    }
}
