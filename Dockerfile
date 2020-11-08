FROM openjdk:12-jdk-alpine

WORKDIR "/opt"

COPY spring-config-0.0.1.jar spring-config.jar

COPY config/ config/

CMD ["java","-jar","spring-config.jar"]