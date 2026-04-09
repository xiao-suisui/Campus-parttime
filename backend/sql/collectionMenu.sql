-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('岗位收藏', '3', '1', 'collection', 'system/collection/index', 1, 0, 'C', '0', '0', 'system:collection:list', '#', 'admin', sysdate(), '', null, '岗位收藏菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('岗位收藏查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:collection:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('岗位收藏新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:collection:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('岗位收藏修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:collection:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('岗位收藏删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:collection:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('岗位收藏导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:collection:export',       '#', 'admin', sysdate(), '', null, '');