-- 校园兼职平台：岗位投递与互评模块数据库脚本
-- 说明：
-- 1) 基于 RuoYi 原生表扩展，不修改 sys_user 等核心表结构
-- 2) 互评仅在企业标记“已完成”后开放
-- 3) 公告能力复用 sys_notice/sys_notice_read，本脚本不新增公告表

set names utf8mb4;

-- ----------------------------
-- 1、学生信息表
-- ----------------------------
drop table if exists t_student_info;
create table t_student_info (
  student_id        bigint(20)      not null                   comment '学生ID（关联sys_user.user_id）',
  student_no        varchar(32)     not null                   comment '学号',
  real_name         varchar(30)     not null                   comment '姓名',
  gender            char(1)         default '2'                comment '性别（0男 1女 2未知）',
  school_name       varchar(100)    not null                   comment '学校名称',
  major_name        varchar(100)    not null                   comment '专业名称',
  grade_year        smallint(4)     default null               comment '入学年份',
  phone             varchar(11)     default ''                 comment '联系电话',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  del_flag          char(1)         default '0'                comment '删除标志（0存在 2删除）',
  primary key (student_id),
  unique key uk_student_no (student_no),
  key idx_student_school (school_name),
  key idx_student_phone (phone),
  constraint fk_student_user foreign key (student_id) references sys_user (user_id)
) engine=innodb comment='学生信息表';


-- ----------------------------
-- 2、学生简历表
-- ----------------------------
drop table if exists t_student_resume;
create table t_student_resume (
  resume_id               bigint(20)      not null auto_increment    comment '简历ID',
  student_id              bigint(20)      not null                   comment '学生ID（关联sys_user.user_id）',
  expected_position       varchar(100)    default ''                 comment '求职意向',
  expected_salary_min     decimal(10,2)   default null               comment '期望薪资下限',
  expected_salary_max     decimal(10,2)   default null               comment '期望薪资上限',
  education_experience    text                                       comment '教育经历',
  part_time_experience    text                                       comment '兼职经历',
  skill_tags              varchar(500)    default ''                 comment '技能特长',
  self_intro              varchar(1000)   default ''                 comment '自我介绍',
  status                  char(1)         default '0'                comment '状态（0有效 1停用）',
  create_by               varchar(64)     default ''                 comment '创建者',
  create_time             datetime                                   comment '创建时间',
  update_by               varchar(64)     default ''                 comment '更新者',
  update_time             datetime                                   comment '更新时间',
  remark                  varchar(500)    default null               comment '备注',
  del_flag                char(1)         default '0'                comment '删除标志（0存在 2删除）',
  primary key (resume_id),
  unique key uk_resume_student (student_id),
  key idx_resume_status (status),
  constraint fk_resume_student foreign key (student_id) references sys_user (user_id)
) engine=innodb auto_increment=1000 comment='学生简历表';


-- ----------------------------
-- 3、企业信息表
-- ----------------------------
drop table if exists t_enterprise_info;
create table t_enterprise_info (
  enterprise_id      bigint(20)      not null                   comment '企业ID（关联sys_user.user_id）',
  enterprise_name    varchar(150)    not null                   comment '企业名称',
  unified_code       varchar(32)     not null                   comment '统一社会信用代码',
  legal_person       varchar(50)     default ''                 comment '法人信息',
  contact_name       varchar(30)     not null                   comment '联系人',
  contact_phone      varchar(11)     not null                   comment '联系电话',
  license_url        varchar(255)    not null                   comment '营业执照图片路径',
  intro              varchar(2000)   default ''                 comment '企业简介',
  audit_status       char(1)         default '0'                comment '审核状态（0待审核 1通过 2驳回）',
  audit_reason       varchar(500)    default ''                 comment '驳回原因',
  audit_by           bigint(20)      default null               comment '审核人（sys_user.user_id）',
  audit_time         datetime                                   comment '审核时间',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  del_flag           char(1)         default '0'                comment '删除标志（0存在 2删除）',
  primary key (enterprise_id),
  unique key uk_enterprise_code (unified_code),
  key idx_enterprise_name (enterprise_name),
  key idx_enterprise_audit (audit_status),
  constraint fk_enterprise_user foreign key (enterprise_id) references sys_user (user_id),
  constraint fk_enterprise_audit_user foreign key (audit_by) references sys_user (user_id)
) engine=innodb comment='企业信息表';


