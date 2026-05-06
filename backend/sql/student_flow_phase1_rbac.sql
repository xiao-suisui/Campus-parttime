-- 学生端全流程 Phase-1 权限点脚本（仅后端接口权限）
-- 说明：
-- 1) 当前学生前端（小程序）未接入后台菜单，故采用“隐藏目录 + 功能权限(F)”模式。
-- 2) 本脚本不改你现有管理员/企业菜单结构，仅补 student 角色权限点。
-- 3) 幂等执行：使用 not exists 防重复插入。

SET NAMES utf8mb4;

START TRANSACTION;

-- =====================================================================
-- 1) 学生接口权限目录（隐藏）
-- =====================================================================
INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, update_by, update_time, remark
)
SELECT
  2250, '学生端接口(Phase1)', 0, 30, 'student-api-phase1', NULL, '', '',
  1, 1, 'M', '1', '0', '', 'user',
  'admin', SYSDATE(), '', NULL, '学生端接口权限目录（隐藏）'
WHERE NOT EXISTS (
  SELECT 1 FROM sys_menu WHERE menu_id = 2250 OR path = 'student-api-phase1'
);

-- =====================================================================
-- 2) 学生接口权限点（对应本次新增后端接口）
-- =====================================================================
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2251, '岗位列表', 2250, 1, '', '', '', '', 1, 0, 'F', '1', '0', 'student:job:list', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:job:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2252, '岗位详情', 2250, 2, '', '', '', '', 1, 0, 'F', '1', '0', 'student:job:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:job:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2253, '收藏列表', 2250, 3, '', '', '', '', 1, 0, 'F', '1', '0', 'student:collection:list', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:collection:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2254, '收藏新增', 2250, 4, '', '', '', '', 1, 0, 'F', '1', '0', 'student:collection:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:collection:add');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2255, '收藏取消', 2250, 5, '', '', '', '', 1, 0, 'F', '1', '0', 'student:collection:remove', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:collection:remove');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2256, '投递列表', 2250, 6, '', '', '', '', 1, 0, 'F', '1', '0', 'student:application:list', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:application:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2257, '投递详情', 2250, 7, '', '', '', '', 1, 0, 'F', '1', '0', 'student:application:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:application:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2258, '投递新增', 2250, 8, '', '', '', '', 1, 0, 'F', '1', '0', 'student:application:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:application:add');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2259, '互评列表', 2250, 9, '', '', '', '', 1, 0, 'F', '1', '0', 'student:evaluation:list', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:evaluation:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2260, '互评新增', 2250, 10, '', '', '', '', 1, 0, 'F', '1', '0', 'student:evaluation:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'student:evaluation:add');

-- =====================================================================
-- 3) 角色绑定（student）
-- =====================================================================
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.perms IN (
  'student:job:list',
  'student:job:query',
  'student:collection:list',
  'student:collection:add',
  'student:collection:remove',
  'student:application:list',
  'student:application:query',
  'student:application:add',
  'student:evaluation:list',
  'student:evaluation:add'
)
WHERE r.role_key = 'student'
  AND NOT EXISTS (
    SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id
  );

-- =====================================================================
-- 4) 验证查询（执行后可手动查看）
-- =====================================================================
-- SELECT menu_id, menu_name, perms FROM sys_menu WHERE parent_id = 2250 OR menu_id = 2250 ORDER BY order_num;
-- SELECT r.role_key, m.menu_name, m.perms
-- FROM sys_role_menu rm
-- JOIN sys_role r ON rm.role_id = r.role_id
-- JOIN sys_menu m ON rm.menu_id = m.menu_id
-- WHERE r.role_key = 'student' AND m.perms LIKE 'student:%'
-- ORDER BY m.perms;

-- =====================================================================
-- 5) 回滚草案（按需手动执行）
-- =====================================================================
-- DELETE rm FROM sys_role_menu rm
-- JOIN sys_menu m ON rm.menu_id = m.menu_id
-- WHERE m.perms IN (
--   'student:job:list','student:job:query',
--   'student:collection:list','student:collection:add','student:collection:remove',
--   'student:application:list','student:application:query','student:application:add',
--   'student:evaluation:list','student:evaluation:add'
-- );
--
-- DELETE FROM sys_menu WHERE perms IN (
--   'student:job:list','student:job:query',
--   'student:collection:list','student:collection:add','student:collection:remove',
--   'student:application:list','student:application:query','student:application:add',
--   'student:evaluation:list','student:evaluation:add'
-- );
--
-- DELETE FROM sys_menu WHERE menu_id = 2250;

-- 先检查再提交
-- COMMIT;
ROLLBACK;

