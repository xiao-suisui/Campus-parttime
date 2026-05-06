# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Campus part-time job information platform (高校校园兼职信息平台) — a graduation thesis project built on the **RuoYi v3.9.2** rapid development framework. Three user roles (Student, Enterprise, Admin) with separate API endpoints and frontend views.

## Repository Structure

- `backend/` — Spring Boot 4.x multi-module Maven project (Java 17)
- `frontend/` — Vue 2 + Element UI web admin panel
- `RuoYi-App-master/` — UniApp mobile client (Vue 2, H5/WeChat/App)
- `doc/` — Project documentation and dev plans (Chinese)

## Build & Run Commands

### Backend
```bash
cd backend
mvn -DskipTests clean package          # Build
java -jar ruoyi-admin/target/ruoyi-admin.jar  # Run (port 8080)
# Windows: bin/run.bat
# Linux/Mac: ./ry.sh start
```
Requires MySQL database `campus_parttime` on localhost:3306 and Redis on localhost:6379.

### Frontend
```bash
cd frontend
npm install --registry=https://registry.npmmirror.com
npm run dev          # Dev server on port 80, proxies /dev-api to localhost:8080
npm run build:prod   # Production build
```

### Mobile App
Open `RuoYi-App-master/` in HBuilderX or use UniApp CLI. Configure API base URL in `RuoYi-App-master/config.js`.

### SQL Setup
Run scripts in order from `backend/sql/`: `ry_20260321.sql` (base schema), then `campus_parttime_module.sql` (business tables), then `campus_parttime_rbac.sql` (permissions).

## Architecture

### Backend Modules (Maven)
| Module | Purpose |
|---|---|
| `ruoyi-admin` | Entry point (`RuoYiApplication.java`), controllers, resources/config |
| `ruoyi-framework` | Security, interceptors, AOP, datasource config |
| `ruoyi-system` | Business logic — both RuoYi system (Sys*) and custom business (biz) |
| `ruoyi-common` | Shared utilities, annotations, constants, exceptions, XSS filter |
| `ruoyi-quartz` | Scheduled task management |
| `ruoyi-generator` | Velocity-based code generation for CRUD scaffolding |

### Business Code Location
Custom business code lives under `com.ruoyi.biz.*` in `ruoyi-system`, separated from the framework's `com.ruoyi.system.*`:
- `biz/recruitment/` — Job posts, applications, collections, evaluations
- `biz/enterprise/` — Enterprise profiles
- `biz/student/` — Student info, resumes
- `biz/admin/audit/` — Enterprise qualification audit
- `biz/common/` — Shared DTOs, VOs, enums, validators

### Backend Layering
Controller → Service → Mapper (MyBatis DAO) → Domain. Controllers are in `ruoyi-admin` under role-based subdirectories: `student/`, `enterprise/`, `admin/audit/`.

### Frontend Structure
- `src/api/{student,enterprise,admin/audit}/` — API service modules per role
- `src/views/system/{student,enterprise,admin}/` — Vue page components per role
- `src/utils/request.js` — Axios instance with JWT token injection
- `src/router/index.js` — Route definitions (dynamic menu loading from backend)

### Key Config Files
- `backend/ruoyi-admin/src/main/resources/application.yml` — Main config (port, Redis, MyBatis, JWT token)
- `backend/ruoyi-admin/src/main/resources/application-druid.yml` — MySQL datasource
- `frontend/vue.config.js` — Webpack dev server proxy, port, chunk splitting
- `frontend/.env.development` — Dev API base path (`/dev-api`)

### Conventions
- JWT-based auth via Spring Security; tokens injected via Axios interceptors on frontend
- Three RBAC roles: `student`, `enterprise`, `admin` — each with distinct API prefix (`/student/*`, `/enterprise/*`, `/admin/*`)
- Business status fields use numeric codes (e.g., application: 0=submitted, 1=hired, 2=completed)
- RuoYi code generator (`ruoyi-generator`) can scaffold CRUD — templates in `ruoyi-generator/src/main/resources/vm/`