-- ----------------------------
-- 4、岗位信息表
-- ----------------------------
drop table if exists t_job_post;
create table t_job_post (
  post_id             bigint(20)      not null auto_increment    comment '岗位ID',
  enterprise_id       bigint(20)      not null                   comment '企业ID',
  post_name           varchar(100)    not null                   comment '岗位名称',
  salary_min          decimal(10,2)   not null                   comment '薪资下限',
  salary_max          decimal(10,2)   not null                   comment '薪资上限',
  salary_unit         char(1)         not null                   comment '结算方式（1日结 2周结 3月结）',
  work_location_type  char(1)         not null                   comment '工作地点类型（1校内 2校外 3远程）',
  work_address        varchar(255)    not null                   comment '工作地点',
  work_type           char(1)         not null                   comment '工作类型（1短期 2长期 3实习）',
  work_time_desc      varchar(255)    not null                   comment '工作时间描述',
  requirement_desc    varchar(1000)   default ''                 comment '岗位要求',
  recruit_count       int(11)         not null                   comment '招聘人数',
  deadline_time       datetime        not null                   comment '截止时间',
  contact_name        varchar(30)     not null                   comment '联系方式-联系人',
  contact_phone       varchar(11)     not null                   comment '联系方式-电话',
  post_status         char(1)         default '1'                comment '岗位状态（0草稿 1上架 2下架）',
  view_count          int(11)         default 0                  comment '浏览量',
  collect_count       int(11)         default 0                  comment '收藏量',
  apply_count         int(11)         default 0                  comment '投递量',
  hire_count          int(11)         default 0                  comment '录用人数',
  complete_count      int(11)         default 0                  comment '完成人数',
  create_by           varchar(64)     default ''                 comment '创建者',
  create_time         datetime                                   comment '创建时间',
  update_by           varchar(64)     default ''                 comment '更新者',
  update_time         datetime                                   comment '更新时间',
  remark              varchar(500)    default null               comment '备注',
  del_flag            char(1)         default '0'                comment '删除标志（0存在 2删除）',
  primary key (post_id),
  key idx_post_enterprise (enterprise_id),
  key idx_post_status (post_status),
  key idx_post_deadline (deadline_time),
  key idx_post_time (create_time),
  constraint fk_post_enterprise foreign key (enterprise_id) references t_enterprise_info (enterprise_id)
) engine=innodb auto_increment=1000 comment='岗位信息表';


-- ----------------------------
-- 5、投递记录表
-- ----------------------------
drop table if exists t_job_application;
create table t_job_application (
  application_id      bigint(20)      not null auto_increment    comment '投递ID',
  post_id             bigint(20)      not null                   comment '岗位ID',
  student_id          bigint(20)      not null                   comment '学生ID',
  resume_id           bigint(20)      not null                   comment '简历ID',
  apply_time          datetime        not null                   comment '投递时间',
  application_status  char(1)         default '0'                comment '处理状态（0已投递 1已录用 2企业已完成）',
  process_time        datetime                                   comment '最近处理时间',
  enterprise_remark   varchar(500)    default ''                 comment '企业备注',
  complete_time       datetime                                   comment '企业标记完成时间',
  create_by           varchar(64)     default ''                 comment '创建者',
  create_time         datetime                                   comment '创建时间',
  update_by           varchar(64)     default ''                 comment '更新者',
  update_time         datetime                                   comment '更新时间',
  remark              varchar(500)    default null               comment '备注',
  del_flag            char(1)         default '0'                comment '删除标志（0存在 2删除）',
  primary key (application_id),
  unique key uk_application_post_student (post_id, student_id),
  key idx_application_student (student_id),
  key idx_application_status (application_status),
  key idx_application_time (apply_time),
  constraint fk_application_post foreign key (post_id) references t_job_post (post_id),
  constraint fk_application_student foreign key (student_id) references t_student_info (student_id),
  constraint fk_application_resume foreign key (resume_id) references t_student_resume (resume_id)
) engine=innodb auto_increment=1000 comment='投递记录表';


-- ----------------------------
-- 6、岗位收藏表
-- ----------------------------
drop table if exists t_job_collection;
create table t_job_collection (
  collection_id      bigint(20)      not null auto_increment    comment '收藏ID',
  student_id         bigint(20)      not null                   comment '学生ID',
  post_id            bigint(20)      not null                   comment '岗位ID',
  collection_time    datetime        not null                   comment '收藏时间',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  del_flag           char(1)         default '0'                comment '删除标志（0存在 2删除）',
  primary key (collection_id),
  unique key uk_collection_student_post (student_id, post_id),
  key idx_collection_time (collection_time),
  constraint fk_collection_student foreign key (student_id) references t_student_info (student_id),
  constraint fk_collection_post foreign key (post_id) references t_job_post (post_id)
) engine=innodb auto_increment=1000 comment='岗位收藏表';


