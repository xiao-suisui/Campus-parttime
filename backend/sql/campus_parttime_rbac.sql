-- 校园兼职平台：岗位投递与互评模块 RBAC 初始化脚本
-- 说明：
-- 1) 幂等执行：角色、菜单、权限点均带 not exists 判断
-- 2) 公告复用 sys_notice，不新增公告表权限模型

set names utf8mb4;

-- ----------------------------
-- 1、业务角色（企业/学生）
-- ----------------------------
insert into sys_role (
  role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly,
  status, del_flag, create_by, create_time, remark
)
select '企业用户', 'enterprise', 10, '1', 1, 1, '0', '0', 'admin', sysdate(), '校园兼职平台-企业角色'
where not exists (select 1 from sys_role where role_key = 'enterprise');

insert into sys_role (
  role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly,
  status, del_flag, create_by, create_time, remark
)
select '学生用户', 'student', 11, '1', 1, 1, '0', '0', 'admin', sysdate(), '校园兼职平台-学生角色'
where not exists (select 1 from sys_role where role_key = 'student');


-- ----------------------------
-- 2、企业端菜单与权限点
-- ----------------------------
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2000, '企业工作台', 0, 10, 'enterprise', null, '', '', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', sysdate(), '', null, '校园兼职平台-企业目录'
where not exists (select 1 from sys_menu where menu_id = 2000);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2001, '企业岗位管理', 2000, 1, 'post', 'enterprise/post/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:post:list', 'clipboard', 'admin', sysdate(), '', null, '企业岗位菜单'
where not exists (select 1 from sys_menu where menu_id = 2001);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2002, '投递处理', 2000, 2, 'application', 'enterprise/application/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:application:list', 'form', 'admin', sysdate(), '', null, '企业投递处理菜单'
where not exists (select 1 from sys_menu where menu_id = 2002);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2003, '互评管理', 2000, 3, 'evaluation', 'enterprise/evaluation/index', '', '', 1, 0, 'C', '0', '0', 'enterprise:evaluation:list', 'message', 'admin', sysdate(), '', null, '企业互评菜单'
where not exists (select 1 from sys_menu where menu_id = 2003);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2004, '岗位查询', 2001, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2004);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2005, '岗位新增', 2001, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2005);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2006, '岗位修改', 2001, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:edit', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2006);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2007, '岗位上架', 2001, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:up', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2007);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2008, '岗位下架', 2001, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:post:down', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2008);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2009, '投递查询', 2002, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2009);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2010, '投递录用', 2002, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:hire', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2010);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2011, '标记完成', 2002, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:application:complete', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2011);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2012, '互评查询', 2003, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:evaluation:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2012);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2013, '互评新增', 2003, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'enterprise:evaluation:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2013);


-- ----------------------------
-- 3、学生端权限点（仅接口权限，菜单隐藏）
-- ----------------------------
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2050, '学生端接口', 0, 11, 'student-api', null, '', '', 1, 1, 'M', '1', '0', '', 'user', 'admin', sysdate(), '', null, '学生端隐藏目录'
where not exists (select 1 from sys_menu where menu_id = 2050);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2051, '岗位列表', 2050, 1, '', '', '', '', 1, 0, 'F', '1', '0', 'student:job:list', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2051);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2052, '岗位详情', 2050, 2, '', '', '', '', 1, 0, 'F', '1', '0', 'student:job:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2052);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2053, '收藏新增', 2050, 3, '', '', '', '', 1, 0, 'F', '1', '0', 'student:collection:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2053);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2054, '收藏取消', 2050, 4, '', '', '', '', 1, 0, 'F', '1', '0', 'student:collection:remove', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2054);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2055, '投递新增', 2050, 5, '', '', '', '', 1, 0, 'F', '1', '0', 'student:application:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2055);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2056, '投递列表', 2050, 6, '', '', '', '', 1, 0, 'F', '1', '0', 'student:application:list', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2056);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2057, '互评新增', 2050, 7, '', '', '', '', 1, 0, 'F', '1', '0', 'student:evaluation:add', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2057);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2058, '互评列表', 2050, 8, '', '', '', '', 1, 0, 'F', '1', '0', 'student:evaluation:list', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2058);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2059, '公告列表', 2050, 9, '', '', '', '', 1, 0, 'F', '1', '0', 'student:notice:list', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2059);


-- ----------------------------
-- 4、管理员端业务菜单与权限点
-- ----------------------------
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2100, '企业资质审核', 1, 10, 'enterpriseAudit', 'campus/enterpriseAudit/index', '', '', 1, 0, 'C', '0', '0', 'campus:enterprise:audit:list', 'example', 'admin', sysdate(), '', null, '管理员-企业资质审核'
where not exists (select 1 from sys_menu where menu_id = 2100);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2101, '岗位监管', 1, 11, 'jobReview', 'campus/jobReview/index', '', '', 1, 0, 'C', '0', '0', 'campus:job:review:list', 'dashboard', 'admin', sysdate(), '', null, '管理员-岗位监管'
where not exists (select 1 from sys_menu where menu_id = 2101);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2102, '运营统计', 1, 12, 'campusStat', 'campus/stat/index', '', '', 1, 0, 'C', '0', '0', 'campus:stat:list', 'chart', 'admin', sysdate(), '', null, '管理员-运营统计'
where not exists (select 1 from sys_menu where menu_id = 2102);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2103, '审核查询', 2100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'campus:enterprise:audit:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2103);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2104, '审核通过', 2100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'campus:enterprise:audit:pass', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2104);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2105, '审核驳回', 2100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'campus:enterprise:audit:reject', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2105);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2106, '监管查询', 2101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'campus:job:review:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2106);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2107, '强制下架', 2101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'campus:job:review:forceDown', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2107);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2108, '统计查询', 2102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'campus:stat:query', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2108);

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
select 2109, '统计导出', 2102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'campus:stat:export', '#', 'admin', sysdate(), '', null, ''
where not exists (select 1 from sys_menu where menu_id = 2109);


-- ----------------------------
-- 5、角色绑定菜单权限（幂等）
-- ----------------------------
insert into sys_role_menu (role_id, menu_id)
select r.role_id, m.menu_id
from sys_role r
join sys_menu m on m.menu_id in (2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013)
where r.role_key = 'enterprise'
  and not exists (
    select 1 from sys_role_menu rm where rm.role_id = r.role_id and rm.menu_id = m.menu_id
  );

insert into sys_role_menu (role_id, menu_id)
select r.role_id, m.menu_id
from sys_role r
join sys_menu m on m.menu_id in (2050,2051,2052,2053,2054,2055,2056,2057,2058,2059)
where r.role_key = 'student'
  and not exists (
    select 1 from sys_role_menu rm where rm.role_id = r.role_id and rm.menu_id = m.menu_id
  );

insert into sys_role_menu (role_id, menu_id)
select r.role_id, m.menu_id
from sys_role r
join sys_menu m on m.menu_id in (2100,2101,2102,2103,2104,2105,2106,2107,2108,2109)
where r.role_key = 'admin'
  and not exists (
    select 1 from sys_role_menu rm where rm.role_id = r.role_id and rm.menu_id = m.menu_id
  );


-- ----------------------------
-- 6、可选：为测试账号绑定企业/学生角色（默认注释）
-- ----------------------------
-- insert into sys_user_role (user_id, role_id)
-- select 2, r.role_id from sys_role r
-- where r.role_key = 'enterprise'
--   and not exists (select 1 from sys_user_role ur where ur.user_id = 2 and ur.role_id = r.role_id);

