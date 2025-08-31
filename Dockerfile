FROM openjdk:17-jdk-slim as builder
WORKDIR application
COPY target/lottery-rnd-generators-checker-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=tools extract --layers --launcher -jar app.jar
FROM openjdk:17-jdk-slim
WORKDIR application
COPY --from=builder application/dependencies/ ./dependencies/
COPY --from=builder application/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=builder application/spring-boot-loader/ ./spring-boot-loader/
COPY --from=builder application/application/ ./application/
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]