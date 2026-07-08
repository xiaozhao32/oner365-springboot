---
name: check-code
description: 'Check Spring Boot project code quality (code review / 代码审查). Covers: code style, security, performance, exception handling, project structure. Use when: reviewing PRs, pre-commit self-check, debugging issues, refactoring assessment.'
argument-hint: 'Path or module to check (optional)'
---

# 检查代码 (Code Review Checklist)

针对 `oner365-springboot` Spring Boot 项目的代码质量检查清单。

## 何时使用

- 提交代码前进行自检
- Review 他人的 Pull Request
- 排查线上问题时审查相关代码
- 重构前评估代码质量

## 使用方式

在聊天中输入 `/check-code` 调用此技能，可附带指定文件或模块路径缩小检查范围。

## 检查清单

### 一、代码风格与规范

- [ ] **命名规范**：类名 PascalCase、方法/变量 camelCase、常量 UPPER_SNAKE_CASE，包名全小写
- [ ] **分层命名**：Controller 以 `XxxController`、Service 接口以 `IXxxService`、实现以 `XxxServiceImpl`、Mapper 以 `XxxMapper` 命名
- [ ] **注释质量**：关键业务逻辑有 JavaDoc 注释，中英文注释无混淆，不保留无用注释代码
- [ ] **代码整洁**：无未使用的 import、无多余空行、无魔法数字（用常量代替）、方法不超过 80-100 行
- [ ] **泛型使用**：集合声明指定泛型类型，避免原始类型警告
- [ ] **日志规范**：使用 SLF4J Logger（`private static final Logger`），日志级别使用正确（error/warn/info/debug）

### 二、安全审查

- [ ] **SQL 注入**：MyBatis Mapper XML 中使用 `#{}` 而非 `${}`；若必须用 `${}` 需对参数做白名单校验
- [ ] **XSS 防护**：前端展示的字符串已做 HTML 转义，JSON 输出不包含未处理的用户输入
- [ ] **权限校验**：Controller 接口明确标注权限注解或在 TokenInterceptor 白名单外做了鉴权；关键操作有操作权限校验
- [ ] **Token 安全**：Token 生成使用强密钥（HMAC/RSA），有过期时间；`TokenInterceptor` 白名单配置正确
- [ ] **参数校验**：使用 `@Valid` / `@Validated` + `jakarta.validation` 注解对请求参数做校验，不信任外部输入
- [ ] **敏感信息**：配置文件中无明文密码/密钥，使用环境变量或配置中心管理；日志不打印密码、Token 等敏感信息
- [ ] **文件上传**：文件上传做了类型、大小限制，路径穿越防护
- [ ] **CORS 配置**：`TokenInterceptorConfigurer` 中跨域配置合理，不开放给所有来源（生产环境）

### 三、性能问题

- [ ] **N+1 查询**：MyBatis 关联查询使用 JOIN 或 `@Many` 时注意 N+1 问题，检查是否需要懒加载或批处理
- [ ] **循环内调用**：不在 for 循环内调用远程服务、数据库查询、HTTP 请求（应批量处理）
- [ ] **事务管理**：`@Transactional` 注解范围合理（不跨网络调用），只读操作设置 `readOnly = true`
- [ ] **批量操作**：批量插入/更新使用 MyBatis Batch 或 JDBC batch，而非逐条执行
- [ ] **连接池**：数据库连接池（HikariCP）配置合理（最大连接数、超时时间）
- [ ] **缓存使用**：频繁查询的热点数据使用 Redis 缓存，避免重复查库
- [ ] **大对象处理**：避免一次性加载大字段（如 BLOB/TEXT），使用流式处理或分页
- [ ] **异步处理**：耗时操作（邮件发送、文件处理、消息推送）使用 `@Async` 或消息队列异步执行

### 四、异常处理

- [ ] **全局异常**：使用 `@RestControllerAdvice` / `@ControllerAdvice` 统一处理异常，不将异常堆栈直接返回前端
- [ ] **业务异常**：业务逻辑异常使用自定义业务异常类（如 `BusinessException`），而非直接抛出 `RuntimeException`
- [ ] **try-catch 粒度**：不捕获 `Exception` 或 `Throwable`（除非顶级入口），精确捕获具体异常类型
- [ ] **资源关闭**：IO 流、数据库连接等资源使用 try-with-resources 确保释放
- [ ] **事务回滚**：`@Transactional` 明确指定 `rollbackFor`，确保异常时事务正确回滚
- [ ] **日志记录**：catch 块中记录错误日志（含完整堆栈），不吞没异常

### 五、项目结构

- [ ] **包依赖**：模块间依赖方向正确（sys → data，而非 data → sys），无循环依赖
- [ ] **DTO/VO/Entity 分离**：Controller 返回 VO、Service 使用 DTO、ORM 使用 Entity，不混用
- [ ] **配置管理**：环境相关配置在 `application-dev.yml` 等 profile 文件中，`application.yml` 放公共配置
- [ ] **依赖冗余**：`pom.xml` 中无未使用的依赖，版本号统一管理在 `<properties>` 中
- [ ] **API 规范**：RESTful 路径符合规范（复数名词、HTTP 动词语义正确），统一响应格式（`ResponseResult` / `R`）
- [ ] **代码复用**：无大段重复代码，公共逻辑抽取到工具类（`data` 模块的 commons 包中）或基类

## 相关资源

### 项目文件
- 项目配置：[application.yml](../../../src/main/resources/application.yml)
- 开发配置：[application-dev.yml](../../../src/main/resources/config/application-dev.yml)
- 安全拦截器：[TokenInterceptor.java](../../../src/main/java/com/oner365/interceptor/TokenInterceptor.java)
- Maven 依赖管理：[pom.xml](../../../pom.xml)

### 参考资料（详细规范说明）
- [Java 命名规范参考](./references/naming-conventions.md)
- [安全编码规范参考](./references/security-checklist.md)
- [MyBatis 最佳实践参考](./references/mybatis-best-practices.md)
