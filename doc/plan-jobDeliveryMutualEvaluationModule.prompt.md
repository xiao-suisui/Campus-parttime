## Plan: 岗位投递互评模块下一步改造（执行版）

本计划按“先闭环、后治理”推进：优先修复阻塞问题与越权入口，再收敛动作接口，最后完善权限、稳定性与测试。你已明确数据库菜单表由你修改，因此本计划不改菜单SQL。

### P0（最高优先）：先让最小闭环可用且不越权

#### 目标
- 跑通最小闭环：发布岗位 -> 投递 -> 录用 -> 企业标记完成 -> 双向互评。
- 先堵住高风险问题：路由冲突、前端高危字段暴露、互评状态未校验。

#### 改造点
1. 修复 `StudentInfoController` 与 `EnterpriseInfoController` 的接口路由与权限冲突。
2. 在互评服务端强制校验：仅 `application_status='2'` 才允许新增互评。
3. 前端先移除明显高危字段的可编辑入口（如 `delFlag`，后续继续收敛 `enterpriseId/studentId/统计字段`）。
4. 统一说明：菜单与按钮权限 SQL 由你手工维护，本轮代码不改菜单 SQL。

#### 涉及文件
- `backend/main/java/com/ruoyi/system/controller/StudentInfoController.java`
- `backend/main/java/com/ruoyi/system/service/impl/JobEvaluationServiceImpl.java`
- `frontend/src/views/system/application/index.vue`

#### 验收标准
- `StudentInfo` 与 `EnterpriseInfo` 不再共享同一路由/同一权限前缀。
- 非 `application_status='2'` 的投递无法创建互评。
- `system/application` 页面不再允许编辑 `delFlag`。

### P1：按角色收敛业务动作接口

#### 目标
- 将“生成器式 CRUD”转为“业务动作接口”，减少越权和脏数据。

#### 改造点
1. `JobApplication` 收敛为 `apply/hire/complete` 动作接口。
2. `JobEvaluation` 收敛为 `evaluate` 动作接口，字段由后端派生为主。
3. 前端页面改为状态驱动按钮（仅允许合法状态下出现下一步动作）。
4. 查询结果按登录身份做数据隔离（学生仅看自己，企业仅看本企业）。

#### 验收标准
- 状态机仅允许 `0 -> 1 -> 2`。
- 动作接口拒绝非法状态跳转与越权操作。

### P2：稳定性与上线前治理

#### 目标
- 保证一致性、可追溯、可回归。

#### 改造点
1. 幂等与并发保护（重复投递/重复完成/重复互评）。
2. 审计日志与业务错误码规范化。
3. 关键链路回归用例与冒烟脚本补齐。

#### 验收标准
- 关键动作并发下无重复脏数据。
- 闭环核心场景可稳定回归。

### 执行顺序（最小闭环优先）
1. 先做 P0 三项快速修复并联调。
2. 再推进 P1 动作接口与页面改造。
3. 最后做 P2 稳定性与测试补齐。

### 边界约定
- 本轮不修改菜单相关 SQL（`sys_menu` / 按钮权限SQL），该部分由你手工调整。
