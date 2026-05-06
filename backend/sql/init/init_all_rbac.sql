-- ============================================================
-- 校园兼职平台：全量 RBAC + 字典 初始化脚本
-- ============================================================
-- 说明：
-- 1) 本脚本整合所有历史 RBAC 脚本和字典数据，可一键执行。
-- 2) 会先清理旧版菜单（ID 2000-2109），再创建新版（3300+）。
-- 3) 所有操作幂等（WHERE NOT EXISTS / DELETE 安全执行）。
-- 4) 菜单 ID 分配：
--      3300-3341  企业端（目录+页面+按钮）
--      3350-3388  学生端（目录+页面+按钮）
--      3367       学生智能推荐（挂在岗位浏览下）
--      3370-3400  管理端（目录+页面+按钮）
-- 5) 权限前缀：enterprise: / student: / admin:

SET NAMES utf8mb4;
START TRANSACTION;

-- ============================================================
-- 第一步：清理旧版菜单（2000-2109）及其角色绑定
-- ============================================================

DELETE rm FROM sys_role_menu rm
  JOIN sys_menu m ON m.menu_id = rm.menu_id
 WHERE m.menu_id BETWEEN 2000 AND 2109;

DELETE FROM sys_menu WHERE menu_id BETWEEN 2000 AND 2109;

-- 同时清理旧的 Phase1 ROLLBACK 脚本可能留下的菜单（2250-2374）
DELETE rm FROM sys_role_menu rm
  JOIN sys_menu m ON m.menu_id = rm.menu_id
 WHERE m.menu_id BETWEEN 2250 AND 2374;

DELETE FROM sys_menu WHERE menu_id BETWEEN 2250 AND 2374;

-- ============================================================
-- 第二步：确保角色存在
-- ============================================================

INSERT INTO sys_role (role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
SELECT '企业用户', 'enterprise', 10, '1', 1, 1, '0', '0', 'admin', SYSDATE(), '校园兼职平台-企业角色'
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key = 'enterprise');

INSERT INTO sys_role (role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
SELECT '学生用户', 'student', 11, '1', 1, 1, '0', '0', 'admin', SYSDATE(), '校园兼职平台-学生角色'
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key = 'student');

-- ============================================================
-- 第三步：企业端菜单（3300-3341）
-- ============================================================

-- 目录
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3300, '企业端业务', 0, 20, 'enterprise-biz', 'Layout', '', '', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', SYSDATE(), '', NULL, '企业业务目录'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3300);

-- 页面
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3301, '企业资料', 3300, 1, 'profile', 'system/enterprise/profile/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:profile:query', 'user', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3301);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3302, '岗位管理', 3300, 2, 'post', 'system/enterprise/post/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:post:list', 'clipboard', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3302);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3303, '投递处理', 3300, 3, 'application', 'system/enterprise/application/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:application:list', 'form', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3303);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3304, '互评管理', 3300, 4, 'evaluation', 'system/enterprise/evaluation/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:evaluation:list', 'message', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3304);

-- 企业资料按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3311, '资料新增', 3301, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:profile:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3311);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3312, '资料修改', 3301, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:profile:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3312);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3313, '提交审核', 3301, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:profile:submit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3313);

-- 岗位管理按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3321, '岗位详情', 3302, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3321);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3322, '岗位新增', 3302, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3322);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3323, '岗位编辑', 3302, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3323);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3324, '岗位上架', 3302, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:up', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3324);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3325, '岗位下架', 3302, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:down', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3325);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3326, '岗位删除', 3302, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:remove', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3326);

-- 投递处理按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3331, '投递详情', 3303, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3331);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3332, '录用投递', 3303, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:hire', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3332);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3333, '标记完成', 3303, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:complete', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3333);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3334, '拒绝投递', 3303, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:reject', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3334);

-- 互评管理按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3341, '互评新增', 3304, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:evaluation:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3341);

-- ============================================================
-- 第四步：学生端菜单（3350-3388）
-- ============================================================

-- 目录
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3350, '学生端业务', 0, 21, 'student-biz', 'Layout', '', '', 1, 0, 'M', '0', '0', '', 'user', 'admin', SYSDATE(), '', NULL, '学生业务目录'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3350);

-- 页面
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3351, '岗位浏览', 3350, 1, 'post', 'system/student/post/index', '', '', 1, 0, 'C', '0', '0', 'student:job:list', 'search', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3351);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3352, '岗位收藏', 3350, 2, 'collection', 'system/student/collection/index', '', '', 1, 0, 'C', '0', '0', 'student:collection:list', 'star', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3352);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3353, '岗位投递', 3350, 3, 'application', 'system/student/application/index', '', '', 1, 0, 'C', '0', '0', 'student:application:list', 'edit', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3353);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3354, '互评记录', 3350, 4, 'evaluation', 'system/student/evaluation/index', '', '', 1, 0, 'C', '0', '0', 'student:evaluation:list', 'message', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3354);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3380, '我的简历', 3350, 5, 'resume', 'system/student/resume/index', '', '', 1, 0, 'C', '0', '0', 'student:resume:query', 'form', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3380);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3385, '个人信息', 3350, 6, 'info', 'system/student/info/index', '', '', 1, 0, 'C', '0', '0', 'student:info:query', 'user', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3385);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3388, '平台公告', 3350, 7, 'notice', 'system/student/notice/index', '', '', 1, 0, 'C', '0', '0', 'student:notice:list', 'notice', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3388);

