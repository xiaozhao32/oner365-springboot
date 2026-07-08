# 安全编码规范参考

## 本项目安全架构

本项目使用自定义 JWT Token 拦截器进行鉴权，无 Spring Security / Shiro 框架。

### Token 认证流程

```
请求 → TokenInterceptor (preHandle)
         ├── 白名单路径 → 放行
         └── 非白名单 → 校验 Token
                  ├── Token 有效 → 放行，设置用户上下文
                  └── Token 无效 → 返回 401
```

### 白名单配置

路径在 `application-dev.yml` 中 `ignore.whites` 配置，关键白名单路径包括：
- `/login`, `/register`
- `/doc.html`, `/webjars/**`, `/v3/api-docs/**` (Swagger/Knife4j)
- `/static/**` (静态资源)
- `/actuator/**` (监控端点)

## 常见安全漏洞检查

### 1. SQL 注入

**MyBatis 中的安全写法：**

```xml
<!-- ✅ 安全：使用 #{} 预编译 -->
<select id="findByName">
    SELECT * FROM sys_user WHERE name = #{name}
</select>

<!-- ❌ 危险：使用 ${} 直接拼接 -->
<select id="findByOrder">
    SELECT * FROM sys_user ORDER BY ${orderBy}
</select>
```

如果必须使用 `${}`（如动态排序），必须对参数做白名单校验：

```java
private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "name", "createTime");

public String validateSortField(String input) {
    if (!ALLOWED_SORT_FIELDS.contains(input)) {
        return "id"; // 默认值
    }
    return input;
}
```

### 2. XSS 防护

- 后端输出的用户输入字符串需要 HTML 转义
- 使用 Spring 的 `HtmlUtils.htmlEscape()` 或自定义序列化器
- JSON 响应中不应包含未处理的富文本用户输入

### 3. 文件上传安全

- 限制文件类型：使用白名单而非黑名单
- 限制文件大小：`spring.servlet.multipart.max-file-size`
- 防止路径穿越：对文件名做净化处理，移除 `../` 等路径字符
- 上传文件保存到专用目录，不混入项目目录

### 4. 敏感信息管理

- 配置文件中不应包含明文密码、密钥、Token
- 使用环境变量或配置中心（Spring Cloud Config / Nacos / Vault）
- 日志中禁止打印密码、Token、身份证号、银行卡号等
- 使用 `@Value` 或 `@ConfigurationProperties` 注入配置

### 5. CORS 配置

```java
// ✅ 生产环境：指定允许的来源
.allowedOrigins("https://admin.oner365.com")

// ❌ 禁止：开放所有来源
.allowedOrigins("*")
```
