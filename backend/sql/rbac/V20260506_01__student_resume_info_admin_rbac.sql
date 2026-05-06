-- 学生简历/个人信息 + 管理员岗位监管/运营统计 菜单与权限脚本
-- 说明：
-- 1) 继承 V20260414_01 的 ID 范围，从 3380 开始。
-- 2) 幂等执行：所有 INSERT 使用 WHERE NOT EXISTS。
-- 3) 包含学生端新增页面和管理员端新增页面的菜单、按钮权限、角色绑定。

SET NAMES utf8mb4;
START TRANSACTION;

-- ============================================================
-- 学生端：简历管理（页面 + 按钮）
-- ============================================================

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3380, '我的简历', 3350, 5, 'resume', 'system/student/resume/index', '', '', 1, 0, 'C', '0', '0', 'student:resume:query', 'form', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:resume:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3381, '简历新增', 3380, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:resume:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:resume:add');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3382, '简历修改', 3380, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:resume:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:resume:edit');

-- ============================================================
-- 学生端：个人信息（页面 + 按钮）
-- ============================================================

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3385, '个人信息', 3350, 6, 'info', 'system/student/info/index', '', '', 1, 0, 'C', '0', '0', 'student:info:query', 'user', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:info:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3386, '信息新增', 3385, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:info:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:info:add');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3387, '信息修改', 3385, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:info:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:info:edit');

-- ============================================================
-- 管理端：岗位监管（页面 + 按钮）
-- ============================================================

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3390, '岗位监管', 3370, 2, 'jobReview', 'system/admin/jobReview/index', '', '', 1, 0, 'C', '0', '0', 'admin:job-review:list', 'job', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:job-review:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3391, '岗位详情', 3390, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:job-review:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:job-review:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3392, '强制下架', 3390, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:job-review:force-down', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:job-review:force-down');

-- ============================================================
-- 管理端：运营统计（页面 + 按钮）
-- ============================================================

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3400, '运营统计', 3370, 3, 'stat', 'system/admin/stat/index', '', '', 1, 0, 'C', '0', '0', 'admin:stat:query', 'chart', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:stat:query');

-- ============================================================
-- 学生端：智能推荐（按钮权限，挂在岗位浏览页面下）
-- ============================================================

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3367, '岗位推荐', 3351, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:job:recommend', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:job:recommend');

-- ============================================================
-- 学生端：公告查看（页面）
-- ============================================================

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3388, '平台公告', 3350, 7, 'notice', 'system/student/notice/index', '', '', 1, 0, 'C', '0', '0', 'student:notice:list', 'notice', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:notice:list');

-- ============================================================
-- 角色绑定：学生端新页面
-- ============================================================

INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (3367, 3380, 3381, 3382, 3385, 3386, 3387, 3388)
WHERE r.role_key = 'student'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

-- ============================================================
-- 角色绑定：管理端新页面
-- ============================================================

INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (3390, 3391, 3392, 3400)
WHERE r.role_key = 'admin'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

COMMIT;
