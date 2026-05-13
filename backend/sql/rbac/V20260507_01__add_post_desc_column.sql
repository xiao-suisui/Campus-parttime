-- 为 t_job_post 表添加工作描述字段
-- 说明：岗位缺少工作描述字段，导致学生无法了解具体工作内容

SET NAMES utf8mb4;

ALTER TABLE t_job_post
  ADD COLUMN post_desc text COMMENT '工作描述' AFTER work_time_desc;
