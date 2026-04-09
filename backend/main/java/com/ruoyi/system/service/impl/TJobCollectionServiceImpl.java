package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TJobCollectionMapper;
import com.ruoyi.system.domain.TJobCollection;
import com.ruoyi.system.service.ITJobCollectionService;

/**
 * 岗位收藏Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@Service
public class TJobCollectionServiceImpl implements ITJobCollectionService 
{
    @Autowired
    private TJobCollectionMapper tJobCollectionMapper;

    /**
     * 查询岗位收藏
     * 
     * @param collectionId 岗位收藏主键
     * @return 岗位收藏
     */
    @Override
    public TJobCollection selectTJobCollectionByCollectionId(Long collectionId)
    {
        return tJobCollectionMapper.selectTJobCollectionByCollectionId(collectionId);
    }

    /**
     * 查询岗位收藏列表
     * 
     * @param tJobCollection 岗位收藏
     * @return 岗位收藏
     */
    @Override
    public List<TJobCollection> selectTJobCollectionList(TJobCollection tJobCollection)
    {
        return tJobCollectionMapper.selectTJobCollectionList(tJobCollection);
    }

    /**
     * 新增岗位收藏
     * 
     * @param tJobCollection 岗位收藏
     * @return 结果
     */
    @Override
    public int insertTJobCollection(TJobCollection tJobCollection)
    {
        tJobCollection.setCreateTime(DateUtils.getNowDate());
        return tJobCollectionMapper.insertTJobCollection(tJobCollection);
    }

    /**
     * 修改岗位收藏
     * 
     * @param tJobCollection 岗位收藏
     * @return 结果
     */
    @Override
    public int updateTJobCollection(TJobCollection tJobCollection)
    {
        tJobCollection.setUpdateTime(DateUtils.getNowDate());
        return tJobCollectionMapper.updateTJobCollection(tJobCollection);
    }

    /**
     * 批量删除岗位收藏
     * 
     * @param collectionIds 需要删除的岗位收藏主键
     * @return 结果
     */
    @Override
    public int deleteTJobCollectionByCollectionIds(Long[] collectionIds)
    {
        return tJobCollectionMapper.deleteTJobCollectionByCollectionIds(collectionIds);
    }

    /**
     * 删除岗位收藏信息
     * 
     * @param collectionId 岗位收藏主键
     * @return 结果
     */
    @Override
    public int deleteTJobCollectionByCollectionId(Long collectionId)
    {
        return tJobCollectionMapper.deleteTJobCollectionByCollectionId(collectionId);
    }
}
