# 镜像依赖
FROM jdk:21

# 设置元数据
LABEL maintainer="zhaoyong@oner365.com" \
      version="3.1.0" \
      description="oner365-springboot application"

# 工作目录   
VOLUME /root/oner365-springboot
RUN mkdir -p /root/oner365-springboot
WORKDIR /root/oner365-springboot

# 暴露端口
EXPOSE 8704

# 设置时区和启动配置参数
ENV TZ=Asia/Shanghai \
    PATH=$PATH \
    LANG=C.UTF-8 \
    LC_ALL=C.UTF-8 \
    SPRING_PROFILES_ACTIVE=dev \
    SERVER_ADDRESS=0.0.0.0

# 复制依赖包
ARG OUT_DIRECTORY
COPY ${OUT_DIRECTORY}/lib /root/oner365-springboot/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-springboot/resources/

# 复制应用包
ARG JAR_FILE
COPY ${JAR_FILE} /root/oner365-springboot/oner365-springboot-3.1.0.jar

# 启动命令
ENTRYPOINT ["java", \
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
    "-Dserver.address=${SERVER_ADDRESS}", \
    "-jar", "/root/oner365-springboot/oner365-springboot-3.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-springboot -p 8704:8704 --restart=always --privileged=true oner365-springboot
