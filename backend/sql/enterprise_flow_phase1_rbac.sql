
-- 企业端全流程 Phase-1 权限点脚本（后端接口权限）
-- 说明：
-- 1) 先补 enterprise 与 admin 审核权限，不强制改你现有菜单结构。
-- 2) 幂等执行：not exists 防重复。
-- 3) 默认 ROLLBACK，请你确认后改为 COMMIT。

SET NAMES utf8mb4;

START TRANSACTION;

-- =====================================================================
-- 1) 企业接口权限目录（隐藏）
-- =====================================================================
INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, update_by, update_time, remark
)
SELECT
  2350, '企业端接口(Phase1)', 0, 31, 'enterprise-api-phase1', NULL, '', '',
  1, 1, 'M', '1', '0', '', 'peoples',
  'admin', SYSDATE(), '', NULL, '企业端接口权限目录（隐藏）'
WHERE NOT EXISTS (
  SELECT 1 FROM sys_menu WHERE menu_id = 2350 OR path = 'enterprise-api-phase1'
);

-- =====================================================================
-- 2) 企业权限点（对应 enterprise 后端接口）
-- =====================================================================
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2351, '企业资料查询', 2350, 1, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:profile:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:profile:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2352, '企业资料新增', 2350, 2, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:profile:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:profile:add');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2353, '企业资料修改', 2350, 3, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:profile:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:profile:edit');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2354, '企业资料提交审核', 2350, 4, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:profile:submit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:profile:submit');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2355, '企业岗位列表', 2350, 5, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:post:list', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2356, '企业岗位详情', 2350, 6, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:post:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2357, '企业岗位新增', 2350, 7, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:post:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:add');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2358, '企业岗位修改', 2350, 8, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:post:edit', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:edit');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2359, '企业岗位上架', 2350, 9, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:post:up', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:up');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2360, '企业岗位下架', 2350, 10, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:post:down', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:post:down');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2361, '企业投递列表', 2350, 11, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:application:list', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:application:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2362, '企业投递详情', 2350, 12, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:application:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:application:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2363, '企业投递录用', 2350, 13, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:application:hire', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:application:hire');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2364, '企业标记完成', 2350, 14, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:application:complete', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:application:complete');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2365, '企业互评列表', 2350, 15, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:evaluation:list', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:evaluation:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2366, '企业互评新增', 2350, 16, '', '', '', '', 1, 0, 'F', '1', '0', 'enterprise:evaluation:add', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'enterprise:evaluation:add');

-- =====================================================================
-- 3) 管理员审核权限（只审只看）
-- =====================================================================
INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, update_by, update_time, remark
)
SELECT
  2370, '管理员审核接口(Phase1)', 0, 32, 'admin-audit-phase1', NULL, '', '',
  1, 1, 'M', '1', '0', '', 'lock',
  'admin', SYSDATE(), '', NULL, '管理员审核接口目录（隐藏）'
WHERE NOT EXISTS (
  SELECT 1 FROM sys_menu WHERE menu_id = 2370 OR path = 'admin-audit-phase1'
);

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2371, '企业审核列表', 2370, 1, '', '', '', '', 1, 0, 'F', '1', '0', 'admin:enterprise:audit:list', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:enterprise:audit:list');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2372, '企业审核详情', 2370, 2, '', '', '', '', 1, 0, 'F', '1', '0', 'admin:enterprise:audit:query', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:enterprise:audit:query');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2373, '企业审核通过', 2370, 3, '', '', '', '', 1, 0, 'F', '1', '0', 'admin:enterprise:audit:pass', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:enterprise:audit:pass');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
SELECT 2374, '企业审核驳回', 2370, 4, '', '', '', '', 1, 0, 'F', '1', '0', 'admin:enterprise:audit:reject', '#', 'admin', SYSDATE(), '', NULL, ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'admin:enterprise:audit:reject');

-- =====================================================================
-- 4) 角色绑定（enterprise + admin）
-- =====================================================================
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.perms IN (
  'enterprise:profile:query','enterprise:profile:add','enterprise:profile:edit','enterprise:profile:submit',
  'enterprise:post:list','enterprise:post:query','enterprise:post:add','enterprise:post:edit','enterprise:post:up','enterprise:post:down',
  'enterprise:application:list','enterprise:application:query','enterprise:application:hire','enterprise:application:complete',
  'enterprise:evaluation:list','enterprise:evaluation:add'
)
WHERE r.role_key = 'enterprise'
  AND NOT EXISTS (
    SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id
  );

INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.perms IN (
  'admin:enterprise:audit:list',
  'admin:enterprise:audit:query',
  'admin:enterprise:audit:pass',
  'admin:enterprise:audit:reject'
)
WHERE r.role_key = 'admin'
  AND NOT EXISTS (
    SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id
  );

-- =====================================================================
-- 5) 建议（不自动执行）：收敛管理员企业业务写权限
-- =====================================================================
-- 如你确认管理员只审不办，可在验证后执行：
-- DELETE rm FROM sys_role_menu rm
-- JOIN sys_role r ON rm.role_id = r.role_id
-- JOIN sys_menu m ON rm.menu_id = m.menu_id
-- WHERE r.role_key = 'admin'
--   AND m.perms IN (
--     'enterprise:profile:add','enterprise:profile:edit',
--     'enterprise:post:add','enterprise:post:edit','enterprise:post:up','enterprise:post:down',
--     'enterprise:application:hire','enterprise:application:complete',
--     'enterprise:evaluation:add'
--   );

-- =====================================================================
-- 6) 回滚草案
-- =====================================================================
-- DELETE rm FROM sys_role_menu rm
-- JOIN sys_menu m ON rm.menu_id = m.menu_id
-- WHERE m.perms LIKE 'enterprise:%' OR m.perms LIKE 'admin:enterprise:audit:%';
--
-- DELETE FROM sys_menu WHERE perms LIKE 'enterprise:%' OR perms LIKE 'admin:enterprise:audit:%';
-- DELETE FROM sys_menu WHERE menu_id IN (2350, 2370);

-- 先检查再提交
-- COMMIT;
ROLLBACK;

