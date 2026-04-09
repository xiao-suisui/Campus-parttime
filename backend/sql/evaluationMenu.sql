-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('双向互评', '0', '1', 'evaluation', 'system/evaluation/index', 1, 0, 'C', '0', '0', 'system:evaluation:list', '#', 'admin', sysdate(), '', null, '双向互评菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('双向互评查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:evaluation:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('双向互评新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:evaluation:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('双向互评修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:evaluation:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('双向互评删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:evaluation:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('双向互评导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:evaluation:export',       '#', 'admin', sysdate(), '', null, '');