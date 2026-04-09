package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.EnterpriseInfo;

/**
 * 企业信息Mapper接口
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface EnterpriseInfoMapper 
{
    /**
     * 查询企业信息
     * 
     * @param enterpriseId 企业信息主键
     * @return 企业信息
     */
    public EnterpriseInfo selectEnterpriseInfoByEnterpriseId(Long enterpriseId);

    /**
     * 查询企业信息列表
     * 
     * @param enterpriseInfo 企业信息
     * @return 企业信息集合
     */
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo);

    /**
     * 新增企业信息
     * 
     * @param enterpriseInfo 企业信息
     * @return 结果
     */
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    /**
     * 修改企业信息
     * 
     * @param enterpriseInfo 企业信息
     * @return 结果
     */
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    /**
     * 删除企业信息
     * 
     * @param enterpriseId 企业信息主键
     * @return 结果
     */
    public int deleteEnterpriseInfoByEnterpriseId(Long enterpriseId);

    /**
     * 批量删除企业信息
     * 
     * @param enterpriseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds);
}
