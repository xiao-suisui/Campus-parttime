-- 角色分治菜单与权限脚本（新建：学生/企业/管理员）
-- 说明：
-- 1) 本脚本只负责新建三端菜单与权限，不包含历史数据清理。
-- 2) 清理旧 `campus` 业务菜单/权限前，请先执行 `V20260415_00__legacy_menu_cleanup.sql`。
-- 3) 幂等执行：角色、菜单、按钮、角色菜单绑定均使用 not exists。
-- 4) 学生简历模块后端接口尚未完成，本脚本暂不创建 student:resume:* 菜单与权限。

SET NAMES utf8mb4;
START TRANSACTION;

-- 1) 业务角色（不存在时创建）
INSERT INTO sys_role (
  role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly,
  status, del_flag, create_by, create_time, remark
)
SELECT '企业用户', 'enterprise', 10, '1', 1, 1, '0', '0', 'admin', SYSDATE(), '兼职平台-企业角色'
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key = 'enterprise');

INSERT INTO sys_role (
  role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly,
  status, del_flag, create_by, create_time, remark
)
SELECT '学生用户', 'student', 11, '1', 1, 1, '0', '0', 'admin', SYSDATE(), '兼职平台-学生角色'
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key = 'student');

-- 2) 企业端菜单与按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3300, '企业端业务', 0, 20, 'enterprise-biz', 'Layout', '', '',
  1, 0, 'M', '0', '0', '', 'peoples', 'admin', SYSDATE(), '', NULL, '企业业务目录'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3300 OR path = 'enterprise-biz');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3301, '企业资料', 3300, 1, 'profile', 'system/enterprise/profile/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:profile:query', 'user', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:profile:query');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3302, '岗位管理', 3300, 2, 'post', 'system/enterprise/post/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:post:list', 'clipboard', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:list');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3303, '投递处理', 3300, 3, 'application', 'system/enterprise/application/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:application:list', 'form', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:application:list');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3304, '互评管理', 3300, 4, 'evaluation', 'system/enterprise/evaluation/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:evaluation:list', 'message', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:evaluation:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3311, '资料新增', 3301, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:profile:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:profile:add');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3312, '资料修改', 3301, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:profile:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:profile:edit');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3313, '提交审核', 3301, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:profile:submit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:profile:submit');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3321, '岗位详情', 3302, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:query');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3322, '岗位新增', 3302, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:add');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3323, '岗位编辑', 3302, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:edit');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3324, '岗位上架', 3302, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:up', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:up');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3325, '岗位下架', 3302, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:down', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:down');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3331, '投递详情', 3303, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:application:query');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3332, '录用投递', 3303, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:hire', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:application:hire');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3333, '标记完成', 3303, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:complete', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:application:complete');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3341, '互评新增', 3304, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:evaluation:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:evaluation:add');

-- 3) 学生端菜单与按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3350, '学生端业务', 0, 21, 'student-biz', 'Layout', '', '',
  1, 0, 'M', '0', '0', '', 'user', 'admin', SYSDATE(), '', NULL, '学生业务目录'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3350 OR path = 'student-biz');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3351, '岗位浏览', 3350, 1, 'post', 'system/student/post/index', '', '', 1, 0, 'C', '0', '0', 'student:job:list', 'search', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:job:list');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3352, '岗位收藏', 3350, 2, 'collection', 'system/student/collection/index', '', '', 1, 0, 'C', '0', '0', 'student:collection:list', 'star', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:collection:list');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3353, '岗位投递', 3350, 3, 'application', 'system/student/application/index', '', '', 1, 0, 'C', '0', '0', 'student:application:list', 'edit', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:application:list');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3354, '互评记录', 3350, 4, 'evaluation', 'system/student/evaluation/index', '', '', 1, 0, 'C', '0', '0', 'student:evaluation:list', 'message', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:evaluation:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3361, '岗位详情', 3351, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:job:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:job:query');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3362, '收藏新增', 3352, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:collection:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:collection:add');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3363, '收藏取消', 3352, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:collection:remove', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:collection:remove');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3364, '投递详情', 3353, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:application:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:application:query');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3365, '投递新增', 3353, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:application:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:application:add');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3366, '互评新增', 3354, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:evaluation:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:evaluation:add');

-- 4) 管理端菜单与按钮（仅审核与查看）
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3370, '平台审核', 0, 22, 'admin-audit', 'Layout', '', '',
  1, 0, 'M', '0', '0', '', 'example', 'admin', SYSDATE(), '', NULL, '管理员审核目录'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3370 OR path = 'admin-audit');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3371, '企业资质审核', 3370, 1, 'enterprise', 'system/admin/enterpriseAudit/index', '', '', 1, 0, 'C', '0', '0', 'admin:enterprise:audit:list', 'form', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:enterprise:audit:list');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3372, '审核详情', 3371, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:enterprise:audit:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:enterprise:audit:query');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3373, '审核通过', 3371, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:enterprise:audit:pass', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:enterprise:audit:pass');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3374, '审核驳回', 3371, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:enterprise:audit:reject', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:enterprise:audit:reject');

-- 5) 角色绑定
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (3300,3301,3302,3303,3304,3311,3312,3313,3321,3322,3323,3324,3325,3331,3332,3333,3341)
WHERE r.role_key = 'enterprise'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (3350,3351,3352,3353,3354,3361,3362,3363,3364,3365,3366)
WHERE r.role_key = 'student'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (3370,3371,3372,3373,3374)
WHERE r.role_key = 'admin'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

COMMIT;

