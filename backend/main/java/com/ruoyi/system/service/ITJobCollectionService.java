package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TJobCollection;

/**
 * 岗位收藏Service接口
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
public interface ITJobCollectionService 
{
    /**
     * 查询岗位收藏
     * 
     * @param collectionId 岗位收藏主键
     * @return 岗位收藏
     */
    public TJobCollection selectTJobCollectionByCollectionId(Long collectionId);

    /**
     * 查询岗位收藏列表
     * 
     * @param tJobCollection 岗位收藏
     * @return 岗位收藏集合
     */
    public List<TJobCollection> selectTJobCollectionList(TJobCollection tJobCollection);

    /**
     * 新增岗位收藏
     * 
     * @param tJobCollection 岗位收藏
     * @return 结果
     */
    public int insertTJobCollection(TJobCollection tJobCollection);

    /**
     * 修改岗位收藏
     * 
     * @param tJobCollection 岗位收藏
     * @return 结果
     */
    public int updateTJobCollection(TJobCollection tJobCollection);

    /**
     * 批量删除岗位收藏
     * 
     * @param collectionIds 需要删除的岗位收藏主键集合
     * @return 结果
     */
    public int deleteTJobCollectionByCollectionIds(Long[] collectionIds);

    /**
     * 删除岗位收藏信息
     * 
     * @param collectionId 岗位收藏主键
     * @return 结果
     */
    public int deleteTJobCollectionByCollectionId(Long collectionId);
}