-- 岗位浏览按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3361, '岗位详情', 3351, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:job:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3361);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3367, '岗位推荐', 3351, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:job:recommend', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3367);

-- 收藏按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3362, '收藏新增', 3352, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:collection:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3362);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3363, '收藏取消', 3352, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:collection:remove', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3363);

-- 投递按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3364, '投递详情', 3353, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:application:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3364);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3365, '投递新增', 3353, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:application:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3365);

-- 互评按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3366, '互评新增', 3354, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:evaluation:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3366);

-- 简历按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3381, '简历新增', 3380, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:resume:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3381);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3382, '简历修改', 3380, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:resume:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3382);

-- 信息按钮
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3386, '信息新增', 3385, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'student:info:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3386);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3387, '信息修改', 3385, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'student:info:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3387);

-- ============================================================
-- 第五步：管理端菜单（3370-3400）
-- ============================================================

-- 目录
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3370, '平台管理', 0, 22, 'admin-biz', 'Layout', '', '', 1, 0, 'M', '0', '0', '', 'example', 'admin', SYSDATE(), '', NULL, '管理员业务目录'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3370);

-- 企业资质审核页面
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3371, '企业资质审核', 3370, 1, 'enterprise', 'system/admin/enterpriseAudit/index', '', '', 1, 0, 'C', '0', '0', 'admin:enterprise:audit:list', 'form', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3371);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3372, '审核详情', 3371, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:enterprise:audit:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3372);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3373, '审核通过', 3371, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:enterprise:audit:pass', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3373);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3374, '审核驳回', 3371, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:enterprise:audit:reject', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3374);

-- 岗位监管页面
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3390, '岗位监管', 3370, 2, 'jobReview', 'system/admin/jobReview/index', '', '', 1, 0, 'C', '0', '0', 'admin:job-review:list', 'job', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3390);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3391, '岗位详情', 3390, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:job-review:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3391);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3392, '强制下架', 3390, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'admin:job-review:force-down', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3392);

-- 运营统计页面
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 3400, '运营统计', 3370, 3, 'stat', 'system/admin/stat/index', '', '', 1, 0, 'C', '0', '0', 'admin:stat:query', 'chart', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3400);

-- ============================================================
-- 第六步：角色绑定
-- ============================================================

-- 清理旧的角色绑定（安全起见）
DELETE FROM sys_role_menu WHERE role_id IN (SELECT role_id FROM sys_role WHERE role_key IN ('enterprise', 'student'));

-- 企业角色绑定
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (
  3300,3301,3302,3303,3304,
  3311,3312,3313,
  3321,3322,3323,3324,3325,3326,
  3331,3332,3333,3334,
  3341
)
WHERE r.role_key = 'enterprise'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

-- 学生角色绑定
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (
  3350,3351,3352,3353,3354,
  3361,3362,3363,3364,3365,3366,3367,
  3380,3381,3382,
  3385,3386,3387,
  3388
)
WHERE r.role_key = 'student'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

-- 管理员角色绑定（仅新增的业务菜单，不重复绑定系统菜单）
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (
  3370,3371,3372,3373,3374,
  3390,3391,3392,
  3400
)
WHERE r.role_key = 'admin'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

-- ============================================================
-- 第七步：字典数据
-- ============================================================

-- 投递状态：补充 3=已拒绝
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 4, '已拒绝', '3', 'job_application_status', '', 'danger', 'N', '0', 'admin', SYSDATE(), '企业拒绝投递'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'job_application_status' AND dict_value = '3');

-- 薪资结算方式
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
SELECT '薪资结算方式', 'salary_unit', '0', 'admin', SYSDATE(), '薪资结算方式字典'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'salary_unit');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '日结', '1', 'salary_unit', '', 'info', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'salary_unit' AND dict_value = '1');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '周结', '2', 'salary_unit', '', '', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'salary_unit' AND dict_value = '2');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '月结', '3', 'salary_unit', '', 'success', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'salary_unit' AND dict_value = '3');

-- 工作地点类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
SELECT '工作地点类型', 'work_location_type', '0', 'admin', SYSDATE(), '工作地点类型字典'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'work_location_type');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '校内', '1', 'work_location_type', '', 'success', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'work_location_type' AND dict_value = '1');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '校外', '2', 'work_location_type', '', 'warning', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'work_location_type' AND dict_value = '2');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '远程', '3', 'work_location_type', '', 'info', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'work_location_type' AND dict_value = '3');

-- 工作类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
SELECT '工作类型', 'work_type', '0', 'admin', SYSDATE(), '工作类型字典'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'work_type');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '短期', '1', 'work_type', '', 'info', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'work_type' AND dict_value = '1');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '长期', '2', 'work_type', '', 'success', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'work_type' AND dict_value = '2');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '实习', '3', 'work_type', '', 'warning', 'N', '0', 'admin', SYSDATE(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'work_type' AND dict_value = '3');

COMMIT;
