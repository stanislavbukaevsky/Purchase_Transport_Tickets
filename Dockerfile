FROM maven:3-openjdk-17 AS build
WORKDIR /producer/
COPY src /producer/src
COPY /pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]