-- ----------------------------
-- 7、双向互评表
-- ----------------------------
drop table if exists t_job_evaluation;
create table t_job_evaluation (
  evaluation_id      bigint(20)      not null auto_increment    comment '评价ID',
  application_id     bigint(20)      not null                   comment '投递记录ID',
  post_id            bigint(20)      not null                   comment '岗位ID',
  student_id         bigint(20)      not null                   comment '学生ID',
  enterprise_id      bigint(20)      not null                   comment '企业ID',
  evaluation_type    char(1)         not null                   comment '评价类型（1学生评企业 2企业评学生）',
  score              tinyint(1)      not null                   comment '评分（1-5）',
  content            varchar(1000)   default ''                 comment '评价内容',
  evaluation_time    datetime        not null                   comment '评价时间',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  del_flag           char(1)         default '0'                comment '删除标志（0存在 2删除）',
  primary key (evaluation_id),
  unique key uk_eval_application_type (application_id, evaluation_type),
  key idx_eval_target_student (student_id),
  key idx_eval_target_enterprise (enterprise_id),
  key idx_eval_time (evaluation_time),
  constraint fk_eval_application foreign key (application_id) references t_job_application (application_id),
  constraint fk_eval_post foreign key (post_id) references t_job_post (post_id),
  constraint fk_eval_student foreign key (student_id) references t_student_info (student_id),
  constraint fk_eval_enterprise foreign key (enterprise_id) references t_enterprise_info (enterprise_id),
  constraint chk_eval_score check (score >= 1 and score <= 5)
) engine=innodb auto_increment=1000 comment='双向互评表';


-- ----------------------------
-- 8、业务字典初始化（幂等）
-- ----------------------------
insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
select '岗位状态', 'job_post_status', '0', 'admin', sysdate(), '岗位状态字典'
where not exists (select 1 from sys_dict_type where dict_type = 'job_post_status');

insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
select '投递状态', 'job_application_status', '0', 'admin', sysdate(), '投递状态字典'
where not exists (select 1 from sys_dict_type where dict_type = 'job_application_status');

insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
select '互评类型', 'job_evaluation_type', '0', 'admin', sysdate(), '互评类型字典'
where not exists (select 1 from sys_dict_type where dict_type = 'job_evaluation_type');

insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
select '企业审核状态', 'enterprise_audit_status', '0', 'admin', sysdate(), '企业资质审核状态字典'
where not exists (select 1 from sys_dict_type where dict_type = 'enterprise_audit_status');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 1, '草稿', '0', 'job_post_status', '', 'info', 'N', '0', 'admin', sysdate(), '岗位草稿'
where not exists (select 1 from sys_dict_data where dict_type='job_post_status' and dict_value='0');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 2, '上架', '1', 'job_post_status', '', 'success', 'Y', '0', 'admin', sysdate(), '岗位上架'
where not exists (select 1 from sys_dict_data where dict_type='job_post_status' and dict_value='1');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 3, '下架', '2', 'job_post_status', '', 'danger', 'N', '0', 'admin', sysdate(), '岗位下架'
where not exists (select 1 from sys_dict_data where dict_type='job_post_status' and dict_value='2');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 1, '已投递', '0', 'job_application_status', '', 'info', 'Y', '0', 'admin', sysdate(), '学生已投递'
where not exists (select 1 from sys_dict_data where dict_type='job_application_status' and dict_value='0');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 2, '已录用', '1', 'job_application_status', '', 'primary', 'N', '0', 'admin', sysdate(), '企业已录用'
where not exists (select 1 from sys_dict_data where dict_type='job_application_status' and dict_value='1');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 3, '企业已完成', '2', 'job_application_status', '', 'success', 'N', '0', 'admin', sysdate(), '企业标记兼职完成'
where not exists (select 1 from sys_dict_data where dict_type='job_application_status' and dict_value='2');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 1, '学生评企业', '1', 'job_evaluation_type', '', 'warning', 'N', '0', 'admin', sysdate(), '评价类型'
where not exists (select 1 from sys_dict_data where dict_type='job_evaluation_type' and dict_value='1');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 2, '企业评学生', '2', 'job_evaluation_type', '', 'warning', 'N', '0', 'admin', sysdate(), '评价类型'
where not exists (select 1 from sys_dict_data where dict_type='job_evaluation_type' and dict_value='2');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 1, '待审核', '0', 'enterprise_audit_status', '', 'warning', 'Y', '0', 'admin', sysdate(), '企业审核待处理'
where not exists (select 1 from sys_dict_data where dict_type='enterprise_audit_status' and dict_value='0');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 2, '审核通过', '1', 'enterprise_audit_status', '', 'success', 'N', '0', 'admin', sysdate(), '企业审核通过'
where not exists (select 1 from sys_dict_data where dict_type='enterprise_audit_status' and dict_value='1');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
select 3, '审核驳回', '2', 'enterprise_audit_status', '', 'danger', 'N', '0', 'admin', sysdate(), '企业审核驳回'
where not exists (select 1 from sys_dict_data where dict_type='enterprise_audit_status' and dict_value='2');

