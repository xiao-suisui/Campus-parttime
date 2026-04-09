package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TStudentResume;

/**
 * 学生简历Service接口
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
public interface ITStudentResumeService 
{
    /**
     * 查询学生简历
     * 
     * @param resumeId 学生简历主键
     * @return 学生简历
     */
    public TStudentResume selectTStudentResumeByResumeId(Long resumeId);

    /**
     * 查询学生简历列表
     * 
     * @param tStudentResume 学生简历
     * @return 学生简历集合
     */
    public List<TStudentResume> selectTStudentResumeList(TStudentResume tStudentResume);

    /**
     * 新增学生简历
     * 
     * @param tStudentResume 学生简历
     * @return 结果
     */
    public int insertTStudentResume(TStudentResume tStudentResume);

    /**
     * 修改学生简历
     * 
     * @param tStudentResume 学生简历
     * @return 结果
     */
    public int updateTStudentResume(TStudentResume tStudentResume);

    /**
     * 批量删除学生简历
     * 
     * @param resumeIds 需要删除的学生简历主键集合
     * @return 结果
     */
    public int deleteTStudentResumeByResumeIds(Long[] resumeIds);

    /**
     * 删除学生简历信息
     * 
     * @param resumeId 学生简历主键
     * @return 结果
     */
    public int deleteTStudentResumeByResumeId(Long resumeId);
}
