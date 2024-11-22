
<h3 align="center">Oner365 Springboot</h3>

---

<p align="center">
	<a href="https://spring.io/projects/spring-framework" target="_blank"><img src="https://shields.io/badge/Spring%20Framework-6.1.15-blue" alt="Spring Framework 6.1.15"></a>
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://shields.io/badge/Spring%20Boot-3.3.6-blue" alt="Spring Boot 3.3.6"></a>
    <a href="./LICENSE"><img src="https://shields.io/badge/License-Apache--2.0-blue" alt="License Apache 2.0"></a>
    <a href="https://www.oracle.com/java/technologies/javase-downloads.html" target="_blank"><img src="https://img.shields.io/badge/JDK-21-green" alt="Java 21"></a>
</p>
<p align="center">
    <a href="#"><img src="https://shields.io/badge/Author-Zy&Lt-orange" alt="Zy&Lt"></a>
    <a href="#"><img src="https://shields.io/badge/Version-2.1.0-red" alt="Version 2.1.0"></a>
    <a href="https://github.com/xiaozhao32/oner365-springboot"><img src="https://img.shields.io/github/stars/xiaozhao32/oner365-springboot?style=flat&logo=github" alt="Github star"></a>
    <a href="https://github.com/xiaozhao32/oner365-springboot"><img src="https://img.shields.io/github/forks/xiaozhao32/oner365-springboot?style=flat&logo=github" alt="Github fork"></a>
    <a href="https://gitee.com/xiaozhao32/oner365-springboot"><img src="https://gitee.com/xiaozhao32/oner365-springboot/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/xiaozhao32/oner365-springboot"><img src="https://gitee.com/xiaozhao32/oner365-springboot/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>
<p align="center">
    <a href="https://github.com/xiaozhao32/oner365-springboot">Github 仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/xiaozhao32/oner365-springboot">Gitee 仓库</a>
</p>

---


#### 介绍
1. 个人练习 Springboot

#### 软件架构
软件架构说明

#### 技术框架

1. 数据库：MySQL && Postgres
2. 缓存：Redis
3. 队列：RabbitMQ
4. 文件处理：Fastdfs && Minio && Local
5. 搜索引擎：Elasticserach
6. API框架：Swagger
7. 环境部署：docker

#### 使用说明

1.  导入mysql脚本, 在 resources/scripts中
2.  修改配置文件 application.yml 相关路径。
3.  启动服务 配置启动参数对应的配置文件 如: -Dspring.profiles.active=prod
4.  Swagger地址

      http://localhost:8704/doc.html
      
5. docker使用 

    mvn docker:build
    
6. 服务器执行

    docker run -d --name oner365-springboot -p 8704:8704 --restart=always --privileged=true --restart=always oner365-springboot

##### 前端架构
1. 前端地址 - 8701 
<p>
	<a href="https://github.com/xiaozhao32/oner365-vue">Github 前端仓库</a> &nbsp; | &nbsp; <a href="https://gitee.com/xiaozhao32/oner365-vue">Gitee 前端仓库</a>
</p>

