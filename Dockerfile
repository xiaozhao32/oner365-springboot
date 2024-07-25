FROM jdk:1.8

VOLUME /root/oner365-springboot
RUN mkdir -p /root/oner365-springboot
WORKDIR /root/oner365-springboot
EXPOSE 8704

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-springboot/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-springboot/resources/
COPY ${JAR_FILE} /root/oner365-springboot/oner365-springboot-2.1.0.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","oner365-springboot-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-springboot -p 8704:8704 --restart=always --privileged=true oner365-springboot
