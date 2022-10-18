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
COPY ${JAR_FILE} /root/oner365-springboot/oner365-springboot-2.0.2.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","oner365-springboot-2.0.2.jar"]

# 本地执行
# mvn docker:build
# 服务器执行
# docker run -d --name oner365-springboot-2.0.2 -p 8704:8704 --restart=always --privileged=true --restart=always oner365-springboot:2.0.2
