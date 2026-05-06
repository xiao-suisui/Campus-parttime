-- 旧菜单/权限清理脚本（先执行）
-- 说明：
-- 1) 仅清理历史 legacy 业务菜单、按钮与角色绑定。
-- 2) 不删除若依框架自带的系统菜单，如 system/user、system/role、system/menu、system/post 等。
-- 3) 建议先在测试库执行，确认 sys_menu 与 sys_role_menu 清理范围正确后，再执行新建脚本。

SET NAMES utf8mb4;
START TRANSACTION;

-- 1) 先删角色-菜单绑定，避免外键/孤儿引用
DELETE rm
FROM sys_role_menu rm
JOIN sys_menu m ON m.menu_id = rm.menu_id
WHERE m.perms LIKE 'campus:%'
   OR m.path LIKE 'campus%'
   OR m.component LIKE 'campus/%'
   OR m.path IN ('campus', 'campus-api', 'student-api', 'student-api-phase1');

-- 2) 再删历史 legacy 菜单本体
DELETE FROM sys_menu
WHERE perms LIKE 'campus:%'
   OR path LIKE 'campus%'
   OR component LIKE 'campus/%'
   OR path IN ('campus', 'campus-api', 'student-api', 'student-api-phase1');

COMMIT;

