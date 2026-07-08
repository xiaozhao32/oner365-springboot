# Java 命名规范参考

## 通用规则

| 元素 | 规范 | 示例 |
|------|------|------|
| 包名 | 全小写，用 `.` 分隔 | `com.oner365.sys.controller` |
| 类名 | PascalCase | `SysUserController` |
| 接口名 | PascalCase，通常以 `I` 前缀 | `ISysUserService` |
| 方法名 | camelCase | `findById`, `createUser` |
| 变量名 | camelCase | `userName`, `pageSize` |
| 常量 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |
| 枚举 | PascalCase（类名） + UPPER_SNAKE_CASE（常量） | `StatusEnum.ENABLED` |

## 本项目分层命名规范

| 层 | 命名模式 | 示例 |
|----|---------|------|
| Controller | `XxxController` | `SysRoleController` |
| Service 接口 | `IXxxService` | `ISysRoleService` |
| Service 实现 | `XxxServiceImpl` | `SysRoleServiceImpl` |
| Mapper 接口 | `XxxMapper` | `SysRoleMapper` |
| Entity | `Xxx` | `SysRole` |
| VO | `XxxVo` | `SysRoleVo` |
| DTO | `XxxDto` | `SysRoleDto` |

## 常见反模式

- ❌ 拼音命名：`getUserXingMing()` → ✅ `getUserName()`
- ❌ 不规范的缩写：`getUsrInf()` → ✅ `getUserInfo()`
- ❌ 含义模糊的命名：`data`, `list`, `map` → ✅ `userList`, `roleMap`
- ❌ 接口与实现命名颠倒：`ServiceImpl` + `IService` → ✅ `IService` + `ServiceImpl`
