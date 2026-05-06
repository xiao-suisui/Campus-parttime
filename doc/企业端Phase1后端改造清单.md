# 企业端 Phase-1 后端改造清单

> 目标：先打通企业端后端闭环（无企业前端改造），并与管理员职责分离。
> 
> 闭环：企业资料维护/提交审核 -> 岗位管理与上架下架 -> 投递处理(录用/完成) -> 企业评学生。

## 1. 目录与命名（Phase-1）

### 1.1 ruoyi-admin 控制层

新增目录：

- `backend/ruoyi-admin/src/main/java/com/ruoyi/web/controller/enterprise/`
- `backend/ruoyi-admin/src/main/java/com/ruoyi/web/controller/admin/campus/`（仅审核、查看）

建议新增类：

- `EnterpriseProfileController`（企业资料）
- `EnterprisePostController`（岗位管理）
- `EnterpriseApplicationController`（投递处理）
- `EnterpriseEvaluationController`（企业评价）
- `AdminEnterpriseAuditController`（管理员审核）

### 1.2 ruoyi-system 业务层

新增目录：

- `backend/ruoyi-system/src/main/java/com/ruoyi/enterprise/service/`
- `backend/ruoyi-system/src/main/java/com/ruoyi/enterprise/service/impl/`

建议新增服务：

- `IEnterpriseFlowService`
- `EnterpriseFlowServiceImpl`

> 说明：优先复用现有 `domain/mapper/service`，避免一次性拆太深。

## 2. 接口范围（后端）

### 2.1 企业资料

- `GET /enterprise/profile`
- `POST /enterprise/profile`
- `PUT /enterprise/profile`
- `POST /enterprise/profile/submit-audit`

### 2.2 岗位管理

- `GET /enterprise/post/list`
- `GET /enterprise/post/{postId}`
- `POST /enterprise/post`
- `PUT /enterprise/post`
- `PUT /enterprise/post/{postId}/up`
- `PUT /enterprise/post/{postId}/down`

### 2.3 投递处理

- `GET /enterprise/application/list`
- `GET /enterprise/application/{applicationId}`
- `PUT /enterprise/application/{applicationId}/hire`
- `PUT /enterprise/application/{applicationId}/complete`

### 2.4 企业互评学生

- `GET /enterprise/evaluation/list`
- `POST /enterprise/evaluation`

### 2.5 管理员仅审核（只审只看）

- `GET /admin/campus/enterprise-audit/list`
- `GET /admin/campus/enterprise-audit/{enterpriseId}`
- `PUT /admin/campus/enterprise-audit/{enterpriseId}/pass`
- `PUT /admin/campus/enterprise-audit/{enterpriseId}/reject`

## 3. 核心业务规则

- 企业接口一律服务端注入 `enterpriseId = 当前登录用户ID`。
- 忽略前端传入的 `enterpriseId/studentId`，防止越权。
- 投递状态仅允许：`0 -> 1 -> 2`。
- 企业评价仅允许在投递 `application_status = '2'` 时提交。
- 企业资料审核未通过时，不允许岗位上架。

## 4. 灰度迁移策略

- 兼容保留旧入口：`/campus/*`、`/system/*`（只做委派）。
- 新增企业入口：`/enterprise/*`。
- 先后端双路由稳定，再逐步把调用方切到新前缀。

## 5. 验收标准

- 企业账号可以完整跑通后端闭环。
- 管理员不具备企业业务新增/修改/删除权限。
- 越权请求（跨企业数据）被拒绝。
- 状态机错误流转被拒绝。
- 与已完成学生端后端改造不冲突。

## 6. 回滚方案

- 路由回滚：流量切回旧 `/campus|/system`。
- 权限回滚：先回滚 `sys_role_menu`，再回滚新增 `sys_menu`。
- 业务回滚：停用 `/enterprise/*` 控制器入口，保留代码不删除。

