package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TStudentResumeMapper;
import com.ruoyi.system.domain.TStudentResume;
import com.ruoyi.system.service.ITStudentResumeService;

/**
 * 学生简历Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@Service
public class TStudentResumeServiceImpl implements ITStudentResumeService 
{
    @Autowired
    private TStudentResumeMapper tStudentResumeMapper;

    /**
     * 查询学生简历
     * 
     * @param resumeId 学生简历主键
     * @return 学生简历
     */
    @Override
    public TStudentResume selectTStudentResumeByResumeId(Long resumeId)
    {
        return tStudentResumeMapper.selectTStudentResumeByResumeId(resumeId);
    }

    /**
     * 查询学生简历列表
     * 
     * @param tStudentResume 学生简历
     * @return 学生简历
     */
    @Override
    public List<TStudentResume> selectTStudentResumeList(TStudentResume tStudentResume)
    {
        return tStudentResumeMapper.selectTStudentResumeList(tStudentResume);
    }

    /**
     * 新增学生简历
     * 
     * @param tStudentResume 学生简历
     * @return 结果
     */
    @Override
    public int insertTStudentResume(TStudentResume tStudentResume)
    {
        tStudentResume.setCreateTime(DateUtils.getNowDate());
        return tStudentResumeMapper.insertTStudentResume(tStudentResume);
    }

    /**
     * 修改学生简历
     * 
     * @param tStudentResume 学生简历
     * @return 结果
     */
    @Override
    public int updateTStudentResume(TStudentResume tStudentResume)
    {
        tStudentResume.setUpdateTime(DateUtils.getNowDate());
        return tStudentResumeMapper.updateTStudentResume(tStudentResume);
    }

    /**
     * 批量删除学生简历
     * 
     * @param resumeIds 需要删除的学生简历主键
     * @return 结果
     */
    @Override
    public int deleteTStudentResumeByResumeIds(Long[] resumeIds)
    {
        return tStudentResumeMapper.deleteTStudentResumeByResumeIds(resumeIds);
    }

    /**
     * 删除学生简历信息
     * 
     * @param resumeId 学生简历主键
     * @return 结果
     */
    @Override
    public int deleteTStudentResumeByResumeId(Long resumeId)
    {
        return tStudentResumeMapper.deleteTStudentResumeByResumeId(resumeId);
    }
}
