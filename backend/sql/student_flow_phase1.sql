-- 学生端全流程 Phase-1（后端先行）数据库增量脚本
-- 目标：岗位浏览、收藏、投递、状态查询、企业完成后互评
-- 说明：
-- 1) 本脚本不修改菜单表（按你的要求菜单由你维护）
-- 2) 本脚本仅补索引与约束，表结构字段基本复用现有设计
-- 3) 执行前请先在测试库验证

SET NAMES utf8mb4;

START TRANSACTION;

-- 1) 岗位列表与详情查询索引（学生端高频）
ALTER TABLE t_job_post
  ADD INDEX idx_post_student_list (post_status, del_flag, deadline_time, create_time);

-- 2) 收藏查询索引（按学生维度）
ALTER TABLE t_job_collection
  ADD INDEX idx_collection_student_time (student_id, collection_time, collection_id);

-- 3) 投递查询索引（按学生维度与状态维度）
ALTER TABLE t_job_application
  ADD INDEX idx_application_student_status (student_id, application_status, apply_time, application_id),
  ADD INDEX idx_application_post_status (post_id, application_status, process_time, application_id);

-- 4) 互评查询索引（学生互评列表）
ALTER TABLE t_job_evaluation
  ADD INDEX idx_evaluation_student_type (student_id, evaluation_type, evaluation_time, evaluation_id);

-- 5) 学生简历可用性查询索引
ALTER TABLE t_student_resume
  ADD INDEX idx_resume_student_status (student_id, status, del_flag, resume_id);

-- 6) 学生身份有效性查询索引（可选，主键场景已足够时可不加）
ALTER TABLE t_student_info
  ADD INDEX idx_student_valid (student_id, del_flag);

-- 验证建议：
-- EXPLAIN SELECT * FROM t_job_post WHERE post_status='1' AND del_flag='0' AND deadline_time >= NOW() ORDER BY create_time DESC LIMIT 20;
-- EXPLAIN SELECT * FROM t_job_application WHERE student_id=? ORDER BY apply_time DESC LIMIT 20;
-- EXPLAIN SELECT * FROM t_job_collection WHERE student_id=? ORDER BY collection_time DESC LIMIT 20;

-- 回滚草案（按需执行）
-- ALTER TABLE t_job_post DROP INDEX idx_post_student_list;
-- ALTER TABLE t_job_collection DROP INDEX idx_collection_student_time;
-- ALTER TABLE t_job_application DROP INDEX idx_application_student_status, DROP INDEX idx_application_post_status;
-- ALTER TABLE t_job_evaluation DROP INDEX idx_evaluation_student_type;
-- ALTER TABLE t_student_resume DROP INDEX idx_resume_student_status;
-- ALTER TABLE t_student_info DROP INDEX idx_student_valid;

-- 先检查再提交
-- COMMIT;
ROLLBACK;

