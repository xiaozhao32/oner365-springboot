

# Oner365 Springboot

![Spring Framework 6.2.9](https://shields.io/badge/Spring%20Framework-6.2.9-blue) 
![Spring Boot 3.5.3](https://shields.io/badge/Spring%20Boot-3.5.3-blue) 
![License Apache 2.0](https://shields.io/badge/License-Apache--2.0-blue) 
![Java 21](https://img.shields.io/badge/JDK-21-green)

![Author Zy&Lt](https://shields.io/badge/Author-Zy&Lt-orange) 
![Version 2.1.0](https://shields.io/badge/Version-2.1.0-red) 
![Github star](https://img.shields.io/github/stars/xiaozhao32/oner365-springboot?style=flat&logo=github) 
![Github fork](https://img.shields.io/github/forks/xiaozhao32/oner365-springboot?style=flat&logo=github) 
![Gitee star](https://gitee.com/xiaozhao32/oner365-springboot/badge/star.svg?theme=dark) 
![Gitee fork](https://gitee.com/xiaozhao32/oner365-springboot/badge/fork.svg?theme=dark)

[![Github 仓库](https://github.com/xiaozhao32/oner365-springboot)](https://github.com/xiaozhao32/oner365-springboot) | [Gitee 仓库](https://gitee.com/xiaozhao32/oner365-springboot)

---

## 软件架构
软件架构说明

## 技术框架
1. 数据库：MySQL & Postgres
2. 缓存：Redis
3. 队列：RabbitMQ
4. 文件处理：Fastdfs & Minio & Local
5. 搜索引擎：Elasticserach
6. API框架：Swagger
7. 环境部署：docker

## 使用说明
1. 导入mysql脚本，在 resources/scripts中
2. 修改配置文件 application.yml 相关路径
3. 启动服务 配置启动参数对应的配置文件 如: -Dspring.profiles.active=prod
4. Swagger地址
   
   http://localhost:8704/doc.html
   
5. docker使用
   
   mvn docker:build
   
6. 服务器执行
   
   ```bash
   docker run -d --name oner365-springboot -p 8704:8704 --restart=always --privileged=true oner365-springboot
   ```

### 前端架构
1. 前端地址 - 8701

[前端仓库](https://github.com/xiaozhao32/oner365-vue) | [Gitee 前端仓库](https://gitee.com/xiaozhao32/oner365-vue)