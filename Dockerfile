FROM openjdk:8-jdk-alpine

MAINTAINER DreamChan

VOLUME /tmp

RUN echo -e "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main\n\
https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/community" > /etc/apk/repositories
RUN apk --update add curl bash ttf-dejavu tzdata && \
      cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime &&\
      echo "Asia/Shanghai" > /etc/timezone &&\
      rm -rf /var/cache/apk/*

ADD ./target/fast-boot-backend-1.0.0.jar /app.jar
RUN bash -c 'touch /app.jar'

EXPOSE 6060
ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx768m", "-XX:MetaspaceSize=124m", "-XX:MaxMetaspaceSize=224M"  ,"/app.jar"]