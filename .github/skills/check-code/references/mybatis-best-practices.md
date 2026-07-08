# MyBatis 最佳实践参考

## #{} vs ${}

| 语法 | 说明 | 安全性 | 使用场景 |
|------|------|--------|---------|
| `#{value}` | 预编译参数，自动加引号 | ✅ 安全 | 几乎所有场景 |
| `${value}` | 字符串直接拼接 | ❌ SQL注入风险 | 仅动态表名/列名/排序字段 |

## N+1 查询问题

### 问题示例

```xml
<!-- ❌ N+1：先查主表，再逐条查关联 -->
<select id="selectAllUsers">
    SELECT * FROM sys_user
</select>
<!-- 对每个 user，执行 N 次：SELECT * FROM sys_role WHERE user_id = ? -->
```

### 解决方案

```xml
<!-- ✅ 使用 JOIN 一次查询 -->
<select id="selectUsersWithRoles" resultMap="UserRoleResultMap">
    SELECT u.*, r.* FROM sys_user u
    LEFT JOIN sys_user_role ur ON u.id = ur.user_id
    LEFT JOIN sys_role r ON ur.role_id = r.id
</select>
```

## 批量操作

### ✅ 批量插入（推荐使用 MyBatis Batch）

```xml
<insert id="batchInsert" parameterType="list">
    INSERT INTO sys_user(name, email) VALUES
    <foreach collection="list" item="item" separator=",">
        (#{item.name}, #{item.email})
    </foreach>
</insert>
```

### ⚠️ 逐条插入（禁止在大批量场景使用）

```java
// ❌ 循环内逐条插入
for (User user : userList) {
    userMapper.insert(user); // N 次数据库交互
}
```

## 分页查询

- 使用 PageHelper 或 MyBatis 分页插件
- 大结果集必须分页，禁止无限制查询

```java
// 使用 PageHelper 分页
PageHelper.startPage(pageNum, pageSize);
List<User> list = userMapper.selectPage();
PageInfo<User> pageInfo = new PageInfo<>(list);
```
