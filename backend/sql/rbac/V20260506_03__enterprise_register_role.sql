-- 待审核企业角色与最小权限脚本
-- 说明：
-- 1) 创建"待审核企业"角色（enterprise_pending），用于企业注册后、审核前的状态
-- 2) 仅分配企业资料页面的查看和编辑权限，审核通过后切换为 enterprise 角色
-- 3) 幂等执行：角色、菜单、角色菜单绑定均使用 not exists

SET NAMES utf8mb4;
START TRANSACTION;

-- 1) 创建待审核企业角色
INSERT INTO sys_role (
  role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly,
  status, del_flag, create_by, create_time, remark
)
SELECT '待审核企业', 'enterprise_pending', 9, '1', 1, 1, '0', '0', 'admin', SYSDATE(), '企业注册后待审核状态角色'
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key = 'enterprise_pending');

-- 2) 为待审核企业分配最小权限（仅企业资料页面）
-- 复用已有的企业资料菜单（3300, 3301）
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (3300, 3301, 3311, 3312, 3313)
WHERE r.role_key = 'enterprise_pending'
  AND NOT EXISTS (SELECT 1 FROM sys_role_menu rm WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id);

-- 说明：
-- 3300: 企业端业务目录
-- 3301: 企业资料页面
-- 3311: 资料新增（按钮）
-- 3312: 资料修改（按钮）
-- 3313: 提交审核（按钮）

COMMIT;
