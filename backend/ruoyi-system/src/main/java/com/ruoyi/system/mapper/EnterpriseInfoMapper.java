package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.EnterpriseInfo;

/**
 * 浼佷笟淇℃伅Mapper鎺ュ彛
 * 
 * @author wly
 * @date 2026-04-08
 */
public interface EnterpriseInfoMapper 
{
    /**
     * 鏌ヨ浼佷笟淇℃伅
     * 
     * @param enterpriseId 浼佷笟淇℃伅涓婚敭
     * @return 浼佷笟淇℃伅
     */
    public EnterpriseInfo selectEnterpriseInfoByEnterpriseId(Long enterpriseId);

    /**
     * 鏌ヨ浼佷笟淇℃伅鍒楄〃
     * 
     * @param enterpriseInfo 浼佷笟淇℃伅
     * @return 浼佷笟淇℃伅闆嗗悎
     */
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo);

    /**
     * 鏂板浼佷笟淇℃伅
     * 
     * @param enterpriseInfo 浼佷笟淇℃伅
     * @return 缁撴灉
     */
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    /**
     * 淇敼浼佷笟淇℃伅
     * 
     * @param enterpriseInfo 浼佷笟淇℃伅
     * @return 缁撴灉
     */
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    /**
     * 鍒犻櫎浼佷笟淇℃伅
     * 
     * @param enterpriseId 浼佷笟淇℃伅涓婚敭
     * @return 缁撴灉
     */
    public int deleteEnterpriseInfoByEnterpriseId(Long enterpriseId);

    /**
     * 鎵归噺鍒犻櫎浼佷笟淇℃伅
     * 
     * @param enterpriseIds 闇€瑕佸垹闄ょ殑鏁版嵁涓婚敭闆嗗悎
     * @return 缁撴灉
     */
    public int deleteEnterpriseInfoByEnterpriseIds(Long[] enterpriseIds);
}

