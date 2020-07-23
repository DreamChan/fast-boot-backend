FROM openjdk:8-jdk-alpine

MAINTAINER DreamChan

VOLUME /tmp

ENV TZ=Asia/Shanghai

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN echo -e "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main\n\
https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/community" > /etc/apk/repositories
RUN apk --update add curl bash ttf-dejavu && \
      rm -rf /var/cache/apk/*

ADD ./target/fast-boot-backend-1.0.0.jar /app.jar

EXPOSE 6060
ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx768m", "-XX:MetaspaceSize=124m", "-XX:MaxMetaspaceSize=224M"  ,"/app.jar"